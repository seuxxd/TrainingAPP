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
import Internet.EquipPdfService;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.equipment.EquipPdfResponse;
import model.fileandvideo.FileInfo;
import model.scan.ScanPdfId;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipPDFFragment extends Fragment {



    private static final String TAG = "EquipPDFFragment";

    public static String mId;

    private FileInfo[] mFileInfo ;
    private String[] mFilePath ;
    private String[] mFileName ;
    private PdfVideoAdapter mAdapter;

    private Retrofit mRetorfit = new Retrofit.Builder()
            .baseUrl(URLConstant.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public EquipPDFFragment() {
        // Required empty public constructor
    }


    @BindView(R.id.equip_pdf_list)
    ListView mListView;
//    @BindView(R.id.pdf_list_empty)
//    TextView mTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (mId != null)
            getPDFInfo(mId);
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
        View mLayout = inflater.inflate(R.layout.fragment_equip_pdf, container, false);
        ButterKnife.bind(this,mLayout);

        getPDFInfo(mId);

        return mLayout;
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
                            mFileInfo = equipPdfResponse.getData();
                            mFilePath = new String[mFileInfo.length];
                            mFileName = new String[mFileInfo.length];
                            for (int i = 0 ; i < mFileInfo.length ; i ++){
                                mFilePath[i] = mFileInfo[i].getFilepath();
                                mFileName[i] = mFileInfo[i].getName();
                            }
                            mAdapter = new PdfVideoAdapter(getActivity(),mFilePath,mFileName, ConstantCode.INDEX_PDF_TYPE);
                            mListView.setAdapter(mAdapter);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPDFId(ScanPdfId id){
        Log.i(TAG, "getPDFId: 触发eventbus");
        mId = id.getId();
        getPDFInfo(mId);
    }

}
