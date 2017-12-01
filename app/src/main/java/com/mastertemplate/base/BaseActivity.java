package com.mastertemplate.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mastertemplate.utils.CommonUtils;
import com.mastertemplate.utils.permission.Permission;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * This activity must be parent activity of all the activities in app.
 * this activity contains common functionality that is common between all the child activities.
 */
public abstract class BaseActivity extends SupportActivity implements BaseView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showErrorMsg(String msg) {
        CommonUtils.showErrorToast(getApplicationContext(), msg);
    }

    @Override
    public void showErrorMsg(int msg) {
        CommonUtils.showErrorToast(getApplicationContext(), getString(msg));
    }

    @Override
    public void askPermissionIfRequire(String permission, String rationalMsg, Permission.PermissionListener permissionListener) {
        Permission.askPermission(this, permission, rationalMsg, permissionListener);
    }

    @Override
    public void showSuccessMsg(String msg) {
        CommonUtils.showSuccessToast(getApplicationContext(), msg);
    }

    @Override
    public void showSuccessMsg(int msg) {
        CommonUtils.showSuccessToast(getApplicationContext(), getString(msg));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        CommonUtils.showLoading(BaseActivity.this);
    }

    @Override
    public void hideLoading() {
        CommonUtils.closeLoading();
    }
}