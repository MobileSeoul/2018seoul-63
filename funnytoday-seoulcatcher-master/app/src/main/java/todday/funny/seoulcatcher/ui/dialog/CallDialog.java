package todday.funny.seoulcatcher.ui.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.CallBinding;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.viewmodel.CallViewModel;

public class CallDialog extends DialogFragment {
    private CallBinding binding;
    private GoogleApiClient mClient;

    public static CallDialog newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(Keys.USER, user);
        CallDialog fragment = new CallDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        mClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mClient.connect();*/
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_call, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (binding != null && getActivity() != null && getArguments() != null) {
            User user = getArguments().getParcelable(Keys.USER);
            CallViewModel model = new CallViewModel(getActivity());
            model.initData(user);
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
     /*   if (mClient != null) {
            mClient.disconnect();
        }*/
    }
}
