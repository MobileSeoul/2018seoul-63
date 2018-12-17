package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.model.User;

public class EmailPhoneEditViewModel extends UserViewModel {
    public final int EDIT_EMAIL_TYPE = 100;
    public final int EDIT_PHONE_TYPE = 101;

    public ObservableField<String> mTitle = new ObservableField<>();


    public ObservableBoolean mEmailVisible = new ObservableBoolean(false);
    public ObservableBoolean mEmailConfirm = new ObservableBoolean(false);
    public ObservableBoolean mErrorEmail = new ObservableBoolean(false);

    public ObservableBoolean mPhoneVisible = new ObservableBoolean(false);
    public ObservableBoolean mPhoneConfirm = new ObservableBoolean(false);
    public ObservableBoolean mErrorPhone = new ObservableBoolean(false);

    public EmailPhoneEditViewModel(Context context, User user, String title) {
        super(context, user);
        mTitle.set(title);
        if (title.equals(mContext.getString(R.string.email))) {
            mEmailVisible.set(true);
        } else if (title.equals(mContext.getString(R.string.phone))) {
            mPhoneVisible.set(true);
        }
    }

    public void onTextChange(CharSequence charSequence, int type) {
        String text = charSequence.toString();
        switch (type) {
            case EDIT_EMAIL_TYPE:
                mUser.get().setEmail(text);
                mEmailConfirm.set(false);
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches() && text.length() > 0) {
                    mErrorEmail.set(true);
                } else {
                    mErrorEmail.set(false);
                }
                break;
            case EDIT_PHONE_TYPE:
                mUser.get().setPhoneNumber(text);
                mPhoneConfirm.set(false);
                if (text.length() < 6 && text.length() > 0) {
                    mErrorPhone.set(true);
                } else {
                    mErrorPhone.set(false);
                }
                break;

        }

    }

    public void certifiedEmail() {

    }

    public void certifiedPhone() {

    }
}
