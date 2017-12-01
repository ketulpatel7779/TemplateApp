package com.mastertemplate.utils.fragment;

import android.support.v4.app.FragmentActivity;

import com.mastertemplate.base.BaseFragment;
import com.mastertemplate.utils.CommonUtils;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class FragmentManager {

    /**
    * Push/insert fragment into stack
    * @param fragment is parent fragment
    * @param newFragment is child fragment
    */

    public static void push(BaseFragment fragment, SupportFragment newFragment) {
        try {
            CommonUtils.log("Fragment Pushed");
            fragment.start(newFragment);
            CommonUtils.hideSoftInput(fragment.getSupportActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
    * used for set root fragment
    * @param fragment is current fragment
    * @param containerID is for id of fragment
    * @param BaseFragment is parent fragment
    */
    public static void setRoot(BaseFragment fragment, int containerID, SupportFragment BaseFragment) {
        CommonUtils.log("Fragment Root set");
        try {
            fragment.loadRootFragment(containerID, BaseFragment);
        } catch (Exception e) {

        }
        CommonUtils.hideSoftInput(fragment.getSupportActivity());
    }

    /**
    * used for pop/delete fragment from stack
    * @param fragment is for fragment to delete
    */

    public static void pop(BaseFragment fragment) {
        try {
            fragment.pop();
            CommonUtils.hideSoftInput(fragment.getSupportActivity());
            CommonUtils.log("Fragment Poped");
            if (isBackStackEmpty(fragment.getSupportActivity())) {
            }
        } catch (Exception e) {

        }
    }

    /**
    * used for check the stack is empty or not
    */

    public static boolean isBackStackEmpty(FragmentActivity activity) {
        if (activity != null) {
            CommonUtils.log(" Fragment :  back count = " + activity.getSupportFragmentManager().getBackStackEntryCount());
            return activity.getSupportFragmentManager().getBackStackEntryCount() <= 1;
        }
        return true;
    }

    /**
    * remove all chile fragment and push main fragment in stack
    * @param rootFragmentClass for root fragment
    * @param toFragment for base fragment in stack
    */

    public static <T extends SupportFragment> void popToRootAndPush(final SupportActivity activity,
                                                                    Class<T> rootFragmentClass,
                                                                    final BaseFragment toFragment) {
        SupportFragment fragment = activity.findFragment(toFragment.getClass());
        if (fragment == null) {
            activity.popTo(rootFragmentClass, false, new Runnable() {
                @Override
                public void run() {
                    activity.start(toFragment);
                }
            });
        } else {
            // SingleTask start
            activity.start(fragment, SupportFragment.SINGLETASK);
        }
    }

    /**
     * pop to root fragment
     * @param rootFragmentClass for root fragment
     */

    public static <T extends SupportFragment> void popToRoot(SupportActivity activity, Class<T> rootFragmentClass) {
        SupportFragment fragment = activity.findFragment(rootFragmentClass);
        activity.start(fragment, SupportFragment.SINGLETASK);
    }
}