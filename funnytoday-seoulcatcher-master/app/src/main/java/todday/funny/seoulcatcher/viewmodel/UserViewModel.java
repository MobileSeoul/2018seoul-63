package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import todday.funny.seoulcatcher.model.User;

public class UserViewModel extends BaseViewModel {
    public ObservableField<User> mUser = new ObservableField<>();

    public ObservableBoolean isMy = new ObservableBoolean(false);

    public UserViewModel(Context context, User user) {
        super(context);
        if (user != null) {
            mUser.set(user);
            if (user.getId() != null) {
                isMy.set(user.getId().equals(mServerDataController.getLoginUserId()));
            }
        }

    }
}
