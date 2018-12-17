package todday.funny.seoulcatcher.ui.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.SettingBinding;
import todday.funny.seoulcatcher.viewmodel.SettingViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    private SettingBinding binding;
    private SettingViewModel model;

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            model = ((BaseActivity) getActivity()).getSettingViewModel();
            binding.setModel(model);
        }
        return binding.getRoot();
    }

}
