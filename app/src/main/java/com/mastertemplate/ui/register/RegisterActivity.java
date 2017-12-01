/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mastertemplate.ui.register;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mastertemplate.R;
import com.mastertemplate.base.BaseActivity;
import com.mastertemplate.ui.dashboard.NavigationDrawerActivity;
import com.mastertemplate.utils.CommonUtils;
import com.mastertemplate.utils.permission.Permission;

import java.io.File;

/**
 * Displays an Registration screen.
 */
public class RegisterActivity extends BaseActivity implements RegisterContract.View, View.OnClickListener {
    RegisterPresenter registerPresenter;
    Uri dataUri = null;
    private ImageView ivUserProfilePic;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputConfirmPassword;
    private Button btnRegister;
    private int REQ_CODE_GALLARY = 2;
    private int REQ_CODE_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initView();
        setClickListeners();
        registerPresenter = new RegisterPresenter(this);
    }

    /**
     * set up click listeners to handle click events
     */
    private void setClickListeners() {
        btnRegister.setOnClickListener(this);
        ivUserProfilePic.setOnClickListener(this);
    }

    /**
     * initialize all views
     */
    private void initView() {
        ivUserProfilePic = (ImageView) findViewById(R.id.iv_user_profile_pic);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputConfirmPassword = (EditText) findViewById(R.id.input_confirm_password);
        btnRegister = (Button) findViewById(R.id.btn_register);
    }

    @Override
    public void startDashboardActivity() {
        Intent dashBoardIntent = new Intent(RegisterActivity.this, NavigationDrawerActivity.class);
        startActivity(dashBoardIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_CAMERA && resultCode == RESULT_OK) {
            String imgPath = dataUri.getPath();
            Glide
                    .with(RegisterActivity.this)
                    .load(imgPath)
                    .into(ivUserProfilePic);

        } else if (requestCode == REQ_CODE_GALLARY && resultCode == RESULT_OK) {
            dataUri = data.getData();
            Glide
                    .with(RegisterActivity.this)
                    .load(dataUri.toString())
                    .into(ivUserProfilePic);
            if (dataUri != null) {

                registerPresenter.uploadImage(inputEmail.getText().toString(), inputPassword.getText().toString(), inputConfirmPassword.getText().toString(), new File(CommonUtils.getRealPathFromURI(dataUri, getApplicationContext())));
            }

        }
    }

    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (CommonUtils.isNetworkConnected(RegisterActivity.this)) {
                    registerPresenter.registerUser(inputEmail.getText().toString(), inputPassword.getText().toString(), inputConfirmPassword.getText().toString());
                } else {
                    showErrorMsg(getResources().getString(R.string.error_no_connectivity));
                }

                break;
            case R.id.iv_user_profile_pic:

                CommonUtils.showConfirmationDialog(RegisterActivity.this, "Confirmation", "Select Image from camera or gallery",
                        "Camera", "Gallery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {

                                askPermissionIfRequire(Manifest.permission.WRITE_EXTERNAL_STORAGE, "This is required for use camera", new Permission.PermissionListener() {
                                    @Override
                                    public void granted() {
                                        dataUri = CommonUtils.openCamera(RegisterActivity.this);
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void denied() {
                                        CommonUtils.showErrorToast(RegisterActivity.this,"This permission is required for use camera in this app");
                                    }
                                });

                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CommonUtils.openGallery(RegisterActivity.this);
                                dialog.dismiss();
                            }
                        });
                break;
        }
    }

}
