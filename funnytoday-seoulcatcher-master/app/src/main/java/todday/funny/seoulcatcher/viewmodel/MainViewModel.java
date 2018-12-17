package todday.funny.seoulcatcher.viewmodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.ui.activity.MainActivity;
import todday.funny.seoulcatcher.ui.fragment.EducationFragment;
import todday.funny.seoulcatcher.ui.fragment.HistoryFragment;
import todday.funny.seoulcatcher.ui.fragment.ProfileFragment;
import todday.funny.seoulcatcher.ui.fragment.ScheduleFragment;
import todday.funny.seoulcatcher.ui.fragment.SettingFragment;
import todday.funny.seoulcatcher.util.Keys;

public class MainViewModel extends BaseViewModel {
    private ArrayList<Fragment> mainFragmentList = new ArrayList<>();
    private ObservableField<String> mCurrentTag = new ObservableField<>();
    public ObservableInt mSelectId = new ObservableInt(R.id.navigation_profile);

    public BottomNavigationView.OnNavigationItemSelectedListener mNavigationItemSelectedListener;

    public MainViewModel(Context context) {
        super(context);
    }

    public void setNavigationItemSelectedListener() {
        this.mNavigationItemSelectedListener = navigationItemSelectedListener;
    }

    private void setCurrentTag(String tag) {
        mCurrentTag.set(tag);
    }

    public void initFragmentList(int id) {
        mainFragmentList.add(ScheduleFragment.newInstance());
        mainFragmentList.add(EducationFragment.newInstance());
        mainFragmentList.add(ProfileFragment.newInstance(mServerDataController.getLoginUserId()));
        mainFragmentList.add(HistoryFragment.newInstance(mServerDataController.getLoginUserId()));
        if (mContext instanceof AppCompatActivity) {
            FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
            if (fragmentManager != null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                for (Fragment fragment : mainFragmentList) {
                    fragmentTransaction.add(id, fragment, fragment.getClass().getSimpleName());
                    fragmentTransaction.hide(fragment);
                }
                setCurrentTag(ProfileFragment.class.getSimpleName());
                fragmentTransaction.show(mainFragmentList.get(2));
                fragmentTransaction.commit();
            }
        }
    }

    public void moveFragment(String tag) {
        if (tag != null) {
            if (mCurrentTag.get() != null && !mCurrentTag.get().equals(tag)) {
                if (mContext instanceof MainActivity) {
                    mCurrentTag.set(tag);
                    FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    for (Fragment fragment : fragmentManager.getFragments()) {
                        String fTag = fragment.getTag();
                        if (fTag != null) {
                            if (fTag.equals(tag)) {
                                fragmentTransaction.show(fragment);
                            } else {
                                fragmentTransaction.hide(fragment);
                            }
                        }
                    }
                    fragmentTransaction.setReorderingAllowed(true);
                    fragmentTransaction.commit();
                }
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_educationSchedule:
                    moveFragment(ScheduleFragment.class.getSimpleName());
                    return true;
                case R.id.navigation_education:
                    moveFragment(EducationFragment.class.getSimpleName());
                    return true;
                case R.id.navigation_call:
                    openCall();
                    return false;
                case R.id.navigation_profile:
                    moveFragment(ProfileFragment.class.getSimpleName());
                    return true;
                case R.id.navigation_history:
                    moveFragment(HistoryFragment.class.getSimpleName());
                    return true;
     /*           case R.id.navigation_setting:
                  //  moveFragment(SettingFragment.class.getSimpleName());
                    return true;*/
            }
            return false;
        }
    };

    //브로드캐스트
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(Keys.APPLY_EDUCATION)) {
                    moveFragment(ScheduleFragment.class.getSimpleName());
                    mSelectId.set(R.id.navigation_educationSchedule);
                    mSelectId.notifyChange();
                }
            }
        }
    };

    public BroadcastReceiver getBroadcastReceiver() {
        return broadcastReceiver;
    }

    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Keys.APPLY_EDUCATION);
        return intentFilter;
    }


}
