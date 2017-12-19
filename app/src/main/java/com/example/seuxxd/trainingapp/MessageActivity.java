package com.example.seuxxd.trainingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Adapter.TrainingMessageAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.MessageDetails;

public class MessageActivity extends AppCompatActivity {

    private static final String TAG = "MessageActivity";
    @BindView(R.id.message_list)
    ListView mMessageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        ArrayList<MessageDetails> mList = getIntent().getParcelableArrayListExtra("details");
        if (mList != null){
            TrainingMessageAdapter mAdapter = new TrainingMessageAdapter(this,mList);
            mMessageList.setAdapter(mAdapter);
        }
    }
}
