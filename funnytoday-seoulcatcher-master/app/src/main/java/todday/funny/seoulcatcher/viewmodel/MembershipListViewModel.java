package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import java.util.List;

import io.reactivex.functions.Consumer;
import todday.funny.seoulcatcher.interactor.OnLoadMemberShipsListener;
import todday.funny.seoulcatcher.model.MemberShip;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.util.Keys;

public class MembershipListViewModel extends BaseViewModel {
    public ObservableArrayList<Object> mMemberShipList = new ObservableArrayList<>();

    public MembershipListViewModel(Context context, String level) {
        super(context);

        //mServerDataController.setMemberShipsData();
        initData(level);
    }

    public void initData(final String level) {
        showLoading.set(true);
        mCompositeDisposable.add(mServerDataController.getLoginUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                mMemberShipList.add(Keys.BARCODE);
                mMemberShipList.add(user);
                getMemberShips(level);
            }
        }));

    }

    public void getMemberShips(String level) {
        showLoading.set(true);
        mServerDataController.getMemberShips(level, new OnLoadMemberShipsListener() {
            @Override
            public void onComplete(List<MemberShip> memberShips) {
                mMemberShipList.addAll(memberShips);
                showLoading.set(false);
            }
        });
    }
}
