package todday.funny.seoulcatcher.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.support.v4.app.DialogFragment;

import com.gun0912.tedpermission.PermissionListener;

import java.util.ArrayList;
import java.util.logging.Level;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.model.Call;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.server.ServerDataController;
import todday.funny.seoulcatcher.ui.dialog.AlertDialogCreate;
import todday.funny.seoulcatcher.ui.dialog.CallDialog;
import todday.funny.seoulcatcher.ui.dialog.ImageViewerDialog;
import todday.funny.seoulcatcher.ui.dialog.MembershipDialog;
import todday.funny.seoulcatcher.ui.dialog.QRDialog;
import todday.funny.seoulcatcher.ui.dialog.UserEditDialog;
import todday.funny.seoulcatcher.util.PermissionCheck;
import todday.funny.seoulcatcher.util.ShowIntent;
import todday.funny.seoulcatcher.util.ToastMake;

public class BaseViewModel extends BaseObservable {
    public long mLastClickTime = 0;
    public Context mContext;
    public ServerDataController mServerDataController;
    public AlertDialogCreate mAlertDialogCreate;
    public CompositeDisposable mCompositeDisposable;

    public ObservableBoolean showLoading = new ObservableBoolean(false);

    public BaseViewModel(Context context) {
        mContext = context;
        mServerDataController = ServerDataController.getInstance(context);
        mAlertDialogCreate = AlertDialogCreate.getInstance(context);
        if (context instanceof BaseActivity) {
            mCompositeDisposable = ((BaseActivity) context).compositeDisposable;
        }
    }

    public boolean clickTimeCheck() {
        if (System.currentTimeMillis() - mLastClickTime < 700) {
            return true;
        }
        mLastClickTime = System.currentTimeMillis();
        return false;
    }

    public void hideKeyBoard() {
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).hideKeyboard();
        }
    }

    public void close() {
        if (mContext instanceof Activity) {
            ((Activity) mContext).onBackPressed();
        }
        hideKeyBoard();
    }

    public void openImageViewer(String path) {
        ImageViewerDialog dialog = ImageViewerDialog.newInstance(path);
        startFragmentDialog(dialog, android.R.transition.slide_top);
    }

    public void openUserEdit(User user) {
        UserEditDialog dialog = UserEditDialog.newInstance(user);
        addFragmentDialog(dialog, android.R.transition.slide_right);
    }

    public void openMemberShip(String level) {
        MembershipDialog dialog = MembershipDialog.newInstance(level);
        addFragmentDialog(dialog, android.R.transition.slide_top);
    }

    public void openCall() {
        mCompositeDisposable.add(mServerDataController.getLoginUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                if (user != null) {
                    CallDialog dialog = CallDialog.newInstance(user);
                    startFragmentDialog(dialog, android.R.transition.slide_bottom);
                }

            }
        }));

    }

    public void openQR(final User user) {
        PermissionCheck.cameraCheck(mContext, new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                QRDialog dialog = QRDialog.newInstance(user);
                startFragmentDialog(dialog, android.R.transition.slide_top);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                ToastMake.make(mContext, R.string.error_permission);
            }
        });
    }

    /**
     * replace frahment
     */
    public void startFragmentDialog(DialogFragment dialogFragment, int transitionId) {
        if (clickTimeCheck()) {
            return;
        }
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).startFragmentDialog(dialogFragment, transitionId);
        }
    }

    /**
     * add frahment
     */
    public void addFragmentDialog(DialogFragment dialogFragment, int transitionId) {
        if (clickTimeCheck()) {
            return;
        }
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).addFragmentDialog(dialogFragment, transitionId);
        }
    }
}
