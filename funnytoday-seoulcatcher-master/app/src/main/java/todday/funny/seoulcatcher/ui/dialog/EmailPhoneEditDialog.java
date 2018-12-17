package todday.funny.seoulcatcher.ui.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.EmailPhoneEditBinding;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.viewmodel.EmailPhoneEditViewModel;
import todday.funny.seoulcatcher.viewmodel.UserEditViewModel;

public class EmailPhoneEditDialog extends DialogFragment {
    private EmailPhoneEditBinding binding;

    public static EmailPhoneEditDialog newInstance(User user, String title) {
        Bundle args = new Bundle();
        args.putParcelable(Keys.USER, user);
        args.putString(Keys.TITLE, title);
        EmailPhoneEditDialog fragment = new EmailPhoneEditDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_email_phone_edit, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (binding != null && getActivity() != null && getArguments() != null) {
            User user = getArguments().getParcelable(Keys.USER);
            String title = getArguments().getString(Keys.TITLE);
            EmailPhoneEditViewModel model = new EmailPhoneEditViewModel(getActivity(), user, title);
            binding.setModel(model);
        }
    }
}
