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

import android.support.annotation.NonNull;

import com.mastertemplate.R;
import com.mastertemplate.callback.OnResponse;
import com.mastertemplate.data.DataManager;
import com.mastertemplate.data.api.model.RegisterResponse;
import com.mastertemplate.data.api.model.UploadResponse;
import com.mastertemplate.utils.CommonUtils;

import java.io.File;
import java.util.HashMap;


/**
 * Listens to user actions from the UI ,retrieves the data and updates
 * the UI as required.
 */
public class RegisterPresenter implements RegisterContract.Presenter {

    @NonNull
    private final RegisterContract.View mRegisterView;

    public RegisterPresenter(@NonNull RegisterContract.View loginView) {
        mRegisterView = loginView;
    }

    String TAG = "Register";

    @Override
    public void registerUser(String email, String password, String confirmPassword) {
        if (isDataValid(email, password, confirmPassword)) {
            mRegisterView.showLoading();
            HashMap<String, String> map = new HashMap<>();
            map.put("email", email);
            map.put("password", password);
            DataManager.registerUser(map, new OnResponse<RegisterResponse>() {
                @Override
                public void onSuccess(RegisterResponse response) {
                    mRegisterView.hideLoading();
                    mRegisterView.startDashboardActivity();
                    mRegisterView.showSuccessMsg(R.string.message_register_success);
                }

                @Override
                public void onError(Throwable error, String msg) {
                    mRegisterView.hideLoading();
                    mRegisterView.showErrorMsg(msg);
                }
            });
        }
    }

    @Override
    public void uploadImage(String email, String password, String confirmPassword, File imageFile) {

        if (isDataValid(email, password, confirmPassword)) {
            mRegisterView.showLoading();
            HashMap<String, String> parmMap = new HashMap<>();
            parmMap.put("description", "Register image");
            parmMap.put("user_id", "80");
            parmMap.put("club_id", "643");
            HashMap<String, File> fileMap = new HashMap<>();
            fileMap.put("image_url", imageFile);
            DataManager.uploadFile(parmMap,fileMap, new OnResponse<UploadResponse>() {
                @Override
                public void onSuccess(UploadResponse response) {
                    CommonUtils.log(response.getMessage());
                    CommonUtils.log("" + response.isStatus());
                    mRegisterView.hideLoading();
                }

                @Override
                public void onError(Throwable error, String msg) {
                    mRegisterView.hideLoading();

                }
            });
        }

    }

    private boolean isDataValid(String email, String password, String confirmPassword) {
        if (email.isEmpty() || !CommonUtils.isEmailValid(email)) {
            mRegisterView.showErrorMsg(R.string.error_invalid_email);
            return false;
        }
        if (password.isEmpty()) {
            mRegisterView.showErrorMsg(R.string.error_blank_password);
            return false;
        }
        if (confirmPassword.isEmpty()) {
            mRegisterView.showErrorMsg(R.string.error_blank_confirm_password);
            return false;
        }
        if (password.length() <= 6) {
            mRegisterView.showErrorMsg(R.string.error_password_length);
            return false;
        }
        if (!password.equals(confirmPassword)) {
            mRegisterView.showErrorMsg(R.string.error_mismatch_password);
            return false;
        }
        return true;
    }

}
