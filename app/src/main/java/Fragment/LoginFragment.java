package Fragment;


import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seuxxd.trainingapp.MainActivity;
import com.example.seuxxd.trainingapp.R;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import Constant.BroadcastConstant;
//import Constant.URLConstant;
import Constant.URLConstant;
import Internet.ForgetPasswordService;
import Internet.LoginService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.login.LoginResponse;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    private static final String TAG = "LoginFragment";

    private OkHttpClient mClient = new OkHttpClient.Builder()
            .connectTimeout(3 , TimeUnit.SECONDS)
            .readTimeout(5 , TimeUnit.SECONDS)
            .writeTimeout(5 , TimeUnit.SECONDS)
            .build();
    private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(URLConstant.BASE_URL)
            .client(mClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

//    绑定View
    @BindView(R.id.do_register)
    TextView mDoRegisterText;
    @OnClick(R.id.do_register)
    public void doRegister(){
        Intent mIntent = new Intent(BroadcastConstant.CHANGE_TO_REGISTER);
        getActivity().sendBroadcast(mIntent);
//        Toast.makeText(getContext(), "do_register", Toast.LENGTH_SHORT).show();
    }
//    @BindView(R.id.top_login_text)
//    TextView mTopLoginText;

    @BindView(R.id.store_info)
    CheckBox mStoreInfo;

//    登录框的外围布局，用于显示错误信息
    @BindView(R.id.login_username_layout)
    TextInputLayout mUsernameLayout;
    @BindView(R.id.login_password_layout)
    TextInputLayout mPasswordLayout;


//    登录框的输入部分-用户名和密码
    @BindView(R.id.login_username)
    TextInputEditText mUsernameEditView;
    @BindView(R.id.login_password)
    TextInputEditText mPasswordEditView;



    @BindView(R.id.forget_pwd)
    TextView mForgetPwd;
    @OnClick(R.id.forget_pwd)
    public void onForgetPwd(){
        View mForgetView = LayoutInflater.from(getContext()).inflate(R.layout.forget_password_customview,null);
        final EditText mUsername = mForgetView.findViewById(R.id.forget_username);
        final EditText mNumber = mForgetView.findViewById(R.id.forget_number);
        final EditText mPwd1 = mForgetView.findViewById(R.id.forget_new_pwd1);
        final EditText mPwd2 = mForgetView.findViewById(R.id.forget_new_pwd2);
        AlertDialog mDialog = new AlertDialog.Builder(getContext())
                .setView(mForgetView)
                .setTitle("忘记密码")
                .setPositiveButton("请求更改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!mPwd1.getText().toString().equals(mPwd2.getText().toString())){
                            Toast.makeText(getContext(), "两次输入密码不同", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "onClick: 两次输入密码不同");
                            return;
                        }
                        if (TextUtils.isEmpty(mUsername.getText().toString())){
                            Toast.makeText(getContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "onClick: 用户名为空");
                            return;
                        }
                        if (TextUtils.isEmpty(mNumber.getText().toString())){
                            Toast.makeText(getContext(), "手机号码不能为空", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "onClick: 号码为空");
                            return;
                        }
                        ForgetPasswordService mForgetService = mRetrofit.create(ForgetPasswordService.class);
                        Observable<ResponseBody> mForgetObservable =
                                mForgetService.forgetPwdAndResetPwd(
                                        mUsername.getText().toString(),
                                        mNumber.getText().toString(),
                                        mPwd1.getText().toString());
                        mForgetObservable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ResponseBody>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {
                                        Log.i(TAG, "onSubscribe: 订阅成功");
                                    }

                                    @Override
                                    public void onNext(@NonNull ResponseBody responseBody) {
                                        try {
                                            Log.i(TAG, "onNext: " + responseBody.string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
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

    }


    @BindView(R.id.login)
    Button mLoginButton;
    @OnClick(R.id.login)
    public void onLogin(){
        LoginService mLoginService = mRetrofit.create(LoginService.class);
        final String mUsername = mUsernameEditView.getText().toString();
        final String mPassword = mPasswordEditView.getText().toString();
        final boolean mIsStore = mStoreInfo.isChecked();
        Log.i(TAG, "onLogin: mIsStore   " + mIsStore);
        Observable<LoginResponse> mObservable = mLoginService.doLogin(mUsername,mPassword);
        if (checkLoginInput(mUsername,mPassword)) {
            mObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<LoginResponse>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            Log.i(TAG, "onSubscribe: " + "订阅成功");
//                            无网络测试代码
//                            Intent mIntent = new Intent(getActivity(), MainActivity.class);
//                            String mPhoneNumber = "12312312312";
//                            boolean mIsStore = mStoreInfo.isChecked();
//                            mIntent.putExtra("username",mUsername);
//                            mIntent.putExtra("password",mPassword);
//                            mIntent.putExtra("phoneNumber",mPhoneNumber);
//                            mIntent.putExtra("isStore",mIsStore);
//                            Log.i(TAG, "onSubscribe: " + mIsStore);
//                            startActivity(mIntent);
//                            getActivity().finish();
                        }

                        @Override
                        public void onNext(@NonNull LoginResponse loginResponse) {
                            boolean mSuccess = loginResponse.isSuccess();
                            if (mSuccess){
                                Intent mIntent = new Intent(getActivity(), MainActivity.class);
                                String mPhoneNumber = loginResponse.getMobile();
                                String mDepartment = loginResponse.getDepartment();
                                Log.i(TAG, "onNext: " + mDepartment);
                                boolean mIsSign = loginResponse.isSign();
                                mIntent.putExtra("username",mUsername);
                                mIntent.putExtra("password",mPassword);
                                mIntent.putExtra("phoneNumber",mPhoneNumber);
                                mIntent.putExtra("isStore",mIsStore);
                                mIntent.putExtra("department",mDepartment);
                                mIntent.putExtra("sign",mIsSign);
                                startActivity(mIntent);
                                getActivity().finish();
                            }
                            else {
                                String mErrorText = loginResponse.getText();
                                if (mErrorText.equals("用户没有注册，请先注册")){
                                    mUsernameLayout.setErrorEnabled(true);
                                    mUsernameLayout.setError("用户没有注册，请先注册");
                                    mPasswordLayout.setErrorEnabled(false);
                                }
                                if (mErrorText.equals("密码错误，登录失败")){
                                    mPasswordLayout.setErrorEnabled(true);
                                    mPasswordLayout.setError("密码错误，请重试");
                                    mUsernameLayout.setErrorEnabled(false);
                                }
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.i(TAG, "onError: " + "错误" + e.toString());
//                            Toast.makeText(getActivity(), "连接超时", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onComplete() {
                            Log.i(TAG, "onComplete: " + "完成");
                        }
                    });
        }

    }



    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mLoginLayout = inflater.inflate(R.layout.fragment_login,container,false);
        ButterKnife.bind(this,mLoginLayout);
//        AssetManager mManager = getActivity().getAssets();
//        Typeface mType = Typeface.createFromAsset(mManager, "font/fontType_1.ttf");
//        mTopLoginText.setTypeface(mType);
        SharedPreferences mStore = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean mIsStore = mStore.getBoolean("isStore",false);
        Log.i(TAG, "onCreateView: " + mIsStore);
        if (mIsStore){
            mUsernameEditView.setText(mStore.getString("username",""));
            mPasswordEditView.setText(mStore.getString("password",""));
            mStoreInfo.setChecked(true);
        }

        return mLoginLayout;
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        return super.onCreateAnimation(transit, enter, nextAnim);
        switch (transit){
            case FragmentTransaction.TRANSIT_FRAGMENT_OPEN:
            case FragmentTransaction.TRANSIT_FRAGMENT_CLOSE:
                if (enter){
                    return AnimationUtils.loadAnimation(getActivity(),R.anim.fragment_in);
                }
                else{
                    return AnimationUtils.loadAnimation(getActivity(),R.anim.fragment_out);
                }
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE:
            default:
                if (enter){
                    return AnimationUtils.loadAnimation(getActivity(),android.R.anim.fade_in);
                }
                else {
                    return AnimationUtils.loadAnimation(getActivity(),android.R.anim.fade_out);
                }
        }
    }


    private boolean checkLoginInput(String username , String password){
        boolean result = true;
        if (TextUtils.isEmpty(username)){
            result = false;
            Log.i(TAG, "checkInput: username");
            mUsernameLayout.setError("用户名不能为空");
            mUsernameLayout.setErrorEnabled(true);
        }
        if (TextUtils.isEmpty(password)){
            result = false;
            Log.i(TAG, "checkInput: password");
            mPasswordLayout.setError("密码不能为空");
            mPasswordLayout.setErrorEnabled(true);
        }
        if (!TextUtils.isEmpty(username)){
            mUsernameLayout.setErrorEnabled(false);
        }
        if (!TextUtils.isEmpty(password))
            mPasswordLayout.setErrorEnabled(false);
        return result;
    }
}
