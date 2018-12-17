package todday.funny.seoulcatcher.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.HistoryBinding;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.viewmodel.HistoryListViewModel;

public class HistoryFragment extends Fragment {

    private HistoryBinding binding = null;
    private HistoryListViewModel model = null;

    public static HistoryFragment newInstance(String userId) {
        Bundle args = new Bundle();
        args.putString(Keys.USER_ID, userId);
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        if (getArguments() != null) {
            String userId = getArguments().getString(Keys.USER_ID);
            model = new HistoryListViewModel(getContext(), userId);
            getActivity().registerReceiver(model.getBroadcastReceiver(), model.getIntentFilter());
            binding.setModel(model);
        }
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity() != null && binding != null && binding.getModel() != null) {
            getActivity().unregisterReceiver(binding.getModel().getBroadcastReceiver());
        }
    }
}

