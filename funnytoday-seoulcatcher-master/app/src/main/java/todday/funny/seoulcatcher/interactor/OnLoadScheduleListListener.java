package todday.funny.seoulcatcher.interactor;

import java.util.List;

import todday.funny.seoulcatcher.model.Schedule;
import todday.funny.seoulcatcher.model.User;

public interface OnLoadScheduleListListener {
     void onComplete(List<Schedule> scheduleList);
}
