package todday.funny.seoulcatcher.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.BarcodeBinding;
import todday.funny.seoulcatcher.databinding.MembershipBinding;
import todday.funny.seoulcatcher.databinding.UserMembershipBinding;
import todday.funny.seoulcatcher.model.MemberShip;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.viewmodel.MembershipViewModel;
import todday.funny.seoulcatcher.viewmodel.UserViewModel;

public class MemberShipAdapter extends RecyclerView.Adapter {
    private int BARCODE_TYPE = 0;
    private int USER_TYPE = 1;
    private int MEMBERSHIP_TYPE = 2;

    private Context mContext;
    private List<Object> mItemList;

    public MemberShipAdapter(Context context, List<Object> itemList) {
        mContext = context;
        mItemList = itemList;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mItemList.get(position);
        if (item instanceof String) {
            return BARCODE_TYPE;
        } else if (item instanceof User) {
            return USER_TYPE;
        } else {
            return MEMBERSHIP_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == BARCODE_TYPE) {
            BarcodeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_barcode, viewGroup, false);
            return new BarcodeViewHolder(binding);
        } else if (viewType == USER_TYPE) {
            UserMembershipBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_user_membership, viewGroup, false);
            return new UserMemberShipViewHolder(binding);
        } else {
            MembershipBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_membership, viewGroup, false);
            return new MemberShipViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == BARCODE_TYPE) {

        } else if (viewType == USER_TYPE) {
            UserMemberShipViewHolder holder = (UserMemberShipViewHolder) viewHolder;
            User user = (User) mItemList.get(position);
            UserViewModel model = new UserViewModel(mContext, user);
            holder.setViewModel(model);
        } else if (viewType == MEMBERSHIP_TYPE) {
            MemberShipViewHolder holder = (MemberShipViewHolder) viewHolder;
            MemberShip memberShip = (MemberShip) mItemList.get(position);
            MembershipViewModel model = new MembershipViewModel(mContext, memberShip);
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

    private class BarcodeViewHolder extends RecyclerView.ViewHolder {
        private BarcodeBinding binding;

        public BarcodeViewHolder(@NonNull BarcodeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private class UserMemberShipViewHolder extends RecyclerView.ViewHolder {
        private UserMembershipBinding binding;

        public UserMemberShipViewHolder(@NonNull UserMembershipBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(UserViewModel viewModel) {
            binding.setModel(viewModel);
            binding.executePendingBindings();
        }
    }

    private class MemberShipViewHolder extends RecyclerView.ViewHolder {
        private MembershipBinding binding;

        public MemberShipViewHolder(@NonNull MembershipBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setViewModel(MembershipViewModel viewModel) {
            binding.setModel(viewModel);
            binding.executePendingBindings();
        }
    }
}
