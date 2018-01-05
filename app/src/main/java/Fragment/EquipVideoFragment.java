package Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.seuxxd.trainingapp.R;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import Adapter.PdfVideoAdapter;
import Constant.ConstantCode;
import Constant.URLConstant;
import Internet.EquipVideoService;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.equipment.EquipVideoResponse;
import model.fileandvideo.VideoInfo;
import model.scan.ScanVideoId;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipVideoFragment extends Fragment {



    private static final String TAG = "EquipVideoFragment";
    public static String mId;


    private Retrofit mRetorfit = new Retrofit.Builder()
            .baseUrl(URLConstant.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private String[] mVideoName;
    private String[] mVideoPath;
    @BindView(R.id.equip_video_list)
    ListView mVideoList;
//    @BindView(R.id.video_list_empty)
//    TextView mTextView;

    private PdfVideoAdapter mAdapter;
    public EquipVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (mId != null)
            getVideoInfo(mId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mId = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mId = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mLayout = inflater.inflate(R.layout.fragment_equip_video, container, false);
        ButterKnife.bind(this,mLayout);

        getVideoInfo(mId);

        return mLayout;
    }


    /**
     *
     * @param unitid video id
     */
    private void getVideoInfo(String unitid){
        EquipVideoService mService = mRetorfit.create(EquipVideoService.class);
        Observable<EquipVideoResponse> mObservable = mService.getVideoInfo(unitid);
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EquipVideoResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: 获取Video信息订阅成功");
                    }

                    @Override
                    public void onNext(@NonNull EquipVideoResponse equipVideoResponse) {
                        VideoInfo[] mVideoInfo = equipVideoResponse.getData();
                        mVideoPath = new String[mVideoInfo.length];
                        mVideoName = new String[mVideoInfo.length];
                        for (int i = 0 ; i < mVideoInfo.length ; i ++){
                            mVideoName[i] = mVideoInfo[i].getName();
                            mVideoPath[i] = mVideoInfo[i].getFilepath();
                        }
                        mAdapter = new PdfVideoAdapter(getActivity(),mVideoPath,mVideoName, ConstantCode.INDEX_VIDEO_TYPE);
                        mVideoList.setAdapter(mAdapter);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getVideoId(ScanVideoId id){
        Log.i(TAG, "getVideoId: 触发eventbus");
        mId = id.getId();
        getVideoInfo(mId);
    }

}
