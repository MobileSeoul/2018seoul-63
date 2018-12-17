package todday.funny.seoulcatcher.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.SupportMapFragment;
import com.gun0912.tedpermission.PermissionListener;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.MapBinding;
import todday.funny.seoulcatcher.model.Call;
import todday.funny.seoulcatcher.ui.dialog.AlertDialogCreate;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.util.PermissionCheck;
import todday.funny.seoulcatcher.util.RequestCode;
import todday.funny.seoulcatcher.util.ToastMake;
import todday.funny.seoulcatcher.viewmodel.MapViewModel;

public class MapActivity extends AppCompatActivity {
    private MapBinding binding;
    private MapViewModel model;
    private LocationManager mLocationManager;
    private CompositeDisposable mCompositeDisposable;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mCompositeDisposable = new CompositeDisposable();
        Call call = getIntent().getParcelableExtra(Keys.CALL);
        model = new MapViewModel(this, call);
        model.setCompositeDisposable(mCompositeDisposable);
        binding.setModel(model);
        binding.map.onCreate(savedInstanceState);
        permissionLocation();
    }


    private void permissionLocation() {
        PermissionCheck.loactionCheck(this, new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                enableLocation();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                ToastMake.make(getApplicationContext(), R.string.error_permission);
                finish();
            }
        });
    }

    private void enableLocation() {
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialogCreate.getInstance(this).locationOn(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivityForResult(intent, RequestCode.LOCATION_ENABLE);
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    enableLocation();
                }
            });
        } else {
            model.setMapView(binding.map);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.LOCATION_ENABLE) {
            if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                ToastMake.make(this, R.string.msg_location_on);
            }
            permissionLocation();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        binding.map.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.map.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        binding.map.onStop();
    }

    @Override
    protected void onPause() {
        binding.map.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        binding.map.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.map.onLowMemory();
    }
}
