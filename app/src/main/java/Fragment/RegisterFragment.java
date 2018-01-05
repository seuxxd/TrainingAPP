package Fragment;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.seuxxd.trainingapp.MainActivity;
import com.example.seuxxd.trainingapp.R;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Constant.BroadcastConstant;
import Constant.URLConstant;
import Internet.RegisterService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.register.RegisterResponse;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    private static final String TAG = "RegisterFragment";
    private Disposable mDisposible;//阻断
    private int mDepartment = 0;
//    View绑定

//    @BindView(R.id.top_register_text)
//    TextView mTopRegisterText;

    @BindView(R.id.do_login)
    TextView mDoLoginText;
    @OnClick(R.id.do_login)
    public void doLogin(){
        Intent mIntent = new Intent(BroadcastConstant.CHANGE_TO_LOGIN);
        getActivity().sendBroadcast(mIntent);
    }

//    部门选择
    @BindView(R.id.choose_department)
    Spinner mDepartmentChooser;

    //    输入框
    @BindView(R.id.register_password)
    TextInputEditText mRegisterPassword;
    @BindView(R.id.register_password_2)
    TextInputEditText mRegisterPassword2;
    @BindView(R.id.register_username)
    TextInputEditText mRegisterUsername;
    @BindView(R.id.register_phoneNumber)
    TextInputEditText mRegisterPhonenumber;


    //    输入外围
    @BindView(R.id.register_password_layout)
    TextInputLayout mRegisterPasswordLayout;
    @BindView(R.id.register_password_layout_2)
    TextInputLayout mRegisterPasswordLayout2;
    @BindView(R.id.register_username_layout)
    TextInputLayout mRegisterUsernameLayout;
    @BindView(R.id.register_phoneNumber_layout)
    TextInputLayout mRegisterPhonenumberLayout;

    @BindView(R.id.register)
    Button mRegisterButton;
//    注册验证，先进行本地验证，验证成功之后再上传到服务器进行验证
    @OnClick(R.id.register)
    public void onRegister(){
        final String mUsername = mRegisterUsername.getText().toString();
        final String mPassword = mRegisterPassword.getText().toString();
        final String mPassword2 = mRegisterPassword2.getText().toString();
        final String mPhoneNumber = mRegisterPhonenumber.getText().toString();


//        判断所有错误之后，进行注册服务
        if (checkRegisterInput(mUsername,mPassword,mPassword2,mPhoneNumber)){
            final OkHttpClient mClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build();
            Retrofit mRetrofit = new Retrofit.Builder()
                    .baseUrl(URLConstant.BASE_URL)
                    .client(mClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RegisterService mRegisterService = mRetrofit.create(RegisterService.class);
            Observable<RegisterResponse> mObservable =
                    mRegisterService.doRegister(
                            mUsername,
                            mPassword,
                            mPhoneNumber,
                            String.valueOf(mDepartment));
            mObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<RegisterResponse>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            Log.i(TAG, "onSubscribe: " + "订阅成功");
                            mDisposible = d;
                        }

                        @Override
                        public void onNext(@NonNull RegisterResponse registerResponse) {
//                            这里判断服务器返回消息
                            boolean mSuccess = registerResponse.isSuccess();
                            String text = registerResponse.getText();
                            Log.i(TAG, "onNext: " + mSuccess);
                            Log.i(TAG, "onNext: " + text);
                            if (mSuccess){
                                Intent mIntent = new Intent(getActivity(), MainActivity.class);
                                mIntent.putExtra("username",mUsername);
                                mIntent.putExtra("password",mPassword);
                                mIntent.putExtra("phoneNumber",mPhoneNumber);
                                mIntent.putExtra("department",mDepartment);
                                startActivity(mIntent);
                                getActivity().finish();
                            }
                            else {
                                // TODO text check
                                Log.i(TAG, "onNext: " + text);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
//                            这里判断错误消息
                            Log.i(TAG, "onError: " + e.toString());
                        }

                        @Override
                        public void onComplete() {
//                            完成
                        }
                    });
        }
    }



    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRegisterLayout = inflater.inflate(R.layout.fragment_register,container,false);
        ButterKnife.bind(this,mRegisterLayout);
//        AssetManager mManager = getActivity().getAssets();
//        Typeface typeface = Typeface.createFromAsset(mManager,"font/fontType_1.ttf");
//        mTopRegisterText.setTypeface(typeface);
        mDepartmentChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDepartment = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDepartment = 0;
            }
        });
        return mRegisterLayout;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        return super.onCreateAnimation(transit, enter, nextAnim);
        switch (transit){
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE:
                if (enter){
                    return AnimationUtils.loadAnimation(getActivity(),android.R.anim.fade_in);
                }
                else {
                    return AnimationUtils.loadAnimation(getActivity(),android.R.anim.fade_out);
                }
            case FragmentTransaction.TRANSIT_FRAGMENT_OPEN:
                if (enter){
                    return AnimationUtils.loadAnimation(getActivity(),R.anim.fragment_in);
                }
                else{
                    return AnimationUtils.loadAnimation(getActivity(),R.anim.fragment_out);
                }
            case FragmentTransaction.TRANSIT_FRAGMENT_CLOSE:
                if (enter){
                    return AnimationUtils.loadAnimation(getActivity(),R.anim.fragment_in);
                }
                else{
                    return AnimationUtils.loadAnimation(getActivity(),R.anim.fragment_out);
                }
            default:
                if (enter){
                    return AnimationUtils.loadAnimation(getActivity(),android.R.anim.fade_in);
                }
                else {
                    return AnimationUtils.loadAnimation(getActivity(),android.R.anim.fade_out);
                }
        }
    }

    /**
     *
     * @param username check if it is empty
     * @param password
     * @param password2 compare password and password2
     * @param phonenumber check if it is phonenumber
     * @return result of the check
     */
    private boolean checkRegisterInput(String username ,
                                       String password ,
                                       String password2 ,
                                       String phonenumber){

        boolean mResult = true;
        String regex = "^1[0-9]{10}$";//1开头的手机号码，一共11位
        Pattern mPattern = Pattern.compile(regex);
        Matcher mMatcher = mPattern.matcher(phonenumber);
        //        判断用户名为空
        if (TextUtils.isEmpty(username)){
            mRegisterUsernameLayout.setErrorEnabled(true);
            mRegisterUsernameLayout.setError("用户名不能为空");
            mRegisterUsernameLayout.setErrorTextAppearance(R.style.register_error);
            mResult = false;
        }
        if (TextUtils.isEmpty(password)){
            mRegisterPasswordLayout.setErrorEnabled(true);
            mRegisterPasswordLayout.setError("密码不能为空");
            mRegisterPasswordLayout.setErrorTextAppearance(R.style.register_error);
        }
//        判断两次密码输入不一致
        if (!password.equals(password2)){
            Log.i(TAG, "onRegister: " + "not equal");
            mRegisterPasswordLayout.setErrorEnabled(true);
            mRegisterPasswordLayout2.setErrorEnabled(true);
            mRegisterPasswordLayout.setError("两次输入密码不一致");
            mRegisterPasswordLayout2.setError("两次输入密码不一致");
            mRegisterPasswordLayout.setErrorTextAppearance(R.style.register_error);
            mRegisterPasswordLayout2.setErrorTextAppearance(R.style.register_error);
            mResult = false;
        }

//        电话号码不能为空或者格式错误

        if (!mMatcher.matches()) {
            mRegisterPhonenumberLayout.setErrorEnabled(true);
            mRegisterPhonenumberLayout.setError("电话号码格式不对");
            mRegisterPhonenumberLayout.setErrorTextAppearance(R.style.register_error);
            mResult = false;
        }

        if (!TextUtils.isEmpty(username)){
            mRegisterUsernameLayout.setErrorEnabled(false);
        }

        if (password.equals(password2) && !TextUtils.isEmpty(password)){
            mRegisterPasswordLayout.setErrorEnabled(false);
            mRegisterPasswordLayout2.setErrorEnabled(false);
        }

        if (mMatcher.matches()){
            mRegisterPhonenumberLayout.setErrorEnabled(false);
            Log.i(TAG, "checkRegisterInput: find");
        }
        return mResult;
    }
}
