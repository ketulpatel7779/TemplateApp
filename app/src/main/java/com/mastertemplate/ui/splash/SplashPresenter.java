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

package com.mastertemplate.ui.splash;

import android.os.Handler;
import android.support.annotation.NonNull;


/**
 * Listens to user actions from the UI ,retrieves the data and updates
 * the UI as required.
 */
public class SplashPresenter implements SplashContract.Presenter {

    @NonNull
    private final SplashContract.View mSplashView;

    public SplashPresenter(@NonNull SplashContract.View splashView) {
        mSplashView = splashView;
    }


    @Override
    public void startSplashTimer(long milli) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (1 == 1) {
                    mSplashView.startLoginActivity();
                } else {
                    mSplashView.startDashboardActivity();
                }
            }
        }, milli);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSplashView.startLogoAnimation();
            }
        }, milli / 3);
    }
}
