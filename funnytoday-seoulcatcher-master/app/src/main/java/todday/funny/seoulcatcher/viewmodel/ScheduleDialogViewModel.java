package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import todday.funny.seoulcatcher.model.Schedule;
import todday.funny.seoulcatcher.util.Keys;

public class ScheduleDialogViewModel extends BaseViewModel {
    public ObservableField<String> mDate = new ObservableField<>();
    public ObservableField<String> mLocationName = new ObservableField<>();


    public ScheduleDialogViewModel(Context context, String date) {
        super(context);
        mDate.set(date);
        mLocationName.set("광나루");
    }

    public void inputScheduleDateBase() {
        mServerDataController.saveUserSchedule(new Schedule(mDate.get(), mLocationName.get()));
        close();
    }
}

