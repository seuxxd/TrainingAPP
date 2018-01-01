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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import Adapter.PdfVideoAdapter;
import Constant.ConstantCode;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.EquipVideoResponse;
import model.VideoInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipVideoFragment extends Fragment {



    private static final String TAG = "EquipVideoFragment";


    private String[] mVideoName;
    private String[] mVideoPath;
    @BindView(R.id.equip_video_list)
    ListView mVideoList;
    @BindView(R.id.video_list_empty)
    TextView mTextView;

    private PdfVideoAdapter mAdapter;
    public EquipVideoFragment() {
        // Required empty public constructor
    }


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
        View mLayout = inflater.inflate(R.layout.fragment_equip_video, container, false);
        ButterKnife.bind(this,mLayout);
        if (mAdapter == null) {
            mVideoList.setEmptyView(mTextView);
        }
        else{
            mVideoList.setAdapter(mAdapter);
        }
        return mLayout;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFresh(EquipVideoResponse equipVideoResponse){


        Log.i(TAG, "onFresh: ");
        VideoInfo[] mVideoInfo = equipVideoResponse.getData();
        mVideoPath = new String[mVideoInfo.length];
        mVideoName = new String[mVideoInfo.length];
        for (int i = 0 ; i < mVideoInfo.length ; i ++){
            mVideoName[i] = mVideoInfo[i].getName();
            mVideoPath[i] = mVideoInfo[i].getFilepath();
            Log.i(TAG, "onFresh: " + mVideoName[i]);
            Log.i(TAG, "onFresh: " + mVideoPath[i]);
        }
        mAdapter = new PdfVideoAdapter(getActivity(),mVideoPath,mVideoName, ConstantCode.INDEX_VIDEO_TYPE);
        mVideoList.setAdapter(mAdapter);
    }

}
