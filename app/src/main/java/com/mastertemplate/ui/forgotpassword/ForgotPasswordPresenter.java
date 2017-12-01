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

import android.support.annotation.NonNull;

import com.mastertemplate.R;
import com.mastertemplate.callback.OnResponse;
import com.mastertemplate.data.DataManager;
import com.mastertemplate.data.api.model.ForgotPasswordResponse;
import com.mastertemplate.utils.CommonUtils;

import java.util.HashMap;


/**
 * Listens to user actions from the UI ,retrieves the data and updates
 * the UI as required.
 */
public class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter {

    @NonNull
    private final ForgotPasswordContract.View mForgotPasswordView;

    public ForgotPasswordPresenter(@NonNull ForgotPasswordContract.View forgotPasswordView) {
        mForgotPasswordView = forgotPasswordView;
    }

    @Override
    public void forgotPassword(String email) {
        if (isDataValid(email)) {
            mForgotPasswordView.showLoading();
            HashMap<String, String> map = new HashMap<>();
            map.put("email_address", email);
            DataManager.forgotPassword(map, new OnResponse<ForgotPasswordResponse>() {
                @Override
                public void onSuccess(ForgotPasswordResponse response) {
                    mForgotPasswordView.hideLoading();
                    mForgotPasswordView.startLoginActivity();
                    mForgotPasswordView.showSuccessMsg(R.string.message_success_forgot_password);
                }

                @Override
                public void onError(Throwable error, String msg) {
                    mForgotPasswordView.hideLoading();
                    mForgotPasswordView.showErrorMsg(msg);
                }
            });
        }
    }

    private boolean isDataValid(String email) {
        if (email.isEmpty() || !CommonUtils.isEmailValid(email)) {
            mForgotPasswordView.showErrorMsg(R.string.error_invalid_email);
            return false;
        }
        return true;
    }
}
