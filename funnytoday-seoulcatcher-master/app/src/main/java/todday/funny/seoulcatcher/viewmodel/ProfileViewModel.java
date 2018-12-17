package todday.funny.seoulcatcher.viewmodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import todday.funny.seoulcatcher.interactor.OnLoadScheduleListListener;
import todday.funny.seoulcatcher.interactor.OnLoadUserDataFinishListener;
import todday.funny.seoulcatcher.interactor.OnUploadFinishListener;
import todday.funny.seoulcatcher.model.Schedule;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.util.Keys;

public class ProfileViewModel extends BaseViewModel implements SwipeRefreshLayout.OnRefreshListener {
    public ObservableArrayList<Object> mProfileList = new ObservableArrayList<>();

    public ObservableField<String> mUserId = new ObservableField<>();
    public ObservableField<User> mUser = new ObservableField<>();


    public ProfileViewModel(Context context, String userId) {
        super(context);
        this.mUserId.set(userId);
        initData(userId);
    }

    private void initData(String userId) {
        mProfileList.clear();
        getUser(userId);
    }

    public void getUser(final String userId) {
        showLoading.set(true);
        mServerDataController.getUser(userId, new OnLoadUserDataFinishListener() {
            @Override
            public void onComplete(User user) {
                mUser.set(user);
                mProfileList.add(0, user);
                getUserSchedule(userId);
                showLoading.set(false);
            }
        });
    }

    public void getUserSchedule(String userId) {
        showLoading.set(true);
        mServerDataController.getUserSchedule(userId, new OnLoadScheduleListListener() {
            @Override
            public void onComplete(List<Schedule> scheduleList) {
                if (scheduleList != null && scheduleList.size() > 0) {
                    mProfileList.addAll(scheduleList);
                } else {
                    mProfileList.add(Keys.EMPTY);
                }
                showLoading.set(false);
            }
        });
    }

    //브로드캐스트
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(Keys.EDIT_USER)) {
                    User user = intent.getParcelableExtra(Keys.USER);
                    if (user != null) {
                        mProfileList.set(0, user);
                        mUser.set(user);
                        mUser.notifyChange();
                        mServerDataController.setLoginUser(user);

                    }
                } else if (action.equals(Keys.ADD_SCHEDULE) || action.equals(Keys.DELETE_SCHEDULE)) {
                    initData(mUserId.get());
                }
            }

        }
    };

    public BroadcastReceiver getBroadcastReceiver() {
        return broadcastReceiver;
    }

    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Keys.EDIT_USER);
        intentFilter.addAction(Keys.ADD_SCHEDULE);
        intentFilter.addAction(Keys.DELETE_SCHEDULE);
        return intentFilter;
    }

    @Override
    public void onRefresh() {
        initData(mUserId.get());
    }
}

