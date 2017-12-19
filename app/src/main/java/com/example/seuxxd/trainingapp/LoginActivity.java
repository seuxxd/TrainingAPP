package com.example.seuxxd.trainingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import Constant.BroadcastConstant;
import Fragment.LoginFragment;
import Fragment.RegisterFragment;

public class LoginActivity extends AppCompatActivity {

    private LoginFragment mLoginFragment = new LoginFragment();
    private RegisterFragment mRegisterFragment = new RegisterFragment();
    private FragmentManager mFragmentManager = getSupportFragmentManager();


//    处理切换登录和注册界面的广播接收器
    private ChangeLoginRegisterReceiver mChangeLoginRegisterReceiver = new ChangeLoginRegisterReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mFragmentManager.beginTransaction()
                .replace(R.id.start_container,mLoginFragment)
                .commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(BroadcastConstant.CHANGE_TO_LOGIN);
        mFilter.addAction(BroadcastConstant.CHANGE_TO_REGISTER);
        registerReceiver(mChangeLoginRegisterReceiver,mFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mChangeLoginRegisterReceiver);
    }

    class ChangeLoginRegisterReceiver extends BroadcastReceiver{
        public ChangeLoginRegisterReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String mAction = intent.getAction();
            switch (mAction){
                case BroadcastConstant.CHANGE_TO_REGISTER:
                    mFragmentManager.beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.start_container,mRegisterFragment)
                            .commit();
                    break;
                case BroadcastConstant.CHANGE_TO_LOGIN:
                    mFragmentManager.beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.start_container,mLoginFragment)
                            .commit();
                    break;
            }
        }
    }
}
