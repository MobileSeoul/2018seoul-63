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
import todday.funny.seoulcatcher.databinding.UsingFireBinding;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.viewmodel.educationViewModel.UsingFireViewModel;

public class UsingFireFragmentDialog extends DialogFragment {
    private UsingFireBinding binding;

    public static UsingFireFragmentDialog newInstance() {
        Bundle args = new Bundle();
        UsingFireFragmentDialog fragment = new UsingFireFragmentDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_usingfire, container, false);
        if (getArguments() != null) {
            String level = getArguments().getString(Keys.LEVEL);
            UsingFireViewModel model = new UsingFireViewModel(getActivity());
            binding.setModel(model);
        }
        return binding.getRoot();
    }

}
