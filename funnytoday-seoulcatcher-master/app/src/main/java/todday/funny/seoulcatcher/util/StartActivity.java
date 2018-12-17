package todday.funny.seoulcatcher.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;


public class StartActivity {
    public static void start(Context context, Intent intent, View transitionView, boolean isFinish) {
        String transitionName = null;
        if (transitionView != null) {
            transitionName = ViewCompat.getTransitionName(transitionView);
        }
        if (transitionName != null && context instanceof Activity) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, transitionView, transitionName);
            context.startActivity(intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }
        if (isFinish) {
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        }
    }

    public static void startSingle(Context context, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void startSingle(Context context, Intent intent, boolean isFinish) {
        if (isFinish) {
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
    public static void startStack(Context context , Intent intent, boolean isFinish) {
        if (isFinish) {
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        }
        context.startActivity(intent);
    }
}
