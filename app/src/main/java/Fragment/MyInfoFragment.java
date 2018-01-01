package Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.seuxxd.trainingapp.ExamActivity;
import com.example.seuxxd.trainingapp.LoginActivity;
import com.example.seuxxd.trainingapp.PersonDetailsActivity;
import com.example.seuxxd.trainingapp.R;
import com.example.seuxxd.trainingapp.SuggestionActivity;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import Adapter.MyInfoAdapter;
import Constant.ConstantCode;
import Constant.SourceConstant;
import Constant.URLConstant;
import Internet.ExamResultService;
import Internet.ExamService;
import Internet.SignService;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.ExamResponse;
import model.SignResponse;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoFragment extends Fragment {



    private static final String TAG = "MyInfoFragment";
    private MyInfoAdapter mMyInfoAdapter;
    private SourceConstant mConstant = SourceConstant.newInstance();
    public MyInfoFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.myInfo_toolbar)
    Toolbar mToolBar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
//    @BindView(R.id.toolbar_icon)
//    ImageView mToolBarIcon;
    @BindView(R.id.toolbar_username)
    TextView mToolBarUserName;
    @BindView(R.id.myInfo_list)
    ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_myinfo,container,false);
        ButterKnife.bind(this,mView);
        mToolBar.setTitle("");
//        mToolBarIcon.setImageResource(R.drawable.info);
        final String mUsername = getActivity()
                .getSharedPreferences("user", Context.MODE_PRIVATE)
                .getString("username","未登录");
        String mPhoneNumber = getActivity()
                .getSharedPreferences("user",Context.MODE_PRIVATE)
                .getString("phoneNumber","");
        boolean mIsSign = getActivity()
                .getSharedPreferences("user",Context.MODE_PRIVATE)
                .getBoolean("sign",false);
        if (!mIsSign){
            mMyInfoAdapter= new MyInfoAdapter(
                    getActivity(),
                    mConstant.getMyInfoImageList(),
                    mConstant.getMyInfoStringList());
            mListView.setAdapter(mMyInfoAdapter);
        }
        else{
//            mConstant.getMyInfoImageList()[0] = R.drawable.sign_ok_logo;
            mMyInfoAdapter= new MyInfoAdapter(
                    getActivity(),
                    mConstant.getMyInfoImageList2(),
                    mConstant.getMyInfoStringList2());
            mListView.setAdapter(mMyInfoAdapter);
        }

        final String mTraintime = ConstantCode.getFormatTime();
        Log.i(TAG, "onCreateView: " + mTraintime);


        String mShowText = "您好！ " + mUsername;
        mToolbarTitle.setText("账号信息");
        mToolBarUserName.setText(mShowText);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);


        final Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(URLConstant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        mListView.setDividerHeight(100);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
//                    签到
                    case 1:
                        SignService mSignService = mRetrofit.create(SignService.class);
                        Observable<SignResponse> mSignObservable = mSignService.doSign(mUsername,mTraintime);
                        mSignObservable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<SignResponse>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {
                                        Log.i(TAG, "onSubscribe: " + "订阅成功");
                                    }

                                    @Override
                                    public void onNext(@NonNull SignResponse signResponse) {
                                        Log.i(TAG, "onNext: " + signResponse.isSuccess());
                                        Log.i(TAG, "onNext: " + signResponse.getText());
                                        if (signResponse.isSuccess()){
                                            mMyInfoAdapter = new MyInfoAdapter(getActivity(),
                                                    mConstant.getMyInfoImageList2(),
                                                    mConstant.getMyInfoStringList2());
                                            SharedPreferences.Editor mEditor = getActivity().getSharedPreferences("user",Context.MODE_PRIVATE).edit();
                                            mEditor.putBoolean("sign",true);
                                            mEditor.apply();
                                            mMyInfoAdapter.notifyDataSetChanged();
                                            mListView.setAdapter(mMyInfoAdapter);
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
                        break;
//                    个人资料
                    case 3:
                        final Intent mIntent = new Intent(getActivity(), PersonDetailsActivity.class);
                        startActivity(mIntent);
                        break;
//                    考试
//                    设置
                    case 4:
//                        这里是提交意见与建议
                        Intent mSuggestionIntent = new Intent(getActivity(), SuggestionActivity.class);
                        startActivity(mSuggestionIntent);
                        break;
//                    退出登录
                    case 5:
                        Intent mQuitIntent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(mQuitIntent);
                        getActivity().finish();
                        break;
                    default:
                        break;
                }
            }
        });

        return mView;
    }

}
