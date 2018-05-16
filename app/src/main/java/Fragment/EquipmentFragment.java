package Fragment;


import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seuxxd.trainingapp.R;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import Constant.URLConstant;
import Internet.EquipPdfService;
import Internet.EquipmentService;
import Internet.GetSecSpinService;
import Internet.GetSubCircuitService;
import Internet.GetSwitchService;
import Internet.GetVoltageService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnLongClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import model.equipment.EquipPdfResponse;
import model.equipment.EquipmentResponse;
import model.scan.ScanPdfId;
import model.scan.ScanVideoId;
import model.subcircuit.SubCircuit;
import model.subcircuit.SubCircuits;
import model.substation.SubStation;
import model.substation.Substations;
import model.voltagegrade.VoltageGrade;
import model.voltagegrade.VoltageGrades;
import model.vswitch.VSwitch;
import model.vswitch.VSwitchs;
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
    public static String mId;
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

    /**
     * 这些都是给spinner设置的全局变量
     */
    private String[] mFirstContent;
    private String[] mSecondContent;
    private String[] mThirdContent;
    private String[] mForthContent;
    private String[] mFifthContent;


    private SubStation[] mSubStationArray;
    private VoltageGrade[] mVoltageGradeArray;
    private VSwitch[] mVSwitchArray;
    private SubCircuit[] mSubCircuitArray;


    private boolean mFirstChooseSpinner = true;



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


    /**
     * spinner 的初始化以及控制操作
     */

    @BindView(R.id.first_spinner)
    Spinner mFirstSpinner;
    @BindView(R.id.second_spinner)
    Spinner mSecondSpinner;
    @BindView(R.id.third_spinner)
    Spinner mThirdSpinner;
    @BindView(R.id.forth_spinner)
    Spinner mForthSpinner;
    @BindView(R.id.fifth_spinner)
    Spinner mFifthSpinner;

    //第一个spinner的监听器
    @OnItemSelected(value = R.id.first_spinner,callback = OnItemSelected.Callback.ITEM_SELECTED)
    public void setFirstSpinner(int position){
        if (position == 0)
            getSecondSpinnerContent();
//            Toast.makeText(getContext(), "position: "  + mFirstContent[position], Toast.LENGTH_SHORT).show();
    }
    @OnItemSelected(value = R.id.first_spinner,callback = OnItemSelected.Callback.NOTHING_SELECTED)
    public void setFirstSpinnerNone(){
        Toast.makeText(getContext(), "nothing", Toast.LENGTH_SHORT).show();
    }



    /**
     * 获取第二层spinner的相关信息，接口文档中没有任何参数
     */
    private void getSecondSpinnerContent(){
        GetSecSpinService mSecService = mRetorfit.create(GetSecSpinService.class);
        Observable<Substations> mObservable = mSecService.getContent();
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Substations>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: 获取第二层spinner订阅成功");
                    }

                    @Override
                    public void onNext(Substations substations) {
                        int mResults = substations.getResults();
                        mSecondContent = new String[mResults];
                        if (mResults <= 0)
                            return;
                        mSubStationArray = substations.getRows();
                        for (int i = 0 ; i < mSubStationArray.length ; i ++){
                            mSecondContent[i] = mSubStationArray[i].getSubstationname();
                        }
                        ArrayAdapter<String> mSecondSpinnerAdapter = new ArrayAdapter<>(
                                getContext(),
                                android.R.layout.simple_spinner_item,
                                mSecondContent);
                        mSecondSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSecondSpinner.setAdapter(mSecondSpinnerAdapter);
                        mSecondSpinnerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "出错，请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //第二个spinner的监听器
    @OnItemSelected(value = R.id.second_spinner,callback = OnItemSelected.Callback.ITEM_SELECTED)
    public void setSecondSpinner(int position){
        getThirdSpinnerContent(mSubStationArray[position].getSubstationid());
    }
    @OnItemSelected(value = R.id.second_spinner,callback = OnItemSelected.Callback.NOTHING_SELECTED)
    public void setSecondSpinnerNone(){
        Toast.makeText(getContext(), "nothing", Toast.LENGTH_SHORT).show();
    }



    /**
     *
     * @param id the substationid of second content
     */
    private void getThirdSpinnerContent(int id){
        GetVoltageService mService = mRetorfit.create(GetVoltageService.class);
        Observable<VoltageGrades> mObservable = mService.getContent(id);
        mObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<VoltageGrades>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: 第三级订阅成功");
                    }

                    @Override
                    public void onNext(VoltageGrades voltageGrades) {
                        int mResults = voltageGrades.getResults();
                        if (mResults <= 0)
                            return;
                        mThirdContent = new String[mResults];
                        mVoltageGradeArray = voltageGrades.getRows();
                        for (int i = 0 ; i < mResults ; i ++){
                            mThirdContent[i] = mVoltageGradeArray[i].getVoltagegradename();
                        }
                        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(
                                getContext(),
                                android.R.layout.simple_spinner_item,
                                mThirdContent);
                        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mThirdSpinner.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "错误，请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @OnItemSelected(R.id.third_spinner)
    public void setThirdSpinner(int position){
        getForthSpinnerContent(mVoltageGradeArray[position].getVoltagegradeid());
    }
    @OnItemSelected(value = R.id.third_spinner,callback = OnItemSelected.Callback.NOTHING_SELECTED)
    public void setThirdSpinnerNone(){
        Toast.makeText(getContext(), "nothing", Toast.LENGTH_SHORT).show();
    }


    /**
     *
     * @param id 根据voltagegradeid进行选择
     */
    private void getForthSpinnerContent(int id){
        GetSwitchService mService = mRetorfit.create(GetSwitchService.class);
        Observable<VSwitchs> mObservable = mService.getContent(id);
        mObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<VSwitchs>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: 第四层订阅成功");
                    }

                    @Override
                    public void onNext(VSwitchs vSwitchs) {
                        int mResults = vSwitchs.getResults();
                        if (mResults <= 0)
                            return;
                        mForthContent = new String[mResults];
                        mVSwitchArray = vSwitchs.getRows();
                        for (int i = 0 ; i < mResults ; i ++)
                            mForthContent[i] = mVSwitchArray[i].getSwitchname();
                        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(
                                getContext(),
                                android.R.layout.simple_spinner_item,
                                mForthContent);
                        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mForthSpinner.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "出错，请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @OnItemSelected(value = R.id.forth_spinner,callback = OnItemSelected.Callback.ITEM_SELECTED)
    public void setForthSpinner(int position){
        getFifthSpinnerContent(mVSwitchArray[position].getSwitchid());
    }
    @OnItemSelected(value = R.id.forth_spinner,callback = OnItemSelected.Callback.NOTHING_SELECTED)
    public void setForthSpinnerNone(){
        Toast.makeText(getContext(), "nothing", Toast.LENGTH_SHORT).show();
    }



    private void getFifthSpinnerContent(int id){
        GetSubCircuitService mService = mRetorfit.create(GetSubCircuitService.class);
        Observable<SubCircuits> mObservable = mService.getContent(id);
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SubCircuits>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: 第五层订阅成功");
                    }

                    @Override
                    public void onNext(SubCircuits subCircuits) {
                        int mResults = subCircuits.getResults();
                        if (mResults <= 0)
                            return;
                        Log.i(TAG, "onNext: " + mResults);
                        mFifthContent = new String[mResults];
                        mSubCircuitArray = subCircuits.getRows();
                        for (int i = 0 ; i < mResults ; i ++)
                            mFifthContent[i] = mSubCircuitArray[i].getSubscircuitname();
                        for (String s : mFifthContent){
                            Log.i(TAG, "onNext: " + s);
                            if (s == null){
                                Log.i(TAG, "onNext: null");
                                Toast.makeText(getContext(), "数据为空", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(
                                getContext(),
                                android.R.layout.simple_spinner_item,
                                mFifthContent);
                        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mFifthSpinner.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "出错，请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @BindView(R.id.query_spinner)
    Button mQueryButton;
    @OnClick(R.id.query_spinner)
    public void getFinalInfo(){
        try {
            int position = mFifthSpinner.getSelectedItemPosition();
            int mId = mSubCircuitArray[position].getSubscircuitid();
            mEquipmentInfoText.setText(String.valueOf(mId));
            getEquipInfoNext(String.valueOf(mId));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "请按顺序选择后再点击查询！", Toast.LENGTH_SHORT).show();
        }
    }


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
        mFirstContent = getResources().getStringArray(R.array.first_spinner);
        ArrayAdapter<String> mFirstArrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                mFirstContent);
        mFirstArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFirstSpinner.setSelection(1,true);
        mFirstSpinner.setAdapter(mFirstArrayAdapter);
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
        mFirstChooseSpinner = false;
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
                    int mIndex = mInfo.indexOf('|');
                    String mUnitId = mInfo.substring(mIndex+1);
                    Log.i(TAG, "doGetInfo: " + mUnitId);
//                    Toast.makeText(getContext(), "id:" + mUnitId, Toast.LENGTH_SHORT).show();
                    getEquipInfoNext(mUnitId);
                    mEquipVideoFragment.mId = mUnitId;
                    mId = mUnitId;
                    break;
                case SCAN_RESULT_FAILED:
                    break;
                default:
                    break;
            }
        }
    }

    private void getEquipInfoNext(String mUnitId) {
        getEquipInfo(mUnitId);
        EventBus.getDefault().post(new ScanPdfId(mUnitId));
        EventBus.getDefault().post(new ScanVideoId(mUnitId));
    }


    private void getEquipInfo(String unitid){
        EquipmentService mEquipmentService = mRetorfit.create(EquipmentService.class);
//        getPDFInfo(unitid);
//        getVideoInfo(unitid);
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
                        EventBus.getDefault().post(equipmentResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "获取数据超时", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }




    class InnerViewPagerAdapter extends FragmentStatePagerAdapter{
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
