package todday.funny.seoulcatcher.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

import todday.funny.seoulcatcher.R;

public class EventDecorator implements DayViewDecorator {

    private Drawable drawable = null;
    private int color;
    private HashSet<CalendarDay> dates = null;

    public EventDecorator(int color, Collection<CalendarDay> dates, Context context) {
        drawable = context.getResources().getDrawable(R.drawable.image_calendar_item);
        this.color = color;
        this.dates = new HashSet<CalendarDay>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);

    }
}
