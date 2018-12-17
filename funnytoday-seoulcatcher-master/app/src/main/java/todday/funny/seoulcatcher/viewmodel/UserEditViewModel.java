package todday.funny.seoulcatcher.viewmodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.gun0912.tedpermission.PermissionListener;

import java.util.ArrayList;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.interactor.OnUploadFinishListener;
import todday.funny.seoulcatcher.model.History;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.ui.activity.IntroActivity;
import todday.funny.seoulcatcher.ui.dialog.AlertDialogCreate;
import todday.funny.seoulcatcher.ui.dialog.EmailPhoneEditDialog;
import todday.funny.seoulcatcher.ui.dialog.UserEditDialog;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.util.PermissionCheck;
import todday.funny.seoulcatcher.util.SendBroadcast;
import todday.funny.seoulcatcher.util.ShowIntent;
import todday.funny.seoulcatcher.util.StartActivity;
import todday.funny.seoulcatcher.util.ToastMake;

public class UserEditViewModel extends UserViewModel {

    public int EDIT_USER_NAME = 0;
    public int EDIT_USER_NICK_NAME = 1;
    public int EDIT_USER_EMAIL = 2;
    public int EDIT_USER_PHONE = 3;

    public ObservableBoolean isUserNameModify = new ObservableBoolean(false); //네임 변경 유무
    public ObservableBoolean isUserNickNameModify = new ObservableBoolean(false); //닉네임 변경유무
    public ObservableBoolean isUseProfileModify = new ObservableBoolean(false); //프로필 변경유무
    public ObservableBoolean isUserBackgroundModify = new ObservableBoolean(false); //백그라운드 변경유무


    public UserEditViewModel(Context context, User user) {
        super(context, user);
    }

    public void onTextChanged(final CharSequence text, final int type) {
        if (mUser.get() != null) {
            User user = mUser.get();
            if (user != null) {
                if (type == EDIT_USER_NAME) {
                    isUserNameModify.set(true);
                    user.setName(text.toString());
                } else if (type == EDIT_USER_NICK_NAME) {
                    isUserNickNameModify.set(true);
                    user.setNickName(text.toString());
                } else if (type == EDIT_USER_EMAIL) {
                    user.setEmail(text.toString());
                } else if (type == EDIT_USER_PHONE) {
                    user.setPhoneNumber(text.toString());
                }
            }
        }
    }

    public void editImage(final int requestCode) {
        PermissionCheck.imageCheck(mContext, new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                ShowIntent.imageSelect(mContext, requestCode);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                ToastMake.make(mContext, R.string.error_permission);
            }
        });
    }

    public void openUserEmail(User user) {
       /* EmailPhoneEditDialog dialog = EmailPhoneEditDialog.newInstance(user, mContext.getString(R.string.email));
        addFragmentDialog(dialog, android.R.transition.slide_bottom);*/
    }

    public void openUserPhone(User user) {
       /* EmailPhoneEditDialog dialog = EmailPhoneEditDialog.newInstance(user, mContext.getString(R.string.phone));
        addFragmentDialog(dialog, android.R.transition.slide_bottom);*/
    }

    public void logout() {
        mAlertDialogCreate.logout(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mServerDataController.logout(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        StartActivity.startSingle(mContext, new Intent(mContext, IntroActivity.class), true);
                    }
                });
            }
        });

    }


    public void editUser() {
        if (clickTimeCheck() || showLoading.get()) {
            return;
        }

        User user = mUser.get();
        if (user == null) {
            return;
        }
        String name = user.getName();
        if (name == null || name.trim().length() == 0) {
            ToastMake.make(mContext, R.string.hint_name);
            return;
        }
        if (isUserNameModify.get() || isUserNickNameModify.get() || isUseProfileModify.get() || isUserBackgroundModify.get()) { // 하나라도변경시 업뎃 브로딩캐스트 -> Profile ViewModel 로 전송
            SendBroadcast.user(mContext, Keys.EDIT_USER, mUser.get());
            mServerDataController.saveHistory(new History(mUser.get())); //프로필 수정
        }
        if (isUserNameModify.get() || isUserNickNameModify.get()) {
            showLoading.set(true);
            mServerDataController.updateUser(user.getId(), user.getName(), user.getNickName(), new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    showLoading.set(false);
                    close();

                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showLoading.set(false);
                    ToastMake.make(mContext, R.string.network_error);
                }
            });
        } else {
            close();
        }
    }

    /**
     * 이미지 브로드 캐스트
     */
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(Keys.EDIT_USER_PROFILE)) {
                    String path = intent.getStringExtra(Keys.PATH);
                    showLoading.set(true);
                    mServerDataController.updateUserProfile(mCompositeDisposable, mUser.get().getId(), path, new OnUploadFinishListener() {
                        @Override
                        public void onUploadFinish(String path) {
                            showLoading.set(false);
                            mUser.get().setPhotoUrl(path);
                            mUser.notifyChange();
                            isUseProfileModify.set(true);
                        }
                    });

                } else if (action.equals(Keys.EDIT_USER_BACKGROUND)) {
                    String path = intent.getStringExtra(Keys.PATH);
                    showLoading.set(true);
                    mServerDataController.updateUserBackground(mCompositeDisposable, mUser.get().getId(), path, new OnUploadFinishListener() {
                        @Override
                        public void onUploadFinish(String path) {
                            showLoading.set(false);
                            mUser.get().setBackgroundUrl(path);
                            mUser.notifyChange();
                            isUserBackgroundModify.set(true);
                        }
                    });
                }
            }

        }
    };

    public BroadcastReceiver getBroadcastReceiver() {
        return broadcastReceiver;
    }

    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Keys.EDIT_USER_PROFILE);
        intentFilter.addAction(Keys.EDIT_USER_BACKGROUND);
        return intentFilter;
    }
}
