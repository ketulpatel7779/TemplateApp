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


import com.mastertemplate.base.BasePresenter;
import com.mastertemplate.base.BaseView;

import java.io.File;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface RegisterContract {

    interface View extends BaseView {

        /**
         * start dashboard activity
         */
        void startDashboardActivity();

    }

    interface Presenter extends BasePresenter {

        /**
         * register user validates email, password, confirm password and on success call
           webservice
         */
        void registerUser(String email, String pswd, String confPswd);
        void uploadImage(String email, String pswd, String confPswd, File imageFile);

    }
}
