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
import todday.funny.seoulcatcher.databinding.UserEditBinding;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.viewmodel.UserEditViewModel;


public class UserEditDialog extends DialogFragment {
    private UserEditBinding binding;

    public static UserEditDialog newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(Keys.USER, user);
        UserEditDialog fragment = new UserEditDialog();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_user_edit, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (binding != null && getActivity() != null && getArguments() != null) {
            User user = getArguments().getParcelable(Keys.USER);
            UserEditViewModel model = new UserEditViewModel(getActivity(), user);
            getActivity().registerReceiver(model.getBroadcastReceiver(), model.getIntentFilter());
            binding.setModel(model);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity() != null && binding != null && binding.getModel() != null) {
            getActivity().unregisterReceiver(binding.getModel().getBroadcastReceiver());
        }
    }
}
