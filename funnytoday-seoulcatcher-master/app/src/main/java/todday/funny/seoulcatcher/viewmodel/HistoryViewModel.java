package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;

import todday.funny.seoulcatcher.model.Call;
import todday.funny.seoulcatcher.model.History;
import todday.funny.seoulcatcher.model.Schedule;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.ui.activity.MapActivity;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.util.StartActivity;

public class HistoryViewModel extends BaseViewModel {
    public ObservableField<History> mHistory = new ObservableField<>();
    public ObservableField<User> mUser = new ObservableField<>();
    public ObservableField<Call> mCall = new ObservableField<>();
    public ObservableField<Schedule> mSchedule = new ObservableField<>();

    public HistoryViewModel(Context context, History history) {
        super(context);
        mHistory.set(history);
        if (history.getUser() != null) {
            mUser.set(history.getUser());
        } else if (history.getCall() != null) {
            mCall.set(history.getCall());
        } else if (history.getSchedule() != null) {
            mSchedule.set(history.getSchedule());
        }
    }

    public void clickItem() {
        if (clickTimeCheck()) {
            return;
        }
        if (mHistory.get().getCall() != null) {
            Intent map = new Intent(mContext, MapActivity.class);
            map.putExtra(Keys.CALL, mHistory.get().getCall());
            StartActivity.startSingle(mContext, map, false);
        }

    }
}
