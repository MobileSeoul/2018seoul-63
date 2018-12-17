package todday.funny.seoulcatcher.viewmodel.bindingAdapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;

public class ColorBindingAdapter {
    @BindingAdapter({"setBackgroundColor"})
    public static void setBackgroundColor(View view, String color) {
        view.setBackgroundColor(Color.parseColor(color));
    }
}
