package todday.funny.seoulcatcher.viewmodel.bindingAdapter;

import android.databinding.BindingAdapter;
import android.support.design.widget.BottomNavigationView;

public class NavigationViewBindingAdapter {
    @BindingAdapter({"setSelectedItemId"})
    public static void setSelectedItemId(BottomNavigationView view, int itemId) {
        view.setSelectedItemId(itemId);
    }

    @BindingAdapter({"setOnNavigationItemSelected"})
    public static void setOnNavigationItemSelectedListener(BottomNavigationView view, BottomNavigationView.OnNavigationItemSelectedListener listener) {
        if (view != null && listener != null) {
            view.setOnNavigationItemSelectedListener(listener);
        }
    }
}
