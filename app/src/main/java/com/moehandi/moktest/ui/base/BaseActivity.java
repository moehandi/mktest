package com.moehandi.moktest.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.moehandi.moktest.di.ApplicationComponent;
import com.moehandi.moktest.App;

/**
 * Created by moehandi on 22/5/19.
 */

public abstract class BaseActivity extends AppCompatActivity implements MvpView {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectComponent(((App) getApplication()).getApplicationComponent());
    }

    protected abstract void injectComponent(ApplicationComponent component);

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void finishView() {
        finish();
    }
}
