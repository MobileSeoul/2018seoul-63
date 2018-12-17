package todday.funny.seoulcatcher.ui.dialog.educationDialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.MembershipListBinding;
import todday.funny.seoulcatcher.databinding.OneonenineBinding;
import todday.funny.seoulcatcher.ui.dialog.MembershipDialog;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.viewmodel.MembershipListViewModel;
import todday.funny.seoulcatcher.viewmodel.educationViewModel.OneonenineViewModel;

public class OneonenineFragmentDialog extends DialogFragment {
    private OneonenineBinding binding;

    public static OneonenineFragmentDialog newInstance() {
        Bundle args = new Bundle();
        OneonenineFragmentDialog fragment = new OneonenineFragmentDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_oneonenine, container, false);
        if (getArguments() != null) {
            String level = getArguments().getString(Keys.LEVEL);
            OneonenineViewModel model = new OneonenineViewModel(getActivity());
            binding.setModel(model);
        }
        return binding.getRoot();
    }

}

