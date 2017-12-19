package com.example.seuxxd.trainingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import Adapter.MyInfoAdapter;
import Adapter.PersonDetailAdapter;
import Constant.URLConstant;
import Internet.PasswordChangeService;
import Internet.PhoneChangeService;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.ChangeNumberResponse;
import model.ChangePasswordResponse;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonDetailsActivity extends AppCompatActivity {


    @BindView(R.id.person_details_title)
    Toolbar mPersonToolBar;
    @BindView(R.id.toolbar_icon)
    ImageView mPersonIcon;
    @BindView(R.id.toolbar_text)
    TextView mPersonName;
    @BindView(R.id.person_details_list)
    ListView mDetailsList;


//    @BindView(R.id.initial_pwd)
//    EditText mInitialPwd;
//    @BindView(R.id.first_change_pwd)
//    EditText mFirstPwd;
//    @BindView(R.id.second_change_pwd)
//    EditText mSecondPwd;


    private static final String TAG = "PersonDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        ButterKnife.bind(this);
        final String mUsername = getSharedPreferences("user",Context.MODE_PRIVATE)
                .getString("username","未登录");
        final String mPassword = getSharedPreferences("user",Context.MODE_PRIVATE)
                .getString("password","");
        final String mPhoneNumber = getSharedPreferences("user",Context.MODE_PRIVATE)
                .getString("phoneNumber","");
        mPersonToolBar.setTitle("");
        mPersonName.setText("您好！" + mUsername);

        String[] str = {mUsername,mPassword,mPhoneNumber};
        final int[] ints = {R.drawable.me,R.drawable.setting,R.drawable.phone};
        PersonDetailAdapter mInfoAdapter = new PersonDetailAdapter(this,ints,str);
//        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,str);

        final Retrofit mRetrofit = new Retrofit
                .Builder()
                .baseUrl(URLConstant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final PasswordChangeService mChangePwdService = mRetrofit.create(PasswordChangeService.class);
        final PhoneChangeService mChangePhoneService  = mRetrofit.create(PhoneChangeService.class);
        mDetailsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        break;
                    case 1:
//                        点击更改账户密码
                        View mInnerView = LayoutInflater
                                .from(PersonDetailsActivity.this)
                                .inflate(R.layout.change_password_customview,null);
                        final EditText mInitialPwd = mInnerView.findViewById(R.id.initial_pwd);
                        final EditText mFirstPwd = mInnerView.findViewById(R.id.first_change_pwd);
                        final EditText mSecondPwd = mInnerView.findViewById(R.id.second_change_pwd);
//                        ButterKnife.bind(this,mInnerView);
                        AlertDialog mChangePwdDialog = new AlertDialog
                                .Builder(PersonDetailsActivity.this)
                                .setView(mInnerView)
                                .setTitle("更改密码")
                                .setPositiveButton("确定更改", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String mInitialPwdText = mInitialPwd.getText().toString();
                                        final String mFirstPwdText   = mFirstPwd.getText().toString();
                                        String mSecondPwdText  = mSecondPwd.getText().toString();
                                        if (TextUtils.isEmpty(mInitialPwdText)){
                                            Toast.makeText(PersonDetailsActivity.this, "初始密码不能为空", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        if (TextUtils.isEmpty(mFirstPwdText) || TextUtils.isEmpty(mSecondPwdText)){
                                            Toast.makeText(PersonDetailsActivity.this, "新密码不能为空", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        if (!mInitialPwdText.equals(mPassword)){
                                            Toast.makeText(PersonDetailsActivity.this, "初始密码不一致", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        if (!mFirstPwdText.equals(mSecondPwdText)){
                                            Toast.makeText(PersonDetailsActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        Observable<ChangePasswordResponse> mChangePwdObservable = mChangePwdService.changePassword(mUsername,mFirstPwdText);
                                        mChangePwdObservable
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<ChangePasswordResponse>() {
                                                    @Override
                                                    public void onSubscribe(@NonNull Disposable d) {
                                                        Log.i(TAG, "onSubscribe: 订阅成功");
                                                    }

                                                    @Override
                                                    public void onNext(@NonNull ChangePasswordResponse responseBody) {
                                                        boolean mSuccess = responseBody.isSuccess();
                                                        if (mSuccess){
                                                            String[] newStr = {mUsername,mFirstPwdText,mPhoneNumber};
                                                            MyInfoAdapter mAdapter = new MyInfoAdapter(getApplicationContext(),ints,newStr);
                                                            mDetailsList.setAdapter(mAdapter);
                                                            mAdapter.notifyDataSetChanged();
                                                        }
                                                        else {
                                                            Toast.makeText(PersonDetailsActivity.this, "更改密码失败", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(@NonNull Throwable e) {
                                                        e.printStackTrace();
                                                    }

                                                    @Override
                                                    public void onComplete() {

                                                    }
                                                });
                                    }
                                })
                                .setNegativeButton("取消更改", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                        break;
                    case 2:
//                        点击更改手机号码
                        View mChangePhoneNumberView = LayoutInflater
                                .from(PersonDetailsActivity.this)
                                .inflate(R.layout.change_phone_number_customview,null);
                        final EditText mPhoneNumberText = mChangePhoneNumberView.findViewById(R.id.change_phone_number);
                        AlertDialog mChangePhoneNumberDialog = new AlertDialog
                                .Builder(PersonDetailsActivity.this)
                                .setView(mChangePhoneNumberView)
                                .setTitle("更改手机号码")
                                .setPositiveButton("确定更改", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        final String mNewPhoneNumber = mPhoneNumberText.getText().toString();
                                        if (TextUtils.isEmpty(mNewPhoneNumber)){
                                            Log.i(TAG, "onClick: 空");
                                            return;
                                        }
                                        Observable<ChangeNumberResponse> mChangePhoneObservable = mChangePhoneService.changePhone(mUsername,mNewPhoneNumber);
                                        mChangePhoneObservable
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<ChangeNumberResponse>() {
                                                    @Override
                                                    public void onSubscribe(@NonNull Disposable d) {
                                                        Log.i(TAG, "onSubscribe: 订阅成功");
                                                    }

                                                    @Override
                                                    public void onNext(@NonNull ChangeNumberResponse responseBody) {
                                                        boolean mSuccess = responseBody.isSuccess();
                                                        if (mSuccess){
//                                                            更改密码成功
                                                            String[] newStr = {mUsername,mPassword,mNewPhoneNumber};
                                                            MyInfoAdapter mAdapter = new MyInfoAdapter(getApplicationContext(),ints,newStr);
                                                            mDetailsList.setAdapter(mAdapter);
                                                            mAdapter.notifyDataSetChanged();
                                                        }
                                                        else {
                                                            Toast.makeText(PersonDetailsActivity.this, "更改失败", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(@NonNull Throwable e) {
                                                        e.printStackTrace();
                                                    }

                                                    @Override
                                                    public void onComplete() {
//                                                        String[] newStr = {mUsername,mPassword,mNewPhoneNumber};
//                                                        MyInfoAdapter mAdapter = new MyInfoAdapter(getApplicationContext(),ints,newStr);
//                                                        mDetailsList.setAdapter(mAdapter);
//                                                        mAdapter.notifyDataSetChanged();
                                                    }
                                                });

                                    }
                                })
                                .setNegativeButton("取消更改", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                        break;
                    default:
                        break;
                }
            }
        });
        mDetailsList.setAdapter(mInfoAdapter);



    }
}
