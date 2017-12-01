package com.mastertemplate.ui.dashboard;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.mastertemplate.R;
import com.mastertemplate.base.BaseActivity;
import com.mastertemplate.base.BaseFragment;
import com.mastertemplate.callback.OnRecycleViewItemClickListener;
import com.mastertemplate.ui.dashboard.adapter.DrawerItemData;
import com.mastertemplate.ui.dashboard.adapter.DrawerListAdapter;
import com.mastertemplate.ui.dashboard.fragments.about.AboutFragment;
import com.mastertemplate.ui.dashboard.fragments.home.HomeFragment;
import com.mastertemplate.utils.CommonUtils;
import com.mastertemplate.utils.events.DrawerItemSelectedEvent;
import com.mastertemplate.utils.fragment.FragmentManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.mastertemplate.utils.events.EventConstants.DRAWER_EVENT;

public class NavigationDrawerActivity extends BaseActivity implements NavigationDrawerContract.View {

    DrawerListAdapter drawerAdapter;
    BaseFragment rootFragment;
    private DrawerLayout drawerLayout;
    private FrameLayout fragmentRootContainer;
    private LinearLayout drawer;
    private RecyclerView listDrawerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        initView();
        initRootFragments();
        setUpDrawer();
    }

    /**
     * set up navigation drawer
     */
    private void setUpDrawer() {
        /* uncomment this line to restrice drawer from opening on finger slide
        *  drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);*/
        drawerAdapter = new DrawerListAdapter(this);
        drawerAdapter.setItemClickListener(new OnRecycleViewItemClickListener<DrawerItemData>() {
            @Override
            public void onItemClick(int position, DrawerItemData itemData) {
                if (!itemData.isSelected()) {
                    startFragment(position);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        listDrawerItem.setLayoutManager(new LinearLayoutManager(this));
        listDrawerItem.setAdapter(drawerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Checks which fragment to start based on position
     */
    private void startFragment(int position) {
        switch (position) {
            case 0:
                FragmentManager.popToRoot(this, HomeFragment.class);
                break;
            case 1:
                AboutFragment aboutFragment = new AboutFragment();
                Bundle args = new Bundle();
                args.putInt(AboutFragment.ARG_COUNT, 1);
                aboutFragment.setArguments(args);
                FragmentManager.popToRootAndPush(this, HomeFragment.class, aboutFragment);
                drawerAdapter.setSelectedItem(position);
                break;
            case 4:

                break;
            default:
                showErrorMsg("Under development.");
        }
    }

    /**
     * initialize root fragment view
     */
    private void initRootFragments() {
        rootFragment = new HomeFragment();
        loadRootFragment(R.id.fragment_container, rootFragment);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    /**
     * Method will execute whenever @{@link EventBus} post any DRAWER_EVENT
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDrawerButtonPresed(String event) {
        if (event.equalsIgnoreCase(DRAWER_EVENT)) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
    }

    /**
     * Method will execute whenever @{@link EventBus} post any @{@link DrawerItemSelectedEvent}
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setDrawerItemSelected(DrawerItemSelectedEvent event) {
        drawerAdapter.setSelectedItem(event.getPosition());
    }

    /**
     * Method will execute whenever user press back button
     * it will close Navigation drawer if open or
     * pop fragment if there is any in back stack or
     * else ask user for confirmation to exit
     */
    @Override
    public void onBackPressedSupport() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                pop();
            } else {
                CommonUtils.showConfirmationDialog(NavigationDrawerActivity.this, "Exit", "Are you sure you want to quit?", "EXIT", "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }, null);
            }
        }
    }

    /**
     * initialize views
     */
    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fragmentRootContainer = (FrameLayout) findViewById(R.id.fragment_container);
        drawer = (LinearLayout) findViewById(R.id.drawer);
        listDrawerItem = (RecyclerView) findViewById(R.id.rv_drawer_item);
    }
}
