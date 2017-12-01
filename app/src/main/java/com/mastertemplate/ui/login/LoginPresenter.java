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

import android.support.annotation.NonNull;

import com.mastertemplate.R;
import com.mastertemplate.callback.OnResponse;
import com.mastertemplate.data.DataManager;
import com.mastertemplate.data.api.model.LoginResponse;
import com.mastertemplate.utils.CommonUtils;

import java.util.HashMap;


/**
 * Listens to user actions from the UI ,retrieves the data and updates
 * the UI as required.
 */
public class LoginPresenter implements LoginContract.Presenter {

    @NonNull
    private final LoginContract.View mLoginView;

    public LoginPresenter(@NonNull LoginContract.View loginView) {
        mLoginView = loginView;
    }

    @Override
    public void logInUser(String email, String password) {
        if (isDataValid(email, password)) {
            mLoginView.showLoading();
            HashMap<String, String> map = new HashMap<>();
            map.put("email", email);
            map.put("password", password);
            DataManager.logInUser(map, new OnResponse<LoginResponse>() {
                @Override
                public void onSuccess(LoginResponse response) {
                    mLoginView.startDashboardActivity();
                    mLoginView.showSuccessMsg(R.string.message_success_login);
                    mLoginView.hideLoading();
                }

                @Override
                public void onError(Throwable error, String msg) {
                    mLoginView.hideLoading();
                    mLoginView.showErrorMsg(msg);
                }
            });

        }
    }

    private boolean isDataValid(String email, String password) {
        if (email.isEmpty() || !CommonUtils.isEmailValid(email)) {
            mLoginView.showErrorMsg(R.string.error_invalid_email);
            return false;
        }
        if (password.isEmpty()) {
            mLoginView.showErrorMsg(R.string.error_blank_password);
            return false;
        }
        if (password.length() <= 6) {
            mLoginView.showErrorMsg(R.string.error_password_length);
            return false;
        }
        return true;
    }
}
