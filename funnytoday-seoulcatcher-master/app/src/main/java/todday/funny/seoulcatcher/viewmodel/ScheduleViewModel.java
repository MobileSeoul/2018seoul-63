package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;

import todday.funny.seoulcatcher.model.EduDate;
import todday.funny.seoulcatcher.model.Schedule;
import todday.funny.seoulcatcher.ui.dialog.ScheduleDialog;
import todday.funny.seoulcatcher.util.DateFormat;

public class ScheduleViewModel extends BaseViewModel {
    final ArrayList<EduDate> eduDates = new ArrayList<>();
    final ArrayList<String> listKeys = new ArrayList<>();

    public ObservableField<Schedule> mSchedule = new ObservableField<>();
    //public ObservableField<Boolean> isSchedule = new ObservableField<>();

    public String userUid = FirebaseAuth.getInstance().getUid();

    public boolean isSchedule = true;

    public ScheduleViewModel(Context context) {
        super(context);
    }

    public ScheduleViewModel(Context context, Schedule schedule) {
        super(context);
        mSchedule.set(schedule);
    }

    public void openScheduleInfo(String date) {
        ScheduleDialog dialog = ScheduleDialog.newInstance(date);
        addFragmentDialog(dialog, android.R.transition.slide_top);
    }


}
