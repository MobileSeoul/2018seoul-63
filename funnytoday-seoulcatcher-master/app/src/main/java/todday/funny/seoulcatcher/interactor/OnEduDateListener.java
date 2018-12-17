package todday.funny.seoulcatcher.interactor;

import java.util.ArrayList;

import todday.funny.seoulcatcher.model.EduDate;

public interface OnEduDateListener {
    void onComplete(ArrayList<EduDate> list);
}
