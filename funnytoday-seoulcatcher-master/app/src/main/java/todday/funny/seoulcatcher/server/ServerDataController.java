package todday.funny.seoulcatcher.server;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.interactor.OnEduDateListener;
import todday.funny.seoulcatcher.interactor.OnInitUserDataListener;
import todday.funny.seoulcatcher.interactor.OnLoadHistoryListListener;
import todday.funny.seoulcatcher.interactor.OnLoadMemberShipsListener;
import todday.funny.seoulcatcher.interactor.OnLoadScheduleListListener;
import todday.funny.seoulcatcher.interactor.OnLoadUserDataFinishListener;
import todday.funny.seoulcatcher.interactor.OnScheduleListener;
import todday.funny.seoulcatcher.interactor.OnUploadFinishListener;
import todday.funny.seoulcatcher.model.Call;
import todday.funny.seoulcatcher.model.EduDate;
import todday.funny.seoulcatcher.model.History;
import todday.funny.seoulcatcher.model.MemberShip;
import todday.funny.seoulcatcher.model.Schedule;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.model.messageModel.Message;
import todday.funny.seoulcatcher.model.messageModel.SendCallData;
import todday.funny.seoulcatcher.model.routeModel.Route;
import todday.funny.seoulcatcher.util.ImageConverter;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.util.SendBroadcast;

public class ServerDataController {
    private String TAG = ServerDataController.class.getSimpleName();
    private Context mContext;

    private FirebaseFirestore db; //Cloud FireBase
    //  private FirebaseStorage storage; //소티지
    private StorageReference storageReference;
    private User mLoginUser;
    private String mLoginUserId;
    private int LIMIT_COUNT = 6;

    private FcmService mFcmService;
    private RouteService mRouteService;

    private static volatile ServerDataController singletonInstance = null;


    public static ServerDataController getInstance(Context context) {
        if (singletonInstance == null) {
            synchronized (ServerDataController.class) {
                if (singletonInstance == null) {
                    singletonInstance = new ServerDataController(context);
                }
            }
        }
        return singletonInstance;
    }

    private ServerDataController(Context context) {
        mContext = context;
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mFcmService = FcmService.Creator.create();
        mRouteService = RouteService.Creator.create();
    }

    public String getLoginUserId() {
        if (mLoginUserId == null) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                mLoginUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            }
        }
        return mLoginUserId;
    }

    public void setLoginUser(User user) {
        if (user != null && user.getId().equals(mLoginUserId)) {
            this.mLoginUser = user;
        }
    }

    //ur 가져오기
    private void getImageDownLoadURL(@NonNull UploadTask uploadTask, @NonNull OnCompleteListener onCompleteListener) {
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return task.getResult().getStorage().getDownloadUrl();
            }
        }).addOnCompleteListener(onCompleteListener);
    }


    /**
     * 유저관련
     */
    public void initUser(final User user, final OnInitUserDataListener onInitUserDataListener, final OnFailureListener onFailureListener) {
        if (onInitUserDataListener != null && onFailureListener != null) {
            db.collection(Keys.USERS).document(user.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User newUser = documentSnapshot.toObject(User.class);
                    if (newUser != null) {
                        onInitUserDataListener.onComplete();
                    } else {
                        db.collection(Keys.USERS).document(user.getId()).set(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        onInitUserDataListener.onComplete();
                                    }
                                })
                                .addOnFailureListener(onFailureListener);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    db.collection(Keys.USERS).document(user.getId()).set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    onInitUserDataListener.onComplete();
                                }
                            })
                            .addOnFailureListener(onFailureListener);
                }
            });

        }
    }

    public Single<User> getLoginUser() {
        return Single.create(new SingleOnSubscribe<User>() {
            @Override
            public void subscribe(final SingleEmitter<User> emitter) throws Exception {
                if (mLoginUser == null) {
                    getUser(getLoginUserId(), new OnLoadUserDataFinishListener() {
                        @Override
                        public void onComplete(User user) {
                            if (user != null) {
                                mLoginUser = user;
                                emitter.onSuccess(mLoginUser);
                            } else {
                                emitter.onError(new Exception());
                            }

                        }
                    });
                } else {
                    emitter.onSuccess(mLoginUser);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getUser(String userId, @NonNull final OnLoadUserDataFinishListener onLoadUserDataFinishListener) {
        Log.d(TAG + "getUser", "userId = " + userId);
        if (userId.equals(getLoginUserId()) && mLoginUser != null) {
            onLoadUserDataFinishListener.onComplete(mLoginUser);
        } else {
            db.collection(Keys.USERS).document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);
                    onLoadUserDataFinishListener.onComplete(user);
                }
            });
        }
    }

    public void updateUser(String userId, String name, String nickName, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        Log.d(TAG + "updateUser", "userId = " + userId);
        Map<String, Object> updateUser = new HashMap<>();
        updateUser.put("name", name);
        updateUser.put("nickName", nickName);
        if (onSuccessListener != null && onFailureListener != null) {
            db.collection(Keys.USERS).document(userId).update(updateUser)
                    .addOnSuccessListener(onSuccessListener)
                    .addOnFailureListener(onFailureListener);
        }
    }

    public void updateUserLevel(User user, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        Log.d(TAG + "updateUserLevel", "userId = " + user.getId());
        user.setLevel(String.valueOf(Integer.parseInt(user.getLevel()) + 1));
        Map<String, Object> updateUser = new HashMap<>();
        updateUser.put("level", user.getLevel());
        if (onSuccessListener != null && onFailureListener != null) {
            db.collection(Keys.USERS).document(user.getId()).update(updateUser)
                    .addOnSuccessListener(onSuccessListener)
                    .addOnFailureListener(onFailureListener);
        }
    }


    //유저 프로파일 업데이트
    public void updateUserProfile(CompositeDisposable compositeDisposable, final String userId, String profileUrl, final OnUploadFinishListener onUploadFinishListener) {
        compositeDisposable.add(ImageConverter.stringToBitmap(mContext, profileUrl).subscribe(new Consumer<Bitmap>() {
            @Override
            public void accept(Bitmap bitmap) throws Exception {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = storageReference.child(Keys.USERS).child(userId).child(Keys.USER_PROFILE).putBytes(data);
                getImageDownLoadURL(uploadTask, new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();

                            db.collection(Keys.USERS).document(userId).update("photoUrl", String.valueOf(downloadUri));
                            onUploadFinishListener.onUploadFinish(String.valueOf(downloadUri));
                        }
                    }
                });
            }
        }));
    }

    //유저 배경 업데이트
    public void updateUserBackground(CompositeDisposable compositeDisposable, final String userId, String backgroundUrl, final OnUploadFinishListener onUploadFinishListener) {
        compositeDisposable.add(ImageConverter.stringToBitmap(mContext, backgroundUrl).subscribe(new Consumer<Bitmap>() {
            @Override
            public void accept(Bitmap bitmap) throws Exception {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = storageReference.child(Keys.USERS).child(userId).child(Keys.USER_BACKGROUND).putBytes(data);
                getImageDownLoadURL(uploadTask, new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            db.collection(Keys.USERS).document(userId).update("backgroundUrl", String.valueOf(downloadUri));
                            onUploadFinishListener.onUploadFinish(String.valueOf(downloadUri));
                        }
                    }
                });
            }
        }));
    }

    public void logout(OnCompleteListener<Void> completeListener) {
        AuthUI.getInstance()
                .signOut(mContext)
                .addOnCompleteListener(completeListener);
    }

    /**
     * 스케줄 관련
     */

    public void getUserSchedule(String userId, final OnLoadScheduleListListener onLoadScheduleListListener) {
        db.collection(Keys.USERS).document(userId).collection(Keys.SCHEDULES).orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Schedule> scheduleArrayList = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (int i = 0; i < task.getResult().getDocuments().size(); i++) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(i);
                        Schedule schedule = documentSnapshot.toObject(Schedule.class);
                        scheduleArrayList.add(schedule);
                    }
                }
                onLoadScheduleListListener.onComplete(scheduleArrayList);
            }
        });
    }

    public void getUserSchedule(String userId, final OnScheduleListener onLoadScheduleListListener) {
        final ArrayList<Schedule> scheduleArrayList = new ArrayList<>();
        FirebaseFirestore.getInstance().collection(Keys.USERS).document(userId).collection(Keys.SCHEDULES).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (int i = 0; i < task.getResult().getDocuments().size(); i++) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(i);
                        Schedule schedule = documentSnapshot.toObject(Schedule.class);
                        String key = documentSnapshot.getId();
                        Log.e("uid", documentSnapshot.getId());
                        Log.e("getschedules", schedule.getDate() + "  " + key);
                        scheduleArrayList.add(schedule);
                    }
                    onLoadScheduleListListener.onComplete(scheduleArrayList);
                } else {
                    onLoadScheduleListListener.onComplete(scheduleArrayList);
                }

            }
        });
    }

    public void getEducationDate(final OnEduDateListener educationDate) {
        if (educationDate != null) {
            db.collection("educationDate").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (queryDocumentSnapshots == null) {
                        Log.e("recyclerView", "없다!");
                    } else {
                        final ArrayList<EduDate> eduDates = new ArrayList<>();
                        for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                            EduDate eduDate = (queryDocumentSnapshots.getDocuments().get(i)).toObject(EduDate.class);
                            Log.e("data", eduDate.getDate());
                            eduDates.add(eduDate);
                        }
                        educationDate.onComplete(eduDates);
                    }

                }
            });

        }
    }

    public void saveUserSchedule(final Schedule schedule) {
        DocumentReference documentReference = db.collection(Keys.USERS).document(mLoginUserId).collection(Keys.SCHEDULES).document();
        schedule.setKey(documentReference.getId());
        documentReference.set(schedule).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(Keys.ADD_SCHEDULE);
                intent.putExtra(Keys.SCHEDULE, schedule);
                mContext.sendBroadcast(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void deleteUserSchedule(Schedule schedule, OnSuccessListener onSuccessListener) {
        db.collection(Keys.USERS).document(mLoginUserId).collection(Keys.SCHEDULES).document(schedule.getKey()).delete()
                .addOnSuccessListener(onSuccessListener);
    }


    /**
     * 멤버쉽
     */
    /**
     * 세팅 멤버쉽
     */
    public void setMemberShipsData() {
        ArrayList<MemberShip> memberShips = new ArrayList<>();
        //name color discountRate
        memberShips.add(new MemberShip("PARIS BAGUETTE"
                , "https://firebasestorage.googleapis.com/v0/b/funnytoday-seoulcatcher.appspot.com/o/memberShips%2FlogoB_mod_renew.png?alt=media&token=ddd71f4f-59b2-48fc-8eec-df75c40e0222",
                "#14408B", 10));
        memberShips.add(new MemberShip("MEGABOX"
                , "https://firebasestorage.googleapis.com/v0/b/funnytoday-seoulcatcher.appspot.com/o/memberShips%2Flogo_megabox.png?alt=media&token=444afc4f-337f-4605-95d1-29ca74c0c8f8",
                "#361F66", 10));
        memberShips.add(new MemberShip("CGV"
                , "https://firebasestorage.googleapis.com/v0/b/funnytoday-seoulcatcher.appspot.com/o/memberShips%2Flogo_cvg.png?alt=media&token=21d630c9-99e2-4009-a5e7-2d98f383665d",
                "#E71A0F", 10));
        memberShips.add(new MemberShip("OLIVE_YOUNG"
                , "https://firebasestorage.googleapis.com/v0/b/funnytoday-seoulcatcher.appspot.com/o/memberShips%2Flogo_olive_young.png?alt=media&token=0e70a663-be5d-4853-8bf9-206ac3f9760c",
                "#000000", 10));
        memberShips.add(new MemberShip("INNISFREE"
                , "https://firebasestorage.googleapis.com/v0/b/funnytoday-seoulcatcher.appspot.com/o/memberShips%2Flogo_innisfree.png?alt=media&token=350121d0-5df1-4d1e-98bb-caeb2995662d",
                "#015D23", 10));
        memberShips.add(new MemberShip("BASKIN ROBBINS"
                , "https://firebasestorage.googleapis.com/v0/b/funnytoday-seoulcatcher.appspot.com/o/memberShips%2Flogo_baskinrobbins.png?alt=media&token=52192869-2494-4fd9-9e5d-f0c1f411ee3d",
                "#7D7364", 10));
        memberShips.add(new MemberShip("LOTTE CINEMA"
                , "https://firebasestorage.googleapis.com/v0/b/funnytoday-seoulcatcher.appspot.com/o/memberShips%2Flogo_lotte_cinema.gif?alt=media&token=243e1597-36d3-4244-a650-44108532dbe9",
                "#C5C5C5", 10));
        for (MemberShip memberShip : memberShips) {
            db.collection(Keys.MEMBER_SHIPS).document("5").collection(Keys.ITEMS).add(memberShip);
        }
    }


    /**
     * 멤버쉽 가져오기
     */
    public void getMemberShips(String level, final OnLoadMemberShipsListener onLoadMemberShipsListener) {
        int levelInt = Integer.parseInt(level);
        level = levelInt > 5 ? "5" : level;
        db.collection(Keys.MEMBER_SHIPS).document(level).collection(Keys.ITEMS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<MemberShip> memberShips = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (int i = 0; i < task.getResult().getDocuments().size(); i++) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(i);
                        MemberShip memberShip = documentSnapshot.toObject(MemberShip.class);
                        memberShips.add(memberShip);
                    }
                }
                onLoadMemberShipsListener.onComplete(memberShips);
            }
        });
    }


    /**
     * 히스토리 저장
     */
    public void saveHistory(final History history) {
        String id = getLoginUserId();
        if (id != null) {
            DocumentReference documentReference = db.collection(Keys.USERS).document(id).collection(Keys.HISTORYS).document();
            history.setId(documentReference.getId());
            history.setDate(System.currentTimeMillis());
            documentReference.set(history).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    SendBroadcast.history(mContext, Keys.ADD_HISTORY, history);
                }
            });
        }
    }

    public void getUserHistory(String userId, final OnLoadHistoryListListener onLoadHistoryListListener) {
        db.collection(Keys.USERS).document(userId).collection(Keys.HISTORYS).orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<History> historyArrayList = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (int i = 0; i < task.getResult().getDocuments().size(); i++) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(i);
                        History history = documentSnapshot.toObject(History.class);
                        historyArrayList.add(history);
                    }
                }
                onLoadHistoryListListener.onComplete(historyArrayList);
            }
        });
    }

    /**
     * FCM  호출하기
     */
    public Observable<Message> call(final Call call) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Bearer " + getAccessToken(mContext));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<? extends Message>>() {
                    @Override
                    public ObservableSource<? extends Message> apply(String token) throws Exception {
                        SendCallData sendCallData = new SendCallData();
                        Message message = sendCallData.getMessage();
                        message.setData(call);
                        message.setTopic(Keys.TOPIC_KEY);
                        return mFcmService.call(token, Keys.CONTENT_TYPE, sendCallData)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                });
    }

    /**
     * FCM 토큰얻기...
     */
    private static String getAccessToken(Context context) {
        GoogleCredential googleCredential = null;
        try {
            googleCredential = GoogleCredential
                    .fromStream(context.getResources().getAssets().open("funnytoday-seoulcatcher-firebase-adminsdk-82a7x-6ad4959447.json"))
                    .createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase.messaging"));
            googleCredential.refreshToken();
            return googleCredential.getAccessToken();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 경로 탐색
     */

    public Observable<Route> getRoute(String startLon, String startLat, String endLon, String endLat, String startName, String endName) {
        return mRouteService.route("4af07278-8a47-4171-b910-69cde5f881f6", startLon, startLat, endLon, endLat, startName, endName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}