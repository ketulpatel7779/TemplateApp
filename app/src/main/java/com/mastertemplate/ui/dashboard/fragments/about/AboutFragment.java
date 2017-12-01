package com.mastertemplate.ui.dashboard.fragments.about;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mastertemplate.R;
import com.mastertemplate.base.BaseFragment;
import com.mastertemplate.utils.fragment.FragmentManager;

import org.greenrobot.eventbus.EventBus;

import static com.mastertemplate.utils.events.EventConstants.DRAWER_EVENT;

public class AboutFragment extends BaseFragment implements AboutContract.View {

    private ImageView imgActionbarMenuBtn;
    private TextView lblTitle;
    private ImageView imgActionbarActionbtn;
    private TextView lblDummy;

    AboutPresenter mPresenter;
    public static final String ARG_COUNT = "count";
    public int count = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        initView(view);
        setClickListeners();
        setUpActionBar();
        mPresenter = new AboutPresenter(this);
        if (getArguments() != null && getArguments().getInt(ARG_COUNT) != 0) {
            count = getArguments().getInt(ARG_COUNT);
        }
        lblDummy.setText("About" + count);
        return view;
    }

    private void setUpActionBar() {

    }

    private void setClickListeners() {
        lblDummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutFragment aboutFragment = new AboutFragment();
                Bundle args = new Bundle();
                args.putInt(AboutFragment.ARG_COUNT, count + 1);
                aboutFragment.setArguments(args);
                FragmentManager.push(AboutFragment.this, aboutFragment);
            }
        });
        if (count == 1) {
            imgActionbarMenuBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(DRAWER_EVENT);
                }
            });
        } else {
            imgActionbarMenuBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager.pop(AboutFragment.this);
                }
            });
        }

    }

    private void initView(View view) {
        imgActionbarMenuBtn = (ImageView) view.findViewById(R.id.img_actionbar_menu_btn);
        lblTitle = (TextView) view.findViewById(R.id.lbl_actionbar_title);
        imgActionbarActionbtn = (ImageView) view.findViewById(R.id.img_actionbar_action_btn);
        lblDummy = (TextView) view.findViewById(R.id.lbl_dummy);
    }
}