package todday.funny.seoulcatcher.ui.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.ScheduleDialogBinding;
import todday.funny.seoulcatcher.viewmodel.ScheduleDialogViewModel;

public class ScheduleDialog extends DialogFragment implements OnMapReadyCallback {
    private ScheduleDialogBinding binding;
    private ScheduleDialogViewModel model;
    private GoogleMap map = null;
    private MapView mapView = null;

    public static ScheduleDialog newInstance(String date) {
        Bundle args = new Bundle();
        args.putString("date", date);
        ScheduleDialog fragment = new ScheduleDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_schedule, container, false);

        if (getArguments() != null) {
            String date = getArguments().getString("date");
            model = new ScheduleDialogViewModel(getActivity(), date);
            binding.setModel(model);
        }
        View view = binding.getRoot();


        mapView = view.findViewById(R.id.schedueldialog_mapview);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
        mapView.getMapAsync(this);


        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(37.551401, 127.077142));

        markerOptions.title("광나루 안점체험관");

        map.addMarker(markerOptions).showInfoWindow();

        LatLng nowLang = new LatLng(37.551401, 127.077142);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(nowLang, 17.5f);
        map.moveCamera(cameraUpdate);

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
