package todday.funny.seoulcatcher.service;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.request.target.NotificationTarget;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;


import todday.funny.seoulcatcher.GlideApp;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.interactor.OnLoadUserDataFinishListener;
import todday.funny.seoulcatcher.model.Call;
import todday.funny.seoulcatcher.model.History;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.server.ServerDataController;
import todday.funny.seoulcatcher.ui.activity.IntroActivity;
import todday.funny.seoulcatcher.ui.activity.MapActivity;
import todday.funny.seoulcatcher.util.DateFormat;
import todday.funny.seoulcatcher.util.Keys;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private final static int NOTIFICATION_ID = 111;

    private static Handler handler = null;
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        Log.d(TAG, ": " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            onDestroy();

            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(remoteMessage.getData());
            final Call call = gson.fromJson(jsonElement, Call.class);

            //노티파이
            ServerDataController.getInstance(this).getUser(ServerDataController.getInstance(this).getLoginUserId(), new OnLoadUserDataFinishListener() {
                @Override
                public void onComplete(User user) {
                    if (user != null) {
                        int level = Integer.parseInt(user.getLevel());
                        if (level > 0) {
                            startNotify(MyFirebaseMessagingService.this, call);
                            //히스토리 저장
                            ServerDataController.getInstance(MyFirebaseMessagingService.this).saveHistory(new History(call));
                        }
                    }
                }
            });

        }
    }

    public static void startNotify(final Context context, final Call call) {

        Intent main = new Intent(context, IntroActivity.class);
        Intent map = new Intent(context, MapActivity.class);
        map.putExtra(Keys.CALL, call);
        PendingIntent pendingIntent = PendingIntent.getActivities(context, 0, new Intent[]{main, map}, PendingIntent.FLAG_UPDATE_CURRENT);

        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        remoteViews.setTextViewText(R.id.text_time, DateFormat.getCallTime(System.currentTimeMillis()));


        String name = context.getString(R.string.call_user_name, call.getToUserName()) + " / " + call.getKind();
        if (call.getKind().equals(context.getString(R.string.cardiac_arrest))) {
            name += " / " + call.getAge();
        }
        remoteViews.setTextViewText(R.id.text_to_user_name, name);

        String location = context.getString(R.string.location) + " : " + call.getAddress();
        remoteViews.setTextViewText(R.id.text_location, location);

        final Notification notification = new NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
                .setSmallIcon(R.drawable.ic_bell_64px)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_STATUS)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, notification);

        handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                final NotificationTarget userTarget = new NotificationTarget(context, R.id.image_touser, remoteViews, notification, NOTIFICATION_ID);
                GlideApp.with(context.getApplicationContext()).asBitmap().circleCrop().load(call.getToUserPhotoUrl()).into(userTarget);
            }
        });

    }


    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}
