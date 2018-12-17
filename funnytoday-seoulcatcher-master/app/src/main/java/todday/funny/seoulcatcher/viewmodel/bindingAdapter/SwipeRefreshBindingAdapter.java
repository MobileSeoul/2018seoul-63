package todday.funny.seoulcatcher.viewmodel.bindingAdapter;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

public class SwipeRefreshBindingAdapter {

    @BindingAdapter({"setOnRefreshListener"})
    public static void setOnRefreshListener(SwipeRefreshLayout view, SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        view.setOnRefreshListener(onRefreshListener);
    }

    @BindingAdapter({"setSwipeLoading"})
    public static void setSwipeLoading(SwipeRefreshLayout view, boolean isLoading) {
        view.setRefreshing(isLoading);
    }
}
