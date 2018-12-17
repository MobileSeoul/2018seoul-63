package todday.funny.seoulcatcher.interactor;

import java.util.List;

import todday.funny.seoulcatcher.model.History;
import todday.funny.seoulcatcher.model.Schedule;

public interface OnLoadHistoryListListener {
    void onComplete(List<History> historyList);
}
