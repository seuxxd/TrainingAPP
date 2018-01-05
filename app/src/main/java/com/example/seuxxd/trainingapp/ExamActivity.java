package com.example.seuxxd.trainingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.questions.ExamResponse;
import model.questions.QuestionData;

import Fragment.QuestionFragment;
import Fragment.QuestionFinishFragment;

public class ExamActivity extends AppCompatActivity {


    private List<Fragment> mList = new ArrayList<>();


    private static final String TAG = "ExamActivity";


    private QuestionFinishFragment mFinishFragment = new QuestionFinishFragment();

    public QuestionFinishFragment getFinishFragment() {
        return mFinishFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        ViewPager mPager = (ViewPager) findViewById(R.id.exam_viewPager);
        Intent mExamIntent = getIntent();
        Bundle mBundle = mExamIntent.getExtras();
        SharedPreferences mPreferences = getSharedPreferences("answer", Context.MODE_PRIVATE);
        if (
                mPreferences.contains("5") ||
                mPreferences.contains("4") ||
                mPreferences.contains("3") ||
                mPreferences.contains("2") ||
                mPreferences.contains("1")){
            mPreferences.edit().clear().apply();
        }
        ExamResponse mExamResponse = mBundle.getParcelable("exam");
        int mType = mBundle.getInt("type",2);
        if (mExamResponse != null){
            int count = mExamResponse.getResults();
            QuestionData[] mDatas = mExamResponse.getRows();
            for (int i = 0 ; i < mDatas.length ; i  ++){
                Log.i(TAG, "onCreate: " + mDatas[i].getAnswer());
                QuestionFragment mFragment = new QuestionFragment();
                mList.add(mFragment);
                Bundle mFragmentBundle = new Bundle();
                mFragmentBundle.putInt("index",i+1);
                mFragmentBundle.putInt("type",mType);
                mFragmentBundle.putParcelable("data",mDatas[i]);
                mFragment.setArguments(mFragmentBundle);
            }
            mPreferences.edit().putInt("type",mType).apply();
            mList.add(QuestionFinishFragment.newInstance());
            QuestionViewPagerAdapter mAdapter = new QuestionViewPagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(mAdapter);
        }
        else{
            Toast.makeText(this, "请退出该界面重试", Toast.LENGTH_SHORT).show();
        }

    }



    class QuestionViewPagerAdapter extends FragmentStatePagerAdapter{
        public QuestionViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

//        @Override
//        public long getItemId(int position) {
//            return position;
//        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
