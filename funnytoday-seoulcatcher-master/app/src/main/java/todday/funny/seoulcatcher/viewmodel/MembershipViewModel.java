package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import todday.funny.seoulcatcher.model.MemberShip;

public class MembershipViewModel extends BaseViewModel {
    public ObservableField<MemberShip> mMemberShip = new ObservableField<>();

    public MembershipViewModel(Context context, MemberShip memberShip) {
        super(context);
        mMemberShip.set(memberShip);
    }
}
