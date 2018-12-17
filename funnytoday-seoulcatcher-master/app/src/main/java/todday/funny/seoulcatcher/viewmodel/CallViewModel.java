package todday.funny.seoulcatcher.viewmodel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import retrofit2.Response;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.model.Call;
import todday.funny.seoulcatcher.model.PlaceData;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.model.messageModel.Message;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.util.PermissionCheck;
import todday.funny.seoulcatcher.util.RequestCode;
import todday.funny.seoulcatcher.util.ShowIntent;
import todday.funny.seoulcatcher.util.ToastMake;

public class CallViewModel extends BaseViewModel {
    public ObservableField<Call> mCall = new ObservableField<>(new Call());
    public ObservableBoolean isCardiac = new ObservableBoolean(false);
    public ObservableBoolean isSelectLocation = new ObservableBoolean(false);


    public CallViewModel(Context context) {
        super(context);
    }

    public CallViewModel(Context context, Call call) {
        super(context);
        mCall.set(call);
    }

    public void initData(User user) {
        if (user != null) {
            mCall.get().setToUserId(user.getId());
            mCall.get().setToUserName(user.getName());
            mCall.get().setToUserPhotoUrl(user.getPhotoUrl());
        }
        mCall.get().setKind(mContext.getString(R.string.fire)); //초기 셋팅값
    }

    public void onClickKind(View view) {
        if (view.getId() == R.id.btn_cardiac) {
            isCardiac.set(true);
        } else {
            isCardiac.set(false);
        }
        if (view instanceof RadioButton) {
            RadioButton radioButton = (RadioButton) view;
            String kind = radioButton.getText().toString();
            mCall.get().setKind(kind);
        }
        isCardiac.notifyChange();
    }

    public void onClickAge(View view) {
        if (view instanceof RadioButton) {
            RadioButton radioButton = (RadioButton) view;
            String age = radioButton.getText().toString();
            mCall.get().setAge(age);

        }
    }

    public void onClickLocation() {
        PermissionCheck.loactionCheck(mContext, new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                ShowIntent.location(mContext, RequestCode.SELECT_CALL_LOCATION);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        });
    }

    public void call() {
        Call call = mCall.get();
        if (call != null) {
            if (isCardiac.get()) {
                if (call.getAge() == null || call.getAge().length() == 0) {
                    ToastMake.make(mContext, R.string.error_age);
                    return;
                }
            }
            if (call.getAddress() == null || call.getAddress().length() == 0 || call.getLatitude() == null || call.getLongitude() == null) {
                ToastMake.make(mContext, R.string.hint_location);
                return;
            }

            showLoading.set(true);
            call.setDate(String.valueOf(System.currentTimeMillis()));
            mCompositeDisposable.add(mServerDataController.call(call).subscribe(new Consumer<Message>() {
                @Override
                public void accept(Message message) throws Exception {
                    ToastMake.make(mContext, R.string.msg_call);
                    showLoading.set(false);
                    close();
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                    ToastMake.make(mContext, R.string.network_error);
                    showLoading.set(false);
                }
            }));

        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(Keys.SELECT_CALL_LOCATION)) {
                    isSelectLocation.set(true);
                    PlaceData placeData = intent.getParcelableExtra(Keys.PLACE);
                    LatLng latLng = placeData.getLatLng();

                    mCall.get().setName(placeData.getName());
                    mCall.get().setAddress(placeData.getAddress());
                    mCall.get().setLatitude(String.valueOf(latLng.latitude));
                    mCall.get().setLongitude(String.valueOf(latLng.longitude));
                    mCall.notifyChange();
                }
            }

        }
    };

    public BroadcastReceiver getBroadcastReceiver() {
        return broadcastReceiver;
    }

    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Keys.SELECT_CALL_LOCATION);
        return intentFilter;
    }
}
