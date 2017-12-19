package com.example.seuxxd.trainingapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.source.InputStreamSource;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import Constant.URLConstant;
import Internet.DownloadService;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PDFActivity extends AppCompatActivity {


    private static final String TAG = "PDFActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("文档加载中...");
        mDialog.setCancelable(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.show();
        String mMessageId = getIntent().getStringExtra("equipath");
        final PDFView mView = (PDFView) findViewById(R.id.pdf_reader);
        Retrofit mRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(URLConstant.BASE_URL)
//                .baseUrl(URLConstant.TEST_PDF_URL)
                .build();
        DownloadService mService = mRetrofit.create(DownloadService.class);
        Log.i(TAG, "onCreate: equipath" + mMessageId);
        Observable<ResponseBody> mObservable = mService.doDownload(mMessageId);
//        Observable<ResponseBody> mObservable = mService.doTestPdf("Java.pdf");
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: 订阅成功");
                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            InputStream mInput = responseBody.byteStream();
                            mView.fromStream(mInput)
                                    .enableSwipe(true)
                                    .swipeHorizontal(false)
                                    .enableDoubletap(true)
                                    .defaultPage(0)
                                    .onLoad(new OnLoadCompleteListener() {
                                        @Override
                                        public void loadComplete(int nbPages) {
//                                            在这里有完成回调，使用加载UI展示
                                            Log.i(TAG, "loadComplete: ");
                                            mDialog.dismiss();
                                        }
                                    })
                                    .onError(new OnErrorListener() {
                                        @Override
                                        public void onError(Throwable t) {
                                            t.printStackTrace();
                                        }
                                    })
                                    .load();
                            Log.i(TAG, "onNext: " + mInput);
                        } catch (Exception e) {
                            e.printStackTrace();
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
//        String mUrl = getIntent().getStringExtra("url");
//        Uri mUri = Uri.parse(mUrl);
//        PDFView pdfView = (PDFView) findViewById(R.id.pdf_reader);
//        String mURL = "http://192.168.2.101:8080/db/AppTrainPdf.action?messageid=14";
//

//        WebView mView = (WebView) findViewById(R.id.pdf_reader);
//        mView.loadUrl("https://docs.google.com/viewer?url=192.168.2.101:8080/db/AppTrainPdf.action?messageid=14");
    }
}
