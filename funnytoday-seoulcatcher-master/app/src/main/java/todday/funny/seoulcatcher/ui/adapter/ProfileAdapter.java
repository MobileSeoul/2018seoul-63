package todday.funny.seoulcatcher.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.ProfileEmptyBinding;
import todday.funny.seoulcatcher.databinding.ProfileScheduleBinding;
import todday.funny.seoulcatcher.databinding.UserBinding;
import todday.funny.seoulcatcher.model.Schedule;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.viewmodel.EmptyViewModel;
import todday.funny.seoulcatcher.viewmodel.ScheduleViewModel;
import todday.funny.seoulcatcher.viewmodel.UserViewModel;

public class ProfileAdapter extends RecyclerView.Adapter {
    private int USER_TYPE = 0;
    private int SCHEDULE_TYPE = 1;
    private int EMPTY_TYPE = 2;

    private Context mContext;
    private List<Object> mItemList;

    public ProfileAdapter(Context context, List<Object> itemList) {
        mContext = context;
        mItemList = itemList;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mItemList.get(position);
        if (item instanceof User) {
            return USER_TYPE;
        } else if (item instanceof Schedule) {
            return SCHEDULE_TYPE;
        } else {
            return EMPTY_TYPE;
        }
    }

    @Override
    public long getItemId(int position) {
        if (mItemList != null) {
            if (mItemList.get(position) instanceof User) {
                User user = (User) mItemList.get(position);
                if (user != null && user.getId() != null) {
                    return user.getId().hashCode();
                }
            }
        }
        return position;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == USER_TYPE) {
            UserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_user_profile, viewGroup, false);
            return new UserHeaderViewHolder(binding);
        } else if (viewType == SCHEDULE_TYPE) {
            ProfileScheduleBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_profile_schedule, viewGroup, false);
            return new ProfileScheduleViewHolder(binding);
        } else {
            ProfileEmptyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_empty_profile, viewGroup, false);
            return new ProfileEmptyViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == USER_TYPE) {
            UserHeaderViewHolder holder = (UserHeaderViewHolder) viewHolder;
            User user = (User) mItemList.get(position);
            UserViewModel model = new UserViewModel(mContext, user);
            holder.setViewModel(model);
        } else if (viewType == SCHEDULE_TYPE) {
            ProfileScheduleViewHolder holder = (ProfileScheduleViewHolder) viewHolder;
            Schedule schedule = (Schedule) mItemList.get(position);
            ScheduleViewModel model = new ScheduleViewModel(mContext, schedule);
            holder.setViewModel(model);
        } else if (viewType == EMPTY_TYPE) {
            ProfileEmptyViewHolder holder = (ProfileEmptyViewHolder) viewHolder;
            EmptyViewModel model = new EmptyViewModel(mContext, mContext.getString(R.string.empty_profile));
            holder.setViewModel(model);
        }
    }

    @Override
    public int getItemCount() {
        if (mItemList != null) {
            return mItemList.size();
        }
        return 0;
    }

    private class UserHeaderViewHolder extends RecyclerView.ViewHolder {
        private UserBinding binding;

        public UserHeaderViewHolder(@NonNull UserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(UserViewModel viewModel) {
            binding.setModel(viewModel);
            binding.executePendingBindings();
        }
    }

    private class ProfileScheduleViewHolder extends RecyclerView.ViewHolder {
        private ProfileScheduleBinding binding;

        public ProfileScheduleViewHolder(@NonNull ProfileScheduleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(ScheduleViewModel viewModel) {
            binding.setModel(viewModel);
            binding.executePendingBindings();
        }
    }

    private class ProfileEmptyViewHolder extends RecyclerView.ViewHolder {
        private ProfileEmptyBinding binding;

        public ProfileEmptyViewHolder(@NonNull ProfileEmptyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(EmptyViewModel model) {
            binding.setModel(model);
            binding.executePendingBindings();
        }
    }
}
