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

package com.mastertemplate.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mastertemplate.R;
import com.mastertemplate.base.BaseActivity;
import com.mastertemplate.ui.dashboard.NavigationDrawerActivity;
import com.mastertemplate.ui.forgotpassword.ForgotPasswordActivity;
import com.mastertemplate.ui.register.RegisterActivity;
import com.mastertemplate.utils.CommonUtils;

/**
 * Displays an Login or dashboard screen.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener {
    LoginPresenter loginPresenter;
    EditText inputEmail, inputPswd;
    Button btnLogin,btn_register;
    TextView   lblForgorPswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setClickListeners();
        loginPresenter = new LoginPresenter(this);
    }

    /**
     * set up click listeners to handle click events
     */
    private void setClickListeners() {
        btnLogin.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        lblForgorPswd.setOnClickListener(this);
    }

    /**
     * initialize all views
     */
    private void initView() {
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPswd = (EditText) findViewById(R.id.input_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        lblForgorPswd = (TextView) findViewById(R.id.btn_forgot_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }

    @Override
    public void startDashboardActivity() {
        Intent dashBoardIntent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
        startActivity(dashBoardIntent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                CommonUtils.hideSoftInput(LoginActivity.this);
                if (CommonUtils.isNetworkConnected(LoginActivity.this)) {
                    loginPresenter.logInUser(inputEmail.getText().toString(), inputPswd.getText().toString());
                } else {
                    showErrorMsg(getResources().getString(R.string.error_no_connectivity));
                }
                break;
            case R.id.btn_register:
                CommonUtils.hideSoftInput(LoginActivity.this);
                Intent dashBoardIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(dashBoardIntent);
                break;
            case R.id.btn_forgot_password:
                CommonUtils.hideSoftInput(LoginActivity.this);
                Intent forgotPasswordIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotPasswordIntent);
                break;
        }
    }

}
