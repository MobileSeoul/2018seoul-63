package todday.funny.seoulcatcher.interactor;

import java.util.List;

import todday.funny.seoulcatcher.model.MemberShip;
import todday.funny.seoulcatcher.model.Schedule;

public interface OnLoadMemberShipsListener {
    void onComplete(List<MemberShip> scheduleList);
}
