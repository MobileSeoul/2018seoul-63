package todday.funny.seoulcatcher.viewmodel.bindingAdapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import todday.funny.seoulcatcher.GlideApp;
import todday.funny.seoulcatcher.R;

public class ImageBindingAdapter {
    @BindingAdapter({"setImage"})
    public static void setImage(final ImageView view, final String path) {
        final Context context = view.getContext();
        final int width = view.getMeasuredWidth();
        final int height = view.getMeasuredHeight();
        if (path != null) {
            GlideApp.with(context).load(path).override(width, height).centerCrop().into(view);
        }
    }

    @BindingAdapter({"setLogoImage"})
    public static void setLogoImage(final ImageView view, final String path) {
        final Context context = view.getContext();
        if (path != null) {
            GlideApp.with(context).load(path).fitCenter().thumbnail(0.1f).into(view);
        }
    }

    @BindingAdapter({"setImage", "setPhotoView"})
    public static void setImage(final ImageView view, final String path, boolean isPhotoView) {
        final Context context = view.getContext();
        final int width = view.getMeasuredWidth();
        final int height = view.getMeasuredHeight();
        if (path != null) {
            if (isPhotoView) {
                GlideApp.with(context).load(path).fitCenter().thumbnail(0.1f).into(view);
            } else {
                GlideApp.with(context).load(path).override(width, height).centerCrop().thumbnail(0.1f).into(view);
            }
        }
    }


    @BindingAdapter("setCircleImage")
    public static void setCircleImage(final ImageView view, final String path) {
        final Context context = view.getContext();
        final int width = view.getMeasuredWidth();
        final int height = view.getMeasuredHeight();
        if (path != null) {
            GlideApp.with(context).load(path).override(width, height).circleCrop().thumbnail(0.1f).into(view);
        }
    }

    @BindingAdapter("setMapCallKindImage")
    public static void setMapCallKindImage(final ImageView view, final String kind) {
        final Context context = view.getContext();
        if (kind != null) {
            if (kind.equals(context.getString(R.string.cardiac_arrest))) {
                view.setImageResource(R.drawable.ic_heart_24dp);

            } else {
                view.setImageResource(R.drawable.ic_fire_24dp);
            }
        }
    }

    @BindingAdapter("setMapCallAgemage")
    public static void setMapCallAgemage(final ImageView view, final String age) {
        final Context context = view.getContext();
        if (age != null) {
            view.setVisibility(View.VISIBLE);
            if (age.equals(context.getString(R.string.children))) {
                view.setImageResource(R.drawable.ic_child_24dp);
            } else if (age.equals(context.getString(R.string.general))) {
                view.setImageResource(R.drawable.ic_adult_24dp);
            } else {
                view.setImageResource(R.drawable.ic_old_24dp);
            }
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
