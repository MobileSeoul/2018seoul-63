package todday.funny.seoulcatcher.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import todday.funny.seoulcatcher.R;

public class AlertDialogCreate {
    private static volatile AlertDialogCreate singletonInstance = null;
    private AlertDialog.Builder alertDialog;
    private Context mContext;

    public static AlertDialogCreate getInstance(Context context) {
        if (singletonInstance == null) {
            synchronized (AlertDialogCreate.class) {
                if (singletonInstance == null) {
                    singletonInstance = new AlertDialogCreate(context);
                }
            }
        }
        return singletonInstance;
    }

    private AlertDialogCreate(Context context) {
        mContext = context;
        alertDialog = new AlertDialog.Builder(context);
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    public void logout(DialogInterface.OnClickListener clickListener) {
        try {
            alertDialog.setCancelable(true);
            alertDialog.setTitle(mContext.getString(R.string.account));
            alertDialog.setMessage(mContext.getString(R.string.msg_logout));
            alertDialog.setPositiveButton(mContext.getString(R.string.log_out), clickListener);
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void locationOn(DialogInterface.OnClickListener clickListener, DialogInterface.OnClickListener negetiveClickListener) {
        try {
            alertDialog.setCancelable(false);
            alertDialog.setTitle(R.string.location_settings);
            alertDialog.setMessage(R.string.msg_location_on);
            alertDialog.setPositiveButton(R.string.setting, clickListener);
            alertDialog.setNegativeButton(R.string.cancel, negetiveClickListener);
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSchedule(String message, DialogInterface.OnClickListener clickListener) {
        try {
            alertDialog.setCancelable(true);
            alertDialog.setTitle("교육을 취소하시겠습니까?");
            alertDialog.setMessage(message);
            alertDialog.setPositiveButton(mContext.getString(R.string.ok), clickListener);
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
