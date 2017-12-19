package com.example.seuxxd.trainingapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Constant.BroadcastConstant;
import Constant.URLConstant;
import Fragment.EquipmentFragment;
import Fragment.ExamFragment;
import Fragment.MyInfoFragment;
import Fragment.IndexFragment;
import Internet.MessageService;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.MessageDetails;
import model.MessageResponse;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    private boolean mIsQuit = false;
//    私有Fragment用于加载
//    private LoginFragment mLoginFragment = new LoginFragment();
//    private RegisterFragment mRegisterFragment = new RegisterFragment();
    private IndexFragment mIndexFragment = new IndexFragment();
    private EquipmentFragment mEquipmentFragment = new EquipmentFragment();
    private ExamFragment mExamFragment = new ExamFragment();
    private MyInfoFragment mMyInfoFragment = new MyInfoFragment();
    private FragmentManager mFragmentManager = getSupportFragmentManager();


//    用于保存推送信息
    private Bundle mNotification = new Bundle();


//    BroadcastReceiver初始化
    private MyBroadcastReceiver mBroadcastReceiver;


//    动态加载的View
    private Toolbar mToolbar;

//    其他一些变量
    private List<Fragment> mFragmentLists;
    private CustomViewPagerAdapter mAdapter;
    private String[] mTitle = {"资料","设备","考试","我的"};
    private int[] mDrawable = {R.drawable.index_logo,R.drawable.equipment_logo,R.drawable.exam_logo,R.drawable.user_logo};

//    View的绑定部分
    @BindView(R.id.tabLayout)
    TabLayout mMainTabLayout;

    @BindView(R.id.fragment_container)
    ViewPager mViewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_main);
        ButterKnife.bind(this);
        mFragmentLists = new ArrayList<>();
        mFragmentLists.add(mIndexFragment);
        mFragmentLists.add(mEquipmentFragment);
        mFragmentLists.add(mExamFragment);
        mFragmentLists.add(mMyInfoFragment);


//        在这里处理登录/注册发送来的数据
        Intent mIntent = getIntent();
        String mUsername = mIntent.getStringExtra("username");
        String mPassword = mIntent.getStringExtra("password");
        String mPhoneNumber = mIntent.getStringExtra("phoneNumber");
        String mDepartment  = mIntent.getStringExtra("department");
        boolean mIsSign     = mIntent.getBooleanExtra("sign",false);
        boolean mIsStore    = mIntent.getBooleanExtra("isStore",false);
        Log.i(TAG, "onCreate: " + mUsername);
        Log.i(TAG, "onCreate: " + mPassword);
        Log.i(TAG, "onCreate: " + mPhoneNumber);
        Log.i(TAG, "onCreate: " + mDepartment);
        Log.i(TAG, "onCreate: " + mIsSign);
        Log.i(TAG, "onCreate: " + mIsStore);

        SharedPreferences.Editor mEditor = getSharedPreferences("user",MODE_PRIVATE).edit();
        mEditor.putString("username",mUsername)
               .putString("password",mPassword)
               .putString("phoneNumber",mPhoneNumber)
               .putString("department",mDepartment)
               .putBoolean("sign",mIsSign)
               .putBoolean("isStore",mIsStore)
               .apply();


        mAdapter = new CustomViewPagerAdapter(mFragmentManager,this);
        mViewPager.setAdapter(mAdapter);
        mMainTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0 ; i < mMainTabLayout.getTabCount() ; i ++){
            TabLayout.Tab mTab = mMainTabLayout.getTabAt(i);
            mTab.setCustomView(mAdapter.getCustomTabView(i));
        }

        mMainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View mView = tab.getCustomView();
                TextView mText = mView.findViewById(R.id.tab_text);
                mText.setTextColor(getResources().getColor(R.color.tabSelecterColor));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View mView = tab.getCustomView();
                TextView mText = mView.findViewById(R.id.tab_text);
                mText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //        默认选中第一个选项卡
        TabLayout.Tab mFirstTab = mMainTabLayout.getTabAt(0);
        TextView mText = /*(TextView)*/ mFirstTab.getCustomView().findViewById(R.id.tab_text);
        mText.setTextColor(getResources().getColor(R.color.tabSelecterColor));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastConstant.CHANGE_TO_REGISTER);
        filter.addAction(BroadcastConstant.CHANGE_TO_LOGIN);
        registerReceiver(mBroadcastReceiver,filter);
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
        JZVideoPlayer.releaseAllVideos();
//        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress())
            return;
        super.onBackPressed();
    }

    //    广播接收器，用于替换Fragment
    class MyBroadcastReceiver extends BroadcastReceiver {
        public MyBroadcastReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case BroadcastConstant.CHANGE_TO_LOGIN:

                    break;
                case BroadcastConstant.CHANGE_TO_REGISTER:

                    break;
            }
        }
    }


//    Viewpager的自定义Adapter
    class CustomViewPagerAdapter extends FragmentStatePagerAdapter {

        private Context mContext;
        private FragmentManager mFragmentManager;



        public CustomViewPagerAdapter(FragmentManager fm , Context context) {
            super(fm);
            mFragmentManager = fm;
            mContext = context;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentLists.get(position);
        }


        @Override
        public int getCount() {
            return mFragmentLists.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }

        public View getCustomTabView(int position){
            View mView = LayoutInflater.from(mContext).inflate(R.layout.tab_custom,null);
//            ButterKnife.bind(mView);
            ImageView mTabIcon = (ImageView) mView.findViewById(R.id.tab_icon);
            TextView  mTabText = (TextView)  mView.findViewById(R.id.tab_text);
            mTabIcon.setImageResource(mDrawable[position]);
            mTabText.setText(mTitle[position]);
            if (Build.VERSION.SDK_INT >= 23) {
                mTabText.setTextAppearance(R.style.tabLayout_textAppearance);
            }
            else{
                mTabText.setTextColor(getResources().getColor(R.color.colorPrimary));
                mTabText.setTextSize(12);
            }
            return mView;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                mIsQuit = true;
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public List<Fragment> getFragmentLists() {
        return mFragmentLists;
    }

    public void getAdapterDataChanged() {
        mAdapter.notifyDataSetChanged();
    }
}

