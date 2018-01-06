package Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.seuxxd.trainingapp.EquipInfoActivity;
import com.example.seuxxd.trainingapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.equipment.EquipmentData;
import model.equipment.EquipmentResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipInfoFragment extends Fragment {



    private EquipmentData[] mEquipmentData;
    private ArrayList<EquipmentData> mList;


    private static final String TAG = "EquipInfoFragment";

    @BindView(R.id.equipment_inner_info)
    TextView mInfoView;
    @OnClick(R.id.equipment_inner_info)
    public void doGetDetail(){
        Log.i(TAG, "doGetDetail: onClick");
        if (TextUtils.isEmpty(mInfoView.getText().toString())){
            return;
        }
        if (mEquipmentData == null){
            Log.i(TAG, "doGetDetail: " + "无数据");
            return;
        }
        mList = new ArrayList<>();
        Intent mIntent = new Intent(getActivity(), EquipInfoActivity.class);
        Bundle mBundle = new Bundle();
        for (EquipmentData data : mEquipmentData){
            mList.add(data);
        }
        mBundle.putParcelableArrayList("data",mList);
        mIntent.putExtras(mBundle);
        startActivity(mIntent);
    }
    public EquipInfoFragment() {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EquipmentResponse equipmentResponse){
        EquipmentData[] mData = equipmentResponse.getData();
        Log.i(TAG, "onEvent: 设备信息event触发");
        if (mData.length >= 1){
            mEquipmentData = mData;
            int mCount = mData.length;
            mInfoView.setText("点击查看详细" + mCount + "条设备信息");
        }
        else {
            mInfoView.setText("无相关信息，请联系后台管理员");
            mEquipmentData = null;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mLayout = inflater.inflate(R.layout.fragment_equip_info,container,false);
        ButterKnife.bind(this,mLayout);
        if (mEquipmentData != null){
            int mCount = mEquipmentData.length;
            mInfoView.setText("点击查看详细" + mCount + "条设备信息");
        }
        else {
            mInfoView.setText("无相关信息，请联系后台管理员");
        }
        return mLayout;
    }
}