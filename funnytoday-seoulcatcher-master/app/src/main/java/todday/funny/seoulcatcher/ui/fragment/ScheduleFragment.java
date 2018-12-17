package todday.funny.seoulcatcher.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executors;

import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.ScheduleBinding;
import todday.funny.seoulcatcher.interactor.OnEduDateListener;
import todday.funny.seoulcatcher.interactor.OnListISizeZero;
import todday.funny.seoulcatcher.interactor.OnScheduleListener;
import todday.funny.seoulcatcher.model.EduDate;
import todday.funny.seoulcatcher.model.Schedule;
import todday.funny.seoulcatcher.server.ServerDataController;
import todday.funny.seoulcatcher.ui.adapter.ScheduleAdapter;
import todday.funny.seoulcatcher.util.CommonDecorator;
import todday.funny.seoulcatcher.util.DateFormat;
import todday.funny.seoulcatcher.util.EventDecorator;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.util.SaturdayDecorator;
import todday.funny.seoulcatcher.util.SundayDecorator;
import todday.funny.seoulcatcher.util.TodayDecorator;
import todday.funny.seoulcatcher.viewmodel.ScheduleViewModel;


public class ScheduleFragment extends Fragment {
    private ScheduleBinding binding;
    private ScheduleViewModel model;
    private Context context;
    private MaterialCalendarView calendarView = null;
    private SundayDecorator sundayDecorator = new SundayDecorator();
    private SaturdayDecorator saturdayDecorator = new SaturdayDecorator();
    private CommonDecorator commonDecorator = new CommonDecorator();
    private TodayDecorator todayDecorator = new TodayDecorator();

    private RecyclerView recyclerView;
    private ScheduleAdapter adapter;

    private ArrayList<Schedule> schedulesLists = new ArrayList<>();
    private ArrayList<EduDate> eduDatesLists;

    private ServerDataController serverDataController = ServerDataController.getInstance(getContext());


    public static ScheduleFragment newInstance() {
        Bundle args = new Bundle();
        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule, container, false);
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            model = new ScheduleViewModel(getContext());
            binding.setModel(model);
        }

        View view = binding.getRoot();
        context = container.getContext();

        calendarView = view.findViewById(R.id.calendarView);
        recyclerView = view.findViewById(R.id.scheduleFragment_recyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        registerBroadCast();

        getScheduleDataBase();
        setEducationDate();


        return view;
    }

    private void registerBroadCast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Keys.ADD_SCHEDULE);
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity() != null && broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }

    private void setEducationDate() {
        serverDataController.getEducationDate(new OnEduDateListener() {
            @Override
            public void onComplete(ArrayList<EduDate> list) {
                eduDatesLists = list;
                settingCalendar();
            }
        });
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Keys.ADD_SCHEDULE)) {
                model.isSchedule = false;
                Schedule schedule = intent.getParcelableExtra(Keys.SCHEDULE);
                schedulesLists.add(schedule);
                adapter.notifyDataSetChanged();
            }
        }
    };

    private void getScheduleDataBase() {
        String uid = FirebaseAuth.getInstance().getUid();
        serverDataController.getUserSchedule(uid, new OnScheduleListener() {
            @Override
            public void onComplete(ArrayList<Schedule> scheduleList) {

                if (scheduleList.size() == 0) {
                    model.isSchedule = true;
                } else {
                    model.isSchedule = false;

                }
                schedulesLists = scheduleList;
                adapter = new ScheduleAdapter(getContext(), schedulesLists, onListISizeZero);
                recyclerView.setAdapter(adapter);

            }
        });
    }


    private void settingCalendar() {
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                for (int i = 0; i < eduDatesLists.size(); i++) {
                    if((DateFormat.getStringCalendar(date.getCalendar())).equals(eduDatesLists.get(i).getDate())) {
                        model.openScheduleInfo(DateFormat.getStringCalendar(date.getCalendar()));
                    }
                }
            }
        });
        if (eduDatesLists.size() != 0) {
            new CheckPointCalender(eduDatesLists).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        calendarView.addDecorators(commonDecorator, sundayDecorator, saturdayDecorator, todayDecorator);
    }

    private OnListISizeZero onListISizeZero = new OnListISizeZero() {
        @Override
        public void sizeZero() {
            model.isSchedule = true;
        }
    };

    private class CheckPointCalender extends AsyncTask<Void, Void, ArrayList<CalendarDay>> {

        private ArrayList<EduDate> timeResult;
        private ArrayList<CalendarDay> list = new ArrayList<CalendarDay>();

        public CheckPointCalender(ArrayList<EduDate> timeResult) {
            this.timeResult = timeResult;
            timeResult.add(timeResult.get(timeResult.size() - 1));
        }

        @Override
        protected ArrayList<CalendarDay> doInBackground(Void... voids) {
            for (EduDate eduDate : timeResult) {
                String time = eduDate.getDate();
                Calendar calendar = DateFormat.getCalendarString(time);
                list.add(CalendarDay.from(calendar));
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            calendarView.addDecorator(new EventDecorator(Color.GREEN, list, context));
        }
    }

}
