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

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.mastertemplate.R;
import com.mastertemplate.base.BaseActivity;
import com.mastertemplate.ui.dashboard.NavigationDrawerActivity;
import com.mastertemplate.ui.login.LoginActivity;

/**
 * Displays an Login or dashboard screen.
 */
public class SplashActivity extends BaseActivity implements SplashContract.View {
    SplashPresenter splashPresenter;
    private ImageView imgSplashLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
        splashPresenter = new SplashPresenter(this);
        imgSplashLogo.animate().alpha(0).setDuration(0).start();
        splashPresenter.startSplashTimer(5000);
    }

    private void initViews() {
        imgSplashLogo = (ImageView) findViewById(R.id.img_splash_logo);
    }

    @Override
    public void startLoginActivity() {
        Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void startDashboardActivity() {
        Intent loginIntent = new Intent(SplashActivity.this, NavigationDrawerActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void startLogoAnimation() {
        imgSplashLogo.animate().alpha(255).scaleXBy(0.5f).scaleYBy(0.5f).setDuration(2500).start();
    }
}
