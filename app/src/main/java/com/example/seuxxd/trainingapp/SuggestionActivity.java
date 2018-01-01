package com.example.seuxxd.trainingapp;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import Constant.ConstantCode;
import Constant.URLConstant;
import Internet.SuggestionService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.SuggestionResponse;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SuggestionActivity extends AppCompatActivity {



    private static final String TAG = "SuggestionActivity";




    @BindView(R.id.suggestion_toolbar)
    Toolbar mSuggestionToolbar;
    @BindView(R.id.suggestion_abstract_input_layout)
    TextInputLayout mAbstractLayout;
    @BindView(R.id.suggestion_abstract_input)
    EditText mAbstractInput;
    @BindView(R.id.suggestion_detail_input_layout)
    TextInputLayout mDetailLayout;
    @BindView(R.id.suggestion_detail_input)
    EditText mDetailInput;
    @BindView(R.id.suggestion_submit)
    Button mSuggestionSubmit;
    @OnClick(R.id.suggestion_submit)
    public void onSubmit(){
        String mTitle = mAbstractInput.getText().toString();
        String mContent = mDetailInput.getText().toString();
        String mUsername = getSharedPreferences("user", Context.MODE_PRIVATE)
                .getString("username","未知用户");
        if (!checkInput(mTitle,mContent)){
            return;
        }
        //TODO 提交意见与建议到服务器
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(URLConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        SuggestionService mService = mRetrofit.create(SuggestionService.class);
        Observable<SuggestionResponse> mObservable = mService.submitSuggestion(
                mTitle,
                mContent,
                mUsername,
                ConstantCode.getFormatTime());
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SuggestionResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: 上传建议订阅成功");
                    }

                    @Override
                    public void onNext(@NonNull SuggestionResponse suggestionResponse) {
                        Log.i(TAG, "onNext: 是否成功 " + suggestionResponse.isSuccess());
                        Log.i(TAG, "onNext: 具体内容 " + suggestionResponse.getText());
                        if (!suggestionResponse.isSuccess())
                            Toast.makeText(SuggestionActivity.this, "提交失败，请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(SuggestionActivity.this, "提交失败，请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(SuggestionActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        ButterKnife.bind(this);
        mSuggestionToolbar.setTitle("意见与建议");
//        mSuggestionToolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(mSuggestionToolbar);
//        mSuggestionToolbar.setNavigationIcon(R.drawable.user_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /**
     *
     * @param title
     * @param content
     * @return
     */
    private boolean checkInput(String title , String content){
        if (TextUtils.isEmpty(title)){
            mAbstractLayout.setErrorEnabled(true);
            mAbstractLayout.setError("建议简述不能为空");
            mDetailLayout.setErrorEnabled(false);
            return false;
        }
        if (TextUtils.isEmpty(content)){
            mDetailLayout.setErrorEnabled(true);
            mDetailLayout.setError("详细建议不能为空");
            mAbstractLayout.setErrorEnabled(false);
            return false;
        }
        if (title.length() > 30){
            mAbstractLayout.setErrorEnabled(true);
            mAbstractLayout.setError("建议简述过长，请精简");
            mDetailLayout.setErrorEnabled(false);
            return false;
        }
        if (content.length() > 200){
            mAbstractLayout.setErrorEnabled(false);
            mDetailLayout.setError("详细建议过长，请精简");
            mDetailLayout.setErrorEnabled(true);
            return false;
        }
        return true;
    }
}
