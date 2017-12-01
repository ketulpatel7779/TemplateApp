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

package com.mastertemplate.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mastertemplate.R;
import com.mastertemplate.base.BaseActivity;
import com.mastertemplate.ui.login.LoginActivity;
import com.mastertemplate.utils.CommonUtils;

/**
 * Displays an Forgot password screen.
 */
public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordContract.View, View.OnClickListener {
    ForgotPasswordPresenter forgotPasswordPresenter;
    EditText inputEmail;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initView();
        setClickListeners();
        forgotPasswordPresenter = new ForgotPasswordPresenter(this);
    }

    /*
     * set up click listeners
     */
    private void setClickListeners() {
        btnSubmit.setOnClickListener(this);
    }

    /*
     * initialize all views
     */
    private void initView() {
        inputEmail = (EditText) findViewById(R.id.input_email);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if (CommonUtils.isNetworkConnected(ForgotPasswordActivity.this)) {
                    forgotPasswordPresenter.forgotPassword(inputEmail.getText().toString());
                } else {
                    showErrorMsg(getResources().getString(R.string.error_no_connectivity));
                }
                break;
        }
    }

    @Override
    public void startLoginActivity() {
        Intent dashBoardIntent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        startActivity(dashBoardIntent);
        finish();
    }
}
