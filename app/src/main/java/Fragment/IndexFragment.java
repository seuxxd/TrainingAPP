package Fragment;


import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.bumptech.glide.Glide;
import com.example.seuxxd.trainingapp.MessageActivity;
import com.example.seuxxd.trainingapp.NewsActivity;
import com.example.seuxxd.trainingapp.R;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sunfusheng.marqueeview.MarqueeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import Constant.URLConstant;
import Internet.DayImageService;
import Internet.MessageService;
import Internet.NewsService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.MessageDetails;
import model.MessageResponse;
import model.NewsDetail;
import model.NewsResponse;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends Fragment {



    private ArrayList<MessageDetails> mList ;
    private MessageDetails[] mDetails;


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
        String url = "http://www.jmzsjy.com/UploadFile/微课/地方风味小吃——宫廷香酥牛肉饼.mp4";
//        mPlayer.setUp(url, JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN,"兴趣");
        Log.i(TAG, "onCreateView: 创建视图");
        return mLayout;
    }

    private void getDayImage(){
        String url = "http://guolin.tech/api/bing_pic";
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
                            Toast.makeText(getActivity(), "获取推送信息出错，请检查网络或重新登陆！", Toast.LENGTH_SHORT).show();
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
}
