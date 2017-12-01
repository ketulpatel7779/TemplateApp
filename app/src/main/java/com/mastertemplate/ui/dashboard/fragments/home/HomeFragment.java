package com.mastertemplate.ui.dashboard.fragments.home;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mastertemplate.R;
import com.mastertemplate.base.BaseFragment;
import com.mastertemplate.callback.OnRecycleViewItemClickListener;
import com.mastertemplate.ui.dashboard.adapter.MyAdapter;
import com.mastertemplate.utils.CommonUtils;
import com.mastertemplate.utils.events.DrawerItemSelectedEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.mastertemplate.utils.events.EventConstants.DRAWER_EVENT;

public class HomeFragment extends BaseFragment implements HomeContract.View {

    HomePresenter mPresenter;
    private ImageView imgActionbarMenuBtn;
    private TextView lblTitle;
    private ImageView imgActionbarActionbtn;
    private TextView lblDummy;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        initView(view);
        setOnClickListener();
        mPresenter = new HomePresenter(this);
        lblDummy.setText("Home");
        return view;
    }

    private void setOnClickListener() {
        imgActionbarMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(DRAWER_EVENT);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        mAdapter.setItemClickListener(new OnRecycleViewItemClickListener<String>() {
            @Override
            public void onItemClick(int position, String itemData) {
                CommonUtils.showSuccessToast(getActivity(),""+position);
            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        EventBus.getDefault().post(new DrawerItemSelectedEvent(0));
    }

    private void initView(View view) {
        imgActionbarMenuBtn = (ImageView) view.findViewById(R.id.img_actionbar_menu_btn);
        lblTitle = (TextView) view.findViewById(R.id.lbl_actionbar_title);
        imgActionbarActionbtn = (ImageView) view.findViewById(R.id.img_actionbar_action_btn);
        lblDummy = (TextView) view.findViewById(R.id.lbl_dummy);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        List<String> input = new ArrayList<>();
        for (int i = 0; i <= 50; i++) {
            input.add("Test" + i);
        }// define an adapter
        mAdapter = new MyAdapter(input);
        recyclerView.setAdapter(mAdapter);
    }
}