package todday.funny.seoulcatcher.viewmodel.bindingAdapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import todday.funny.seoulcatcher.ui.adapter.HistoryAdapter;
import todday.funny.seoulcatcher.ui.adapter.MemberShipAdapter;
import todday.funny.seoulcatcher.ui.adapter.ProfileAdapter;

public class AdapterBindingAdapter {
    @BindingAdapter({"setProfileAdapter"})
    public static void setProfileAdapter(RecyclerView view, List<Object> itemList) {

        Context context = view.getContext();
        RecyclerView.Adapter adapter = view.getAdapter();
        if (adapter != null && adapter instanceof ProfileAdapter) {
            Log.e("setWriteImageAdapter", "setItemList");
            adapter.notifyDataSetChanged();
            //  ((HomeListAdapter) adapter).setItemList(itemList);
        } else {
            LinearLayoutManager manager = new LinearLayoutManager(context);
            ProfileAdapter homeListAdapter = new ProfileAdapter(context, itemList);
            homeListAdapter.setHasStableIds(true);
            view.setLayoutManager(manager);
            view.setAdapter(homeListAdapter);
        }
    }

    @BindingAdapter({"setMemberShipAdapter"})
    public static void setMemberShipAdapter(RecyclerView view, List<Object> itemList) {
        Context context = view.getContext();
        RecyclerView.Adapter adapter = view.getAdapter();
        if (adapter != null && adapter instanceof MemberShipAdapter) {
            Log.e("setWriteImageAdapter", "setItemList");
            adapter.notifyDataSetChanged();
            //  ((HomeListAdapter) adapter).setItemList(itemList);
        } else {
            LinearLayoutManager manager = new LinearLayoutManager(context);
            MemberShipAdapter memberShipAdapter = new MemberShipAdapter(context, itemList);
            view.setLayoutManager(manager);
            view.setAdapter(memberShipAdapter);
        }
    }

    @BindingAdapter({"setHistoryAdapter"})
    public static void setHistoryAdapter(RecyclerView view, List<Object> itemList) {
        Context context = view.getContext();
        RecyclerView.Adapter adapter = view.getAdapter();
        if (adapter != null && adapter instanceof HistoryAdapter) {
            Log.e("setWriteImageAdapter", "setItemList");
            adapter.notifyDataSetChanged();
            //  ((HomeListAdapter) adapter).setItemList(itemList);
        } else {
            LinearLayoutManager manager = new LinearLayoutManager(context);
            HistoryAdapter memberShipAdapter = new HistoryAdapter(context, itemList);
            view.setLayoutManager(manager);
            view.setAdapter(memberShipAdapter);
        }
    }
}
