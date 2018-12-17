package todday.funny.seoulcatcher.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.HistoryCallBinding;
import todday.funny.seoulcatcher.databinding.HistoryEmptyBinding;
import todday.funny.seoulcatcher.databinding.HistoryScheduleBinding;
import todday.funny.seoulcatcher.databinding.HistoryUserBinding;
import todday.funny.seoulcatcher.model.Call;
import todday.funny.seoulcatcher.model.History;
import todday.funny.seoulcatcher.model.Schedule;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.viewmodel.EmptyViewModel;
import todday.funny.seoulcatcher.viewmodel.HistoryListViewModel;
import todday.funny.seoulcatcher.viewmodel.HistoryViewModel;

public class HistoryAdapter extends RecyclerView.Adapter {
    private int EMPTY_TYPE = 0;
    private int USER_TYPE = 1;
    private int CALL_TYPE = 2;
    private int SCHEDULE_TYPE = 3;


    private Context mContext;
    private List<Object> mItemList;

    public HistoryAdapter(Context context, List<Object> itemList) {
        mContext = context;
        mItemList = itemList;
    }


    @Override
    public int getItemViewType(int position) {
        Object item = mItemList.get(position);
        if (item instanceof History) {
            History history = (History) item;
            if (history.getUser() != null) {
                return USER_TYPE;
            } else if (history.getCall() != null) {
                return CALL_TYPE;
            } else if (history.getSchedule() != null) {
                return SCHEDULE_TYPE;
            }
        }
        return EMPTY_TYPE;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == USER_TYPE) {
            HistoryUserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_history_user, viewGroup, false);
            return new HistoryUserViewHolder(binding);
        } else if (viewType == CALL_TYPE) {
            HistoryCallBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_history_call, viewGroup, false);
            return new HistoryCallViewHolder(binding);
        } else if (viewType == SCHEDULE_TYPE) {
            HistoryScheduleBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_history_schedule, viewGroup, false);
            return new HistoryScheduleViewHolder(binding);
        }
        HistoryEmptyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_empty_history, viewGroup, false);
        return new HistoryEmptyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == USER_TYPE) {
            HistoryUserViewHolder holder = (HistoryUserViewHolder) viewHolder;
            History history = (History) mItemList.get(position);
            HistoryViewModel model = new HistoryViewModel(mContext, history);
            holder.setViewModel(model);
        } else if (viewType == CALL_TYPE) {
            HistoryCallViewHolder holder = (HistoryCallViewHolder) viewHolder;
            History history = (History) mItemList.get(position);
            HistoryViewModel model = new HistoryViewModel(mContext, history);
            holder.setViewModel(model);
        } else if (viewType == SCHEDULE_TYPE) {
            HistoryScheduleViewHolder holder = (HistoryScheduleViewHolder) viewHolder;
            History history = (History) mItemList.get(position);
            HistoryViewModel model = new HistoryViewModel(mContext, history);
            holder.setViewModel(model);
        } else {
            HistoryEmptyViewHolder holder = (HistoryEmptyViewHolder) viewHolder;
            EmptyViewModel model = new EmptyViewModel(mContext, mContext.getString(R.string.empty_history));
            holder.setViewModel(model);
        }

    }


    @Override
    public int getItemCount() {
        if (mItemList != null) {
            Log.e("test", String.valueOf(mItemList.size()));
            return mItemList.size();
        }
        return 0;
    }


    private class HistoryUserViewHolder extends RecyclerView.ViewHolder {
        private HistoryUserBinding binding;

        public HistoryUserViewHolder(@NonNull HistoryUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(HistoryViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }

    private class HistoryCallViewHolder extends RecyclerView.ViewHolder {
        private HistoryCallBinding binding;

        public HistoryCallViewHolder(@NonNull HistoryCallBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(HistoryViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }

    private class HistoryScheduleViewHolder extends RecyclerView.ViewHolder {
        private HistoryScheduleBinding binding;

        public HistoryScheduleViewHolder(@NonNull HistoryScheduleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(HistoryViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }

    private class HistoryEmptyViewHolder extends RecyclerView.ViewHolder {
        private HistoryEmptyBinding binding;

        public HistoryEmptyViewHolder(@NonNull HistoryEmptyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(EmptyViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }
}
