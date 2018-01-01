package Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.seuxxd.trainingapp.IndexFourInfoActivity;
import com.example.seuxxd.trainingapp.MessageActivity;
import com.example.seuxxd.trainingapp.NewsActivity;
import com.example.seuxxd.trainingapp.R;
import com.example.seuxxd.trainingapp.SuggestionActivity;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sunfusheng.marqueeview.MarqueeView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import Constant.ConstantCode;
import Constant.URLConstant;
import Internet.DayImageService;
import Internet.GetErrorService;
import Internet.GetPPTService;
import Internet.GetRulesService;
import Internet.GetVideoService;
import Internet.MessageService;
import Internet.NewsService;
import Internet.SignService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.IndexError;
import model.IndexPdf;
import model.IndexRules;
import model.IndexRulesDetails;
import model.IndexVideo;
import model.MessageDetails;
import model.MessageResponse;
import model.NewsDetail;
import model.NewsResponse;
import model.SignResponse;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends Fragment implements View.OnClickListener{



    private ArrayList<MessageDetails> mList ;
    private MessageDetails[] mDetails;
    private IndexPdf mIndexPV;


    private Retrofit mRetrofit = new Retrofit
            .Builder()
            .baseUrl(URLConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    private static final String TAG = "IndexFragment";


    @BindView(R.id.day_image)
    ImageView mDayImage;

    @BindView(R.id.training_notification)
    TextView mNotificationText;
    @OnClick(R.id.training_notification)
    public void onShow(){
        if (mList == null || mList.size() <= 0){
            Log.i(TAG, "onShow: 无数据");
            Toast.makeText(getActivity(), "没有推送信息", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent mIntent = new Intent(getActivity(), MessageActivity.class);
        mIntent.putExtra("count",mList.size());
        mIntent.putParcelableArrayListExtra("details",mList);
        startActivity(mIntent);

    }

    /**
     * 六个点击事件的组件初始化
     */

    @BindView(R.id.index_rules)
    TextView mRulesText;
    @BindView(R.id.index_video)
    TextView mVideoText;
    @BindView(R.id.index_ppt)
    TextView mPPTText;
    @BindView(R.id.index_error)
    TextView mErrorText;
    @BindView(R.id.index_sign)
    TextView mSignText;
    @BindView(R.id.index_suggestion)
    TextView mSuggestionText;

    @BindView(R.id.news_show)
    MarqueeView mMarqueeView;
//    @BindView(R.id.index_like)
//    JZVideoPlayerStandard mPlayer;

    public IndexFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");

    }


    @Override
    public void onResume() {
        Log.i(TAG, "onResume: ");
        super.onResume();
//        final Vibrator mVibrator = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);
//        if (mDetails != null)
//            mVibrator.vibrate(new long[]{100,100,100,100},-1);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mLayout = inflater.inflate(R.layout.fragment_index,container,false);
        ButterKnife.bind(this,mLayout);
        getDayImage();
        getNews();
        getNotifications();
        mRulesText.setOnClickListener(this);
        mPPTText.setOnClickListener(this);
        mErrorText.setOnClickListener(this);
        mSuggestionText.setOnClickListener(this);
        mSignText.setOnClickListener(this);
        mVideoText.setOnClickListener(this);
//        String url = "http://www.jmzsjy.com/UploadFile/微课/地方风味小吃——宫廷香酥牛肉饼.mp4";
//        mPlayer.setUp(url, JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN,"兴趣");
        Log.i(TAG, "onCreateView: 创建视图");
        return mLayout;
    }

    /**
     * 获取每日图片的函数
     * 用来填充首页的空白
     */
    private void getDayImage(){
//        String url = "http://guolin.tech/api/bing_pic";
        DayImageService mService = mRetrofit.create(DayImageService.class);
        Observable<ResponseBody> mObservable = mService.getDayImage();
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: 图片订阅成功");
                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            String mImageUrl = responseBody.string();
                            Log.i(TAG, "onNext: " + mImageUrl);
                            Glide.with(getContext()).load(mImageUrl).into(mDayImage);
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

    /**
     * 这个是获取推送信息的函数
     * 传递参数在函数内自动获取----
     * 时间 年-月-日 如2017-01-01
     * 用户名
     * 返回推送信息并显示在界面
     */
    private void getNotifications(){
        String mUsername = getActivity()
                .getSharedPreferences("user", Context.MODE_PRIVATE)
                .getString("username","");
        MessageService mMessageService = mRetrofit.create(MessageService.class);
        Calendar mCalendar = Calendar.getInstance();
        String mMonth = String.valueOf(mCalendar.get(Calendar.MONTH) + 1);
        String mDay = String.valueOf(mCalendar.get(Calendar.DAY_OF_MONTH));
        if (mCalendar.get(Calendar.MONTH) + 1 < 10){
            mMonth = "0" + (mCalendar.get(Calendar.MONTH) + 1);
        }
        if (mCalendar.get(Calendar.DAY_OF_MONTH) < 10){
            mDay = "0" + mCalendar.get(Calendar.DAY_OF_MONTH);
        }
        final String mDateFormat = mCalendar.get(Calendar.YEAR) +"-" +
                mMonth + "-" + mDay;
        Log.i(TAG, "onCreate:data " + mDateFormat);
        Log.i(TAG, "onCreateView: username" + mUsername);
        Observable<MessageResponse> mObservable = mMessageService.getMessage(mUsername,mDateFormat);
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MessageResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: " + "推送订阅成功");
                    }

                    @Override
                    public void onNext(@NonNull MessageResponse messageResponse) {
                        mDetails = messageResponse.getRows();
                        if (mDetails == null || mDetails.length <= 0){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mNotificationText.setText("当前没有推送信息");
                                }
                            });
                            Log.i(TAG, "onCreateView: 22222222");
                        }
                        else if (mDetails.length >= 1){
                            final String sb =
                                    "您有" +
                                            mDetails.length +
                                            "条培训推送，点击查看！" ;

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mNotificationText.setText(sb);
                                    mList = new ArrayList<>();
                                    mList.addAll(Arrays.asList(mDetails));
                                }
                            });
                            Log.i(TAG, "onCreateView: 1111111");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        try {
                            Toast.makeText(getContext(), "获取推送信息出错，请检查网络或重新登陆！", Toast.LENGTH_SHORT).show();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * 获取新闻资讯的函数
     * 无参数
     * 返回5条新闻相关信息
     */



    private void getNews(){
        NewsService mService = mRetrofit.create(NewsService.class);
        Observable<NewsResponse> mObservable = mService.getNews();
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: 新闻订阅成功");
                    }

                    @Override
                    public void onNext(@NonNull NewsResponse newsResponse) {
                        Log.i(TAG, "onNext: " + newsResponse.getResults());
                        NewsDetail[] details = newsResponse.getRows();
                        List<String> mNewsList = new ArrayList<>();
                        final List<String> mLinkList = new ArrayList<>();
//                        Log.i(TAG, "onNext: " + details[0].getRealchain());
                        for (int i = 0 ; i < details.length ; i ++){
                            Log.i(TAG, "onNext: 链接名字" + details[i].getRealinforname());
                            mNewsList.add(details[i].getRealinforname());
                            mLinkList.add(details[i].getRealchain());
                        }
                        mMarqueeView.startWithList(mNewsList);
                        mMarqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position, TextView textView) {
                                Intent mIntent = new Intent(getActivity(), NewsActivity.class);
                                mIntent.putExtra("url",mLinkList.get(position));
                                startActivity(mIntent);
                            }
                        });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.index_rules:
                getRulesInfo();
                break;
            case R.id.index_ppt:
                getPdfInfo();
                break;
            case R.id.index_error:
                getErrorInfo();
                break;
            case R.id.index_sign:
                sign();
                break;
            case R.id.index_suggestion:
                suggestion();
                break;
            case R.id.index_video:
                getVideoInfo();
                break;
            default:
                break;
        }
    }


    /**
     * convince sign function
     */
    private void sign(){
        SignService mService = mRetrofit.create(SignService.class);
        final SharedPreferences mSP = getActivity().getSharedPreferences("user",Context.MODE_PRIVATE);
        String mUsername = mSP.getString("username","");
        Observable<SignResponse> mObservable = mService.doSign(mUsername,ConstantCode.getFormatTime());
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SignResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: 快捷签到订阅成功");
                    }

                    @Override
                    public void onNext(@NonNull SignResponse signResponse) {
                        if (signResponse.isSuccess()){
                            mSP.edit().putBoolean("sign",true).apply();
                            Toast.makeText(getActivity(), "签到成功", Toast.LENGTH_SHORT).show();
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


    /**
     * get the information of rules from server
     * only contains pdf
     */
    private void getRulesInfo(){
        GetRulesService mService = mRetrofit.create(GetRulesService.class);
        Observable<IndexRules> mObservable = mService.getRules();
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IndexRules>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: 订阅规则规章成功");
                    }

                    @Override
                    public void onNext(@NonNull IndexRules indexRules) {
                        if (indexRules != null){
                            startFourDetails(indexRules,ConstantCode.INDEX_RULES_TYPE);
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


    /**
     * get the information of error from server
     * both pdf and video
     */

    private void getErrorInfo(){
        GetErrorService mService = mRetrofit.create(GetErrorService.class);
        Observable<IndexError> mObservable = mService.getError();
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IndexError>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: 订阅典型错误成功");
                    }

                    @Override
                    public void onNext(@NonNull IndexError indexError) {
                        if (indexError != null){
                            startFourDetails(indexError,ConstantCode.INDEX_ERROR_TYPE);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: 订阅典型错误完成");
                    }
                });
    }


    /**
     * get the information of pdf from server
     * only pdf
     */

    private void getPdfInfo(){
        GetPPTService mService = mRetrofit.create(GetPPTService.class);
        Observable<IndexPdf> mObservable = mService.getPdf();
        mObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<IndexPdf>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: 获取首页文档资料成功");
                    }

                    @Override
                    public void onNext(@NonNull IndexPdf indexPdf) {
                        if (indexPdf != null){
                            startFourDetails(indexPdf,ConstantCode.INDEX_PDF_TYPE);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: 获取首页文档资料完成");
                    }
                });
    }


    /**
     * get the information of video from server
     * only video
     *
     */
    private void getVideoInfo(){
        GetVideoService mService = mRetrofit.create(GetVideoService.class);
        Observable<IndexVideo> mObservable = mService.getPdfVideo();
        mObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<IndexVideo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: 视频信息订阅成功");
                    }

                    @Override
                    public void onNext(@NonNull IndexVideo indexVideo) {
                        if (indexVideo != null){
                            startFourDetails(indexVideo,ConstantCode.INDEX_VIDEO_TYPE);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: 视频信息完成");
                    }
                });
    }


    /**
     *
     * @param p the four kind information : pdf video rules error
     * @param type the type for the new activity to use
     */
    private void startFourDetails(Parcelable p , int type){
        Intent mIntent = new Intent(getActivity(),IndexFourInfoActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putParcelable("info",p);
        mIntent.putExtras(mBundle);
        mIntent.putExtra("type",type);
        startActivity(mIntent);
    }



    /**
     * this method is used to start an activity to send suggestion
     */
    private void suggestion(){
        startActivity(new Intent(getActivity(), SuggestionActivity.class));
    }
}
