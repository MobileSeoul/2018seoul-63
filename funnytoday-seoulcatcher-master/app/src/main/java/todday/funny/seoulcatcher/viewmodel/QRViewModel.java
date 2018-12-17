package todday.funny.seoulcatcher.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.util.SendBroadcast;
import todday.funny.seoulcatcher.util.ToastMake;

public class QRViewModel extends BaseViewModel {
    private ObservableField<User> mUser = new ObservableField<>();
    private CameraSource mCameraSource;
    private BarcodeDetector mBarcodeDetector;
    private SurfaceView mSurfaceView;
    private boolean isProcess = false;

    public QRViewModel(Context context, User user, SurfaceView surfaceView) {
        super(context);
        this.mUser.set(user);
        this.mSurfaceView = surfaceView;

        mBarcodeDetector = new BarcodeDetector.Builder(mContext)
                .setBarcodeFormats(Barcode.QR_CODE) // QR_CODE로 설정하면 좀더 빠르게 인식할 수 있습니다.
                .build();

        mCameraSource = new CameraSource
                .Builder(mContext, mBarcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setAutoFocusEnabled(true)  // AutoFocus를 안하면 초점을 못 잡아서 화질이 많이 흐립니다.
                .build();

        if (callback != null) {
            mSurfaceView.getHolder().addCallback(callback);
        }
        if (barcodeProcessor != null) {
            mBarcodeDetector.setProcessor(barcodeProcessor);
        }
    }


    public void destory() {
        if (callback != null) {
            this.mSurfaceView.getHolder().removeCallback(callback);
        }
        if (barcodeProcessor != null) {
            barcodeProcessor.release();
        }
        mBarcodeDetector = null;
        mCameraSource = null;
        mSurfaceView = null;
    }

    //뷰홀더 연결
    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @SuppressLint("MissingPermission")
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try {   // try-catch 문은 Camera 권한획득을 위한 권장사항
                if (mCameraSource != null && mSurfaceView != null) {
                    mCameraSource.start(mSurfaceView.getHolder());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (mCameraSource != null && mSurfaceView != null) {
                mCameraSource.stop();
            }
        }
    };

    //데이터 인식
    private Detector.Processor<Barcode> barcodeProcessor = new Detector.Processor<Barcode>() {
        @Override
        public void release() {

        }

        @Override
        public void receiveDetections(Detector.Detections<Barcode> detections) {
            final SparseArray<Barcode> barcodes = detections.getDetectedItems();
            if (barcodes != null && barcodes.size() > 0 && !isProcess) {
                isProcess = true;
                String barcodeContents = barcodes.valueAt(0).displayValue; // 바코드 인식 결과물
                if (barcodeContents.equals(Keys.TEST_QR_KEY)) {
                    mServerDataController.updateUserLevel(mUser.get(), new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            ToastMake.make(mContext, R.string.msg_attendance);
                            SendBroadcast.user(mContext, Keys.EDIT_USER, mUser.get());
                            close();

                        }
                    }, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            isProcess = false;
                            ToastMake.make(mContext, R.string.network_error);
                            close();
                        }
                    });
                }
            }
        }
    };
}
