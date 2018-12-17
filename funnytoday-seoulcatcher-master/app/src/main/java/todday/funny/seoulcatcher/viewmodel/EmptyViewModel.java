package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.util.SendBroadcast;

public class EmptyViewModel extends BaseViewModel {
    public ObservableField<String> mEmptyText = new ObservableField<>();

    public EmptyViewModel(Context context, String emptyText) {
        super(context);
        mEmptyText.set(emptyText);
    }

    public void moveEducationFragment() {
        SendBroadcast.move(mContext, Keys.APPLY_EDUCATION);
    }
}
