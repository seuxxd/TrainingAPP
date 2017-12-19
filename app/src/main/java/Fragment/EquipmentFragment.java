package Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seuxxd.trainingapp.R;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import Constant.URLConstant;
import Internet.EquipPdfService;
import Internet.EquipVideoService;
import Internet.EquipmentService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.EquipPdfResponse;
import model.EquipVideoResponse;
import model.EquipmentData;
import model.EquipmentResponse;
import model.FileInfo;
import model.VideoInfo;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zxing.activity.CaptureActivity;

import static Constant.ConstantCode.SCAN_REQUEST_CODE;
import static Constant.ConstantCode.SCAN_RESULT_FAILED;
import static Constant.ConstantCode.SCAN_RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentFragment extends Fragment {




    private static final String TAG = "EquipmentFragment";
    private String[] mTitles = {"信息","文档","视频"};
    private List<Fragment> mFragmentList;
    private EquipInfoFragment mEquipInfoFragment;
    private EquipPDFFragment mEquipPDFFragment;
    private EquipVideoFragment mEquipVideoFragment;
    private InnerViewPagerAdapter mViewPagerAdapter;
    private Retrofit mRetorfit = new Retrofit.Builder()
            .baseUrl(URLConstant.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
//    View绑定
    @BindView(R.id.scan_info_title)
    TextView mScanTitle;

    @BindView(R.id.scan_icon)
    ImageView mImageIcon;
    @OnClick(R.id.scan_icon)
    public void doScan(){
        Intent intent = new Intent(getContext(), CaptureActivity.class);
        startActivityForResult(intent,SCAN_REQUEST_CODE);
    }

    @BindView(R.id.scan_info)
    TextView mEquipmentInfoText;
    @OnClick(R.id.scan_info)
    public void doGetInfo(){
        String mInfo = mEquipmentInfoText.getText().toString();
        if (TextUtils.isEmpty(mInfo))
            return;
        String[] temp = mInfo.split("|");
        String mUnitId = temp[temp.length - 1];
        Log.i(TAG, "doGetInfo: " + mUnitId);

    }

    @BindView(R.id.equipment_inner_tab)
    TabLayout mInnerTab;
    @BindView(R.id.equipment_inner_pager)
    ViewPager mInnerPager;



    public EquipmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFragmentList = new ArrayList<>();
        mEquipInfoFragment = new EquipInfoFragment();
        mEquipPDFFragment = new EquipPDFFragment();
        mEquipVideoFragment = new EquipVideoFragment();
        mFragmentList.add(mEquipInfoFragment);
        mFragmentList.add(mEquipPDFFragment);
        mFragmentList.add(mEquipVideoFragment);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mEquipmentLayout = inflater.inflate(R.layout.fragment_equipment,container,false);
        ButterKnife.bind(this,mEquipmentLayout);
        FragmentManager mFragmentManager = getChildFragmentManager();
        mViewPagerAdapter = new InnerViewPagerAdapter(mFragmentManager);
        mInnerPager.setAdapter(mViewPagerAdapter);
        mInnerTab.setupWithViewPager(mInnerPager);
        return mEquipmentLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//      针对扫码的返回
        if (requestCode == SCAN_REQUEST_CODE){
            switch (resultCode){
                case SCAN_RESULT_OK:
                    String mInfo = data.getStringExtra("result");
                    mEquipmentInfoText.setText(mInfo);
                    Log.i(TAG, "onActivityResult: " + mInfo);
                    if (TextUtils.isEmpty(mInfo))
                        return;
                    String[] temp = mInfo.split("|");
                    String mUnitId = temp[temp.length - 1];
                    Log.i(TAG, "doGetInfo: " + mUnitId);
                    getEquipInfo(mUnitId);
                    break;
                case SCAN_RESULT_FAILED:
                    break;
                default:
                    break;
            }
        }
    }


    private void getEquipInfo(String unitid){
        EquipmentService mEquipmentService = mRetorfit.create(EquipmentService.class);
        getPDFInfo(unitid);
        getVideoInfo(unitid);
        Observable<EquipmentResponse> mObservable = mEquipmentService.getEquipmentInfo(unitid);
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EquipmentResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: " + "订阅成功");
                    }

                    @Override
                    public void onNext(@NonNull EquipmentResponse equipmentResponse) {
                        boolean mSuccess = equipmentResponse.isSuccess();
                        if (!mSuccess){
                            Toast.makeText(getActivity(), "查询失败，请重试", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            EventBus.getDefault().post(equipmentResponse);
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
     * 
     * @param unitid pdf info id
     */
    private void getPDFInfo(String unitid){
        EquipPdfService mService = mRetorfit.create(EquipPdfService.class);
        Observable<EquipPdfResponse> mObservable = mService.getPdfInfo(unitid);
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EquipPdfResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: 获取PDF信息订阅成功");
                    }

                    @Override
                    public void onNext(@NonNull EquipPdfResponse equipPdfResponse) {
                        if (equipPdfResponse.isSuccess()){
                            EventBus.getDefault().post(equipPdfResponse);
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
                        Log.i(TAG, "onNext: post");
                        EventBus.getDefault().post(equipVideoResponse);
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
    class InnerViewPagerAdapter extends FragmentPagerAdapter{
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        public InnerViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
