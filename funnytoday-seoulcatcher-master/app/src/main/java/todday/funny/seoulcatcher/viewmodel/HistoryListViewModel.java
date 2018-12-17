package todday.funny.seoulcatcher.viewmodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ObservableArrayList;

import java.util.List;

import todday.funny.seoulcatcher.interactor.OnLoadHistoryListListener;
import todday.funny.seoulcatcher.model.History;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.util.Keys;

public class HistoryListViewModel extends BaseViewModel {
    public ObservableArrayList<Object> mHistoryList = new ObservableArrayList<>();
    private String mUserId;

    public HistoryListViewModel(Context context, String userId) {
        super(context);
        mUserId = userId;
        getUserHistory(userId);
    }

    public void getUserHistory(String userId) {
        mServerDataController.getUserHistory(userId, new OnLoadHistoryListListener() {
            @Override
            public void onComplete(List<History> historyList) {
                mHistoryList.clear();
                if (historyList != null && historyList.size() > 0) {
                    mHistoryList.addAll(historyList);
                } else {
                    mHistoryList.add(Keys.EMPTY);
                }

            }
        });
    }

    //브로드캐스트
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(Keys.ADD_HISTORY)) {
                    History history = intent.getParcelableExtra(Keys.HISTORY);
                    if (mHistoryList != null && mHistoryList.size() > 0) {
                        if (mHistoryList.size() == 1 && mHistoryList.get(0) instanceof String) {
                            mHistoryList.clear();
                            mHistoryList.add(0, history);
                        } else {
                            mHistoryList.add(0, history);
                        }
                    }

                }
            }

        }
    };

    public BroadcastReceiver getBroadcastReceiver() {
        return broadcastReceiver;
    }

    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Keys.ADD_HISTORY);
        return intentFilter;
    }
}
