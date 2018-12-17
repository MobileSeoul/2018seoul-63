package todday.funny.seoulcatcher.interactor;

import java.util.ArrayList;

import todday.funny.seoulcatcher.model.Schedule;

public interface OnScheduleListener {
    void onComplete(ArrayList<Schedule> scheduleList);

}
