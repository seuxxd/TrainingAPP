package com.example.seuxxd.trainingapp;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

import Adapter.EquipmentAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.equipment.EquipmentData;

public class EquipInfoActivity extends AppCompatActivity {


    @BindView(R.id.equip_info_details)
    ListView mInfoList;
    @BindView(R.id.equip_toolbar)
    Toolbar mEquipToolbar;

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
        mEquipToolbar.setTitle("设备详细信息");
        mEquipToolbar.setBackgroundResource(R.color.loginColor);
        setSupportActionBar(mEquipToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable mDrawable = getResources().getDrawable(R.drawable.list_divider);
        mInfoList.setDivider(mDrawable);
        mInfoList.setDividerHeight(30);
    }
}
