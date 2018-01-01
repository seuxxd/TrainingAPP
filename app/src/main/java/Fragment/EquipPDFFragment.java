package Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.seuxxd.trainingapp.PDFActivity;
import com.example.seuxxd.trainingapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import Adapter.PdfVideoAdapter;
import Constant.ConstantCode;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.EquipPdfResponse;
import model.EquipmentResponse;
import model.FileInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipPDFFragment extends Fragment {



    private static final String TAG = "EquipPDFFragment";
    private FileInfo[] mFileInfo ;
    private String[] mFilePath ;
    private String[] mFileName ;
    private PdfVideoAdapter mAdapter;


    public EquipPDFFragment() {
        // Required empty public constructor
    }


    @BindView(R.id.equip_pdf_list)
    ListView mListView;
    @BindView(R.id.pdf_list_empty)
    TextView mTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mLayout = inflater.inflate(R.layout.fragment_equip_pdf, container, false);
        ButterKnife.bind(this,mLayout);
        if (mFileName == null){
            mListView.setEmptyView(mTextView);
        }
        else {
            mAdapter = new PdfVideoAdapter(getActivity(),mFilePath,mFileName,1);
            mListView.setAdapter(mAdapter);
        }
        return mLayout;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResponse(EquipPdfResponse equipPdfResponse){
        //TODO 接收数据，设置Adapter
        mFileInfo = equipPdfResponse.getData();
        mFilePath = new String[mFileInfo.length];
        mFileName = new String[mFileInfo.length];
        for (int i = 0 ; i < mFileInfo.length ; i ++){
            mFilePath[i] = mFileInfo[i].getFilepath();
            mFileName[i] = mFileInfo[i].getName();
            Log.i(TAG, "onResponse: " + mFileName[i]);
            Log.i(TAG, "onResponse: " + mFilePath[i]);
        }
        mAdapter = new PdfVideoAdapter(getActivity(),mFilePath,mFileName, ConstantCode.INDEX_PDF_TYPE);
        mListView.setAdapter(mAdapter);
    }

}
