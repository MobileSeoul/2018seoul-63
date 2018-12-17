package todday.funny.seoulcatcher.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.yalantis.ucrop.UCrop;

import java.util.List;

import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.MainBinding;
import todday.funny.seoulcatcher.ui.fragment.EducationFragment;
import todday.funny.seoulcatcher.ui.fragment.HistoryFragment;
import todday.funny.seoulcatcher.ui.fragment.ScheduleFragment;
import todday.funny.seoulcatcher.ui.fragment.ProfileFragment;
import todday.funny.seoulcatcher.ui.fragment.SettingFragment;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.util.RequestCode;
import todday.funny.seoulcatcher.util.SendBroadcast;
import todday.funny.seoulcatcher.util.SharedPrefsUtils;
import todday.funny.seoulcatcher.util.ShowIntent;
import todday.funny.seoulcatcher.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity {
    private MainBinding binding;
    private MainViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        model = new MainViewModel(this);
        model.setNavigationItemSelectedListener();
        binding.setModel(model);
        model.initFragmentList(R.id.container);
        registerReceiver(model.getBroadcastReceiver(), model.getIntentFilter());
    }

    @Override
    protected void onDestroy() {
        if (model != null && model.getBroadcastReceiver() != null) {
            unregisterReceiver(model.getBroadcastReceiver());
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK) {
            if (requestCode == RequestCode.EDIT_USER_PROFILE_SELECT) {
                ShowIntent.imageCroup(this, data, RequestCode.EDIT_USER_PROFILE_CROP);
            } else if (requestCode == RequestCode.EDIT_USER_PROFILE_CROP) {
                String path = UCrop.getOutput(data).getPath();
                SendBroadcast.path(this, Keys.EDIT_USER_PROFILE, path); // UserEditViewModel 로 전송
            } else if (requestCode == RequestCode.EDIT_USER_BACKGROUND_SELECT) {
                ShowIntent.imageCroup(this, data, RequestCode.EDIT_USER_BACKGROUND_CROP);
            } else if (requestCode == RequestCode.EDIT_USER_BACKGROUND_CROP) {
                String path = UCrop.getOutput(data).getPath();
                SendBroadcast.path(this, Keys.EDIT_USER_BACKGROUND, path);
            } else if (requestCode == RequestCode.SELECT_CALL_LOCATION) {
                Place place = PlacePicker.getPlace(this, data);
                SendBroadcast.place(this, Keys.SELECT_CALL_LOCATION, place);

            }
        }
    }
}
