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
import todday.funny.seoulcatcher.databinding.FireboomBinding;
import todday.funny.seoulcatcher.databinding.MembershipListBinding;
import todday.funny.seoulcatcher.ui.dialog.MembershipDialog;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.viewmodel.MembershipListViewModel;
import todday.funny.seoulcatcher.viewmodel.educationViewModel.FireboomViewModel;

public class FireboomFragmentDialog extends DialogFragment {
    private FireboomBinding binding;

    public static FireboomFragmentDialog newInstance() {
        Bundle args = new Bundle();
        FireboomFragmentDialog fragment = new FireboomFragmentDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fireboom, container, false);
        if (getArguments() != null) {
            String level = getArguments().getString(Keys.LEVEL);
            FireboomViewModel model = new FireboomViewModel(getActivity());
            binding.setModel(model);
        }
        return binding.getRoot();
    }

}
