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

package com.mastertemplate.ui.dashboard;

import android.support.annotation.NonNull;


/**
 * Listens to user actions from the UI ,retrieves the data and updates
 * the UI as required.
 */
public class DashboardPresenter implements NavigationDrawerContract.Presenter {

    @NonNull
    private final NavigationDrawerContract.View mView;

    public DashboardPresenter(@NonNull NavigationDrawerContract.View mView) {
        this.mView = mView;
    }

}
