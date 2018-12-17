package todday.funny.seoulcatcher.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.interactor.OnListISizeZero;
import todday.funny.seoulcatcher.model.Schedule;
import todday.funny.seoulcatcher.server.ServerDataController;
import todday.funny.seoulcatcher.ui.dialog.AlertDialogCreate;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.util.SendBroadcast;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Schedule> schedules;

    private Context context;
    private OnListISizeZero onListISizeZero;

    public ScheduleAdapter(Context context, ArrayList<Schedule> schedules, OnListISizeZero onListISizeZero) {
        this.context = context;
        this.schedules = schedules;
        this.onListISizeZero = onListISizeZero;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule, viewGroup, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        ScheduleViewHolder scheduleViewHolder = (ScheduleViewHolder) viewHolder;
        final Schedule schedule = schedules.get(position);

        scheduleViewHolder.textView.setText(schedule.getDate());
        scheduleViewHolder.locationName.setText(schedule.getName());

        scheduleViewHolder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogCreate.getInstance(context).deleteSchedule(schedule.getDate() + " " + schedule.getName(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ServerDataController.getInstance(context).deleteUserSchedule(schedule, new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                schedules.remove(position);
                                if (schedules.size() == 0) {
                                    onListISizeZero.sizeZero();
                                }
                                SendBroadcast.schedule(context, Keys.DELETE_SCHEDULE, null);
                                notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    private class ScheduleViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private TextView locationName;
        private LinearLayout btn_cancel;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.scheduleItem_textView);
            locationName = itemView.findViewById(R.id.scheduleItem_name);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
        }
    }

}
