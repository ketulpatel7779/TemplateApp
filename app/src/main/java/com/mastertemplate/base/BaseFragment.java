package com.mastertemplate.base;


import android.support.v4.app.FragmentActivity;

import com.mastertemplate.utils.permission.Permission;

import me.yokeyword.fragmentation.SupportFragment;
/**
 * This fragment must be parent fragment of all the fragments in app.
 * this fragment contains common functionality that is common between all the child fragments.
 */
public abstract class BaseFragment extends SupportFragment implements BaseView {

    /**
     * @return parent activity
     */
    public FragmentActivity getSupportActivity() {
        return _mActivity;
    }

    /**
     * @return instance of fragment
     */
    public BaseFragment getSupportFragment() {
        return this;
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }

    @Override
    public void showErrorMsg(String msg) {
        ((BaseActivity) _mActivity).showErrorMsg(msg);
    }

    @Override
    public void showErrorMsg(int msg) {
        ((BaseActivity) _mActivity).showErrorMsg(msg);
    }

    @Override
    public void showSuccessMsg(String msg) {
        ((BaseActivity) _mActivity).showSuccessMsg(msg);
    }

    @Override
    public void showSuccessMsg(int msg) {
        ((BaseActivity) _mActivity).showSuccessMsg(msg);
    }

    @Override
    public void showLoading( ) {
        ((BaseActivity) _mActivity).showLoading();
    }

    @Override
    public void hideLoading( ) {
        ((BaseActivity) _mActivity).hideLoading();
    }

    @Override
    public void askPermissionIfRequire(String permission, String rationalMsg, Permission.PermissionListener permissionListener) {
        ((BaseActivity) _mActivity).askPermissionIfRequire(permission, rationalMsg, permissionListener);
    }

}