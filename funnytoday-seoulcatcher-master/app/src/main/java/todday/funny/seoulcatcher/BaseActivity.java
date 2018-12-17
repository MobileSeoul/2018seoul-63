package todday.funny.seoulcatcher;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.TransitionInflater;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import io.reactivex.disposables.CompositeDisposable;
import todday.funny.seoulcatcher.util.ToastMake;
import todday.funny.seoulcatcher.viewmodel.EducationViewModel;
import todday.funny.seoulcatcher.viewmodel.ScheduleViewModel;
import todday.funny.seoulcatcher.viewmodel.SettingViewModel;

public class BaseActivity extends AppCompatActivity {
    public InputMethodManager inputMethodManager;

    public CompositeDisposable compositeDisposable;

    private SettingViewModel settingViewModel;
    private EducationViewModel educationViewModel;

    //backpress
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        compositeDisposable = new CompositeDisposable();
    }

    public void hideKeyboard() { //키보드 가리는 함수
        if (inputMethodManager != null && getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void onBackPressed() { //두번클릭해야 종료
        int childFragmentSize = getSupportFragmentManager().getFragments().size();
        if (childFragmentSize > 5) { //5개인 이유? MainFragment 4개 + SupportRequestManagerFragment{6d43a4c #5 com.bumptech.glide.manager}{parent=null} 이게 추가되어서.. glide module 떄문에 생성되는건가? 총 5개
            super.onBackPressed();
        } else {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            } else {
                ToastMake.make(this, getString(R.string.msg_quit));
            }
            mBackPressed = System.currentTimeMillis();
        }
    }

    /**
     * 모델 가져오기
     */

    public EducationViewModel getEducationViewModel() {
        if (educationViewModel == null) {
            educationViewModel = new EducationViewModel(this);
        }
        return educationViewModel;
    }

    public SettingViewModel getSettingViewModel() {
        if (settingViewModel == null) {
            settingViewModel = new SettingViewModel(this);
        }
        return settingViewModel;
    }

    /**
     * 다이얼로그 시작
     */

    /**
     * 다이얼로그 시작
     */

    public void startFragmentDialog(DialogFragment dialogFragment, int transitionId) {
        String fragmentTag = dialogFragment.getClass().getSimpleName();

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogFragment.setEnterTransition(TransitionInflater.from(this).inflateTransition(transitionId));
        }
        fragmentManager.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true)
                .addToBackStack(fragmentTag)
                .replace(android.R.id.content, dialogFragment)
                .commit();
    }

    public void addFragmentDialog(DialogFragment dialogFragment, int transitionId) {
        String fragmentTag = dialogFragment.getClass().getSimpleName();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogFragment.setEnterTransition(TransitionInflater.from(this).inflateTransition(transitionId));
        }
        fragmentManager.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true)
                .addToBackStack(fragmentTag)
                .add(android.R.id.content, dialogFragment)
                .commit();
    }
}
