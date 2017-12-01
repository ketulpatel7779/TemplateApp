package com.mastertemplate.ui.dashboard.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mastertemplate.R;
import com.mastertemplate.callback.OnRecycleViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;

public class DrawerListAdapter extends RecyclerView.Adapter {
    private List<DrawerItemData> dataList;
    private SupportActivity activity;
    private OnRecycleViewItemClickListener<DrawerItemData> itemClickListener;

    public DrawerListAdapter(SupportActivity activity) {
        this.activity = activity;
        this.dataList = getDrawerListData();
    }

    public void setItemClickListener(OnRecycleViewItemClickListener<DrawerItemData> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public List<DrawerItemData> getItems() {
        return this.dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_navigation_drawer, viewGroup, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int pos) {
        final CustomViewHolder holder_type = (CustomViewHolder) holder;
        final DrawerItemData list = dataList.get(pos);
        holder_type.lblTitle.setText(list.getTitle());
        if (list.isSelected()) {
            holder_type.lblTitle.setTextColor(activity.getResources().getColor(R.color.accent));
            holder_type.imgIcon.setImageResource(list.getIconSelected());
            holder_type.containerRootview.setBackgroundColor(Color.WHITE);
        } else {
            holder_type.lblTitle.setTextColor(activity.getResources().getColor(R.color.white));
            holder_type.imgIcon.setImageResource(list.getIconDeselected());
            holder_type.containerRootview.setBackgroundColor(Color.TRANSPARENT);
        }
        holder_type.containerRootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(dataList.get(holder_type.getAdapterPosition()).getPosition(), dataList.get(holder_type.getAdapterPosition()));
                }
            }
        });
    }

    public void setSelectedItem(int pos) {
        if (!dataList.get(pos).isSelected()) {
            for (int i = 0; i < dataList.size(); i++) {
                dataList.get(i).setSelected(false);
            }
            dataList.get(pos).setSelected(true);
            notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private List<DrawerItemData> getDrawerListData() {
        ArrayList<DrawerItemData> drawerItems = new ArrayList<>();
        drawerItems.add(DrawerItemData.newDrawerItemData().position(0).title("HOME").isSelected(true).iconSelected(R.drawable.fragmentation_ic_right).iconDeselected(R.drawable.fragmentation_ic_expandable).build());
        drawerItems.add(DrawerItemData.newDrawerItemData().position(1).title("ABOUT").isSelected(false).iconSelected(R.drawable.fragmentation_ic_right).iconDeselected(R.drawable.fragmentation_ic_expandable).build());
        drawerItems.add(DrawerItemData.newDrawerItemData().position(2).title("SETTINGS").isSelected(false).iconSelected(R.drawable.fragmentation_ic_right).iconDeselected(R.drawable.fragmentation_ic_expandable).build());
        drawerItems.add(DrawerItemData.newDrawerItemData().position(3).title("TERMS").isSelected(false).iconSelected(R.drawable.fragmentation_ic_right).iconDeselected(R.drawable.fragmentation_ic_expandable).build());
        drawerItems.add(DrawerItemData.newDrawerItemData().position(4).title("LOGOUT").isSelected(false).iconSelected(R.drawable.fragmentation_ic_right).iconDeselected(R.drawable.fragmentation_ic_expandable).build());
        return drawerItems;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout containerRootview;
        private ImageView imgIcon;
        private TextView lblTitle;

        public CustomViewHolder(View view) {
            super(view);
            containerRootview = (LinearLayout) view.findViewById(R.id.container_rootview);
            imgIcon = (ImageView) view.findViewById(R.id.img_drawer_list_icon);
            lblTitle = (TextView) view.findViewById(R.id.lbl_drawer_item_title);
        }
    }
}