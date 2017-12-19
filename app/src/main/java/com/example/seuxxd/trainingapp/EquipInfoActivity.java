package com.example.seuxxd.trainingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Adapter.EquipmentAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.EquipmentData;

public class EquipInfoActivity extends AppCompatActivity {


    @BindView(R.id.equip_info_details)
    ListView mInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equip_info);
        ButterKnife.bind(this);
        Bundle mBundle = getIntent().getExtras();
        ArrayList<EquipmentData> mList = mBundle.getParcelableArrayList("data");
//        EquipmentData mData = mBundle.getParcelable("data");
        EquipmentAdapter mAdapter = new EquipmentAdapter(this, mList);
        mInfoList.setAdapter(mAdapter);
        mInfoList.setHeaderDividersEnabled(true);
        mInfoList.setDividerHeight(20);
    }
}
