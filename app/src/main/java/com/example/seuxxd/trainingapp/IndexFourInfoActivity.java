package com.example.seuxxd.trainingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import Adapter.PdfVideoAdapter;
import Constant.ConstantCode;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.index.IndexError;
import model.index.IndexErrorDetails;
import model.index.IndexPdf;
import model.index.IndexPdfDetails;
import model.index.IndexRules;
import model.index.IndexRulesDetails;
import model.index.IndexVideo;
import model.index.IndexVideoDetails;

public class IndexFourInfoActivity extends AppCompatActivity {


    private int mType;
    private PdfVideoAdapter mAdapter;



    private static final String TAG = "IndexFourInfoActivity";

    @BindView(R.id.index_pdf_video_list)
    ListView mListView;

    @BindView(R.id.four_toolbar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_pdfvideo);
        ButterKnife.bind(this);
        mType = getIntent().getIntExtra("type",0);
//        根据type进行对应的操作
        switch (mType){
            case 0:
                Toast.makeText(this, "数据有误，请返回上一级重试", Toast.LENGTH_SHORT).show();
                break;
            case ConstantCode.INDEX_RULES_TYPE:
                IndexRules mRules = getIntent().getExtras().getParcelable("info");
                if (mRules != null){
                    String[] mNames = new String[mRules.getResults()];
                    String[] mPaths = new String[mRules.getResults()];
                    IndexRulesDetails[] mDetails = mRules.getPdf();
                    for (int i = 0 ; i < mDetails.length ; i ++){
                        mNames[i] = mDetails[i].getRulearticname();
                        mPaths[i] = mDetails[i].getRulearticpath();
                    }
                    mAdapter = new PdfVideoAdapter(this,mPaths,mNames,ConstantCode.INDEX_RULES_TYPE);
                    mToolbar.setTitle("规则规章");
                    mToolbar.setBackgroundResource(R.color.loginColor);
                    setSupportActionBar(mToolbar);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    mListView.setAdapter(mAdapter);
                }
                break;
            case ConstantCode.INDEX_PDF_TYPE:
                IndexPdf mPdf = getIntent().getExtras().getParcelable("info");
                if (mPdf != null){
                    String[] mNames = new String[mPdf.getResults()];
                    String[] mPaths = new String[mPdf.getResults()];
                    IndexPdfDetails[] mDetails = mPdf.getPdf();
                    for (int i = 0 ; i < mDetails.length ; i ++){
                        mNames[i] = mDetails[i].getDocucourname();
                        mPaths[i] = mDetails[i].getDocucourpath();
                    }
                    mAdapter = new PdfVideoAdapter(this,mPaths,mNames,ConstantCode.INDEX_PDF_TYPE);
                    mToolbar.setTitle("文档课件");
                    mToolbar.setBackgroundResource(R.color.loginColor);
                    setSupportActionBar(mToolbar);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    mListView.setAdapter(mAdapter);
                }
                break;
            case ConstantCode.INDEX_VIDEO_TYPE:
                IndexVideo mVideo = getIntent().getExtras().getParcelable("info");
                if (mVideo != null){
                    String[] mNames = new String[mVideo.getResults()];
                    String[] mPaths = new String[mVideo.getResults()];
                    IndexVideoDetails[] mDetails = mVideo.getWmv();
                    for (int i = 0 ; i < mDetails.length ; i ++){
                        mNames[i] = mDetails[i].getVideocourname();
                        mPaths[i] = mDetails[i].getVideocourpath();
                    }
                    mAdapter = new PdfVideoAdapter(this,mPaths,mNames,ConstantCode.INDEX_VIDEO_TYPE);
                    mToolbar.setTitle("视频课件");
                    mToolbar.setBackgroundResource(R.color.loginColor);
                    setSupportActionBar(mToolbar);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    mListView.setAdapter(mAdapter);
                }
                break;
            case ConstantCode.INDEX_ERROR_TYPE:
                IndexError mError = getIntent().getExtras().getParcelable("info");
                if (mError != null){
                    String[] mNames = new String[mError.getResults()];
                    String[] mPaths = new String[mError.getResults()];
                    IndexErrorDetails[] mDetails = mError.getPdf();
                    int i = 0;
                    for (; i < mDetails.length ; i ++){
                        mNames[i] = mDetails[i].getTypicanalyname();
                        mPaths[i] = mDetails[i].getTypicanalypath();
                    }
                    mDetails = mError.getWmv();
                    for (int j = 0 ; j < mDetails.length ; j ++){
                        mNames[i+j] = mDetails[j].getTypicanalyname();
                        mPaths[i+j] = mDetails[j].getTypicanalypath();
                    }
                    mAdapter = new PdfVideoAdapter(this,mPaths,mNames,ConstantCode.INDEX_ERROR_TYPE);
                    mToolbar.setTitle("典型错误");
                    mToolbar.setBackgroundResource(R.color.loginColor);
                    setSupportActionBar(mToolbar);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    mListView.setAdapter(mAdapter);
                }
                break;
            default:
                break;
        }
    }


}
