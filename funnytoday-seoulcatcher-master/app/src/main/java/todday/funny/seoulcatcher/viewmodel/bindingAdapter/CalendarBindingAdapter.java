package todday.funny.seoulcatcher.viewmodel.bindingAdapter;

import android.databinding.BindingAdapter;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

public class CalendarBindingAdapter {

    @BindingAdapter("setOnDateSelectedListener")
    public static void setOnDateSelectedListener(MaterialCalendarView view, OnDateSelectedListener onDateSelectedListener) {

        if (onDateSelectedListener != null) {
            view.setOnDateChangedListener(onDateSelectedListener);
        }
    }
}
