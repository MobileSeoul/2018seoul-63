package todday.funny.seoulcatcher.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import todday.funny.seoulcatcher.R;


public class ShowIntent {
    public static void imageMultiSelect(Activity context, int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(intent, requestCode);
    }

    public static void imageSelect(Context context, int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    public static void imageSelect(Context context, int requestCode, int position) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((Activity) context).getIntent().putExtra(Keys.POSITION, position);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    public static void imageCroup(Context context, Intent data, int requestCode) {
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(context, R.color.white));
        options.setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimary));
        options.setToolbarWidgetColor(ContextCompat.getColor(context, R.color.colorAccent));
        options.setActiveWidgetColor(ContextCompat.getColor(context, R.color.colorAccent));
        options.setToolbarTitle(context.getString(R.string.edit_image));

        String fileName = String.valueOf(System.currentTimeMillis()) + ".png";
        UCrop uCrop = UCrop.of(data.getData(), Uri.fromFile(new File(context.getCacheDir(), fileName)));
        uCrop.withOptions(options);
        uCrop.start(((Activity) context), requestCode);
    }

    public static void location(Context context, int requestCode) {
        if (context instanceof AppCompatActivity) {
            try {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                ((AppCompatActivity) context).startActivityForResult(builder.build((AppCompatActivity) context), requestCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
