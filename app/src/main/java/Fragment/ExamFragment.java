package Fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.seuxxd.trainingapp.ExamActivity;
import com.example.seuxxd.trainingapp.R;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import Constant.URLConstant;
import Internet.ExamService;
import Internet.ExamSpecialService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.ExamResponse;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamFragment extends Fragment {



    private int mDifficultyChoosePosition = 0;
    private int mDatabaseChoosePosition = 0;
    private static final String TAG = "ExamFragment";
    final Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(URLConstant.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

//    @BindView(R.id.difficulty_choose)
//    Spinner mDifficultyChooser;
//    @BindView(R.id.database_choose)
//    Spinner mDatabaseChooser;

    @BindView(R.id.self_exam)
    Button mSelfExamButton;
//    自我测评
    @OnClick(R.id.self_exam)
    public void selfExam(){
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.custom_self_exam,null);
        Spinner mDatabaseChooser = (Spinner) mView.findViewById(R.id.database_choose);
        final Spinner mDifficultyChooser = (Spinner) mView.findViewById(R.id.difficulty_choose);
        mDatabaseChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDatabaseChoosePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDatabaseChoosePosition = 0;
            }
        });
        mDifficultyChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDifficultyChoosePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDifficultyChoosePosition = 0;
            }
        });

        AlertDialog mDialog = new AlertDialog.Builder(getContext())
                .setView(mView)
                .setPositiveButton("开始考试", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        获取自评考试题目
                        getSelfExam(
                                String.valueOf(mDatabaseChoosePosition),
                                String.valueOf(mDifficultyChoosePosition));
                    }
                })
                .setNegativeButton("取消考试", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                })
                .setCancelable(false)
                .create();
        mDialog.show();
    }


    @BindView(R.id.start_exam)
    Button mStartExamButton;
    @OnClick(R.id.start_exam)
    public void startExam(){
        getSpecialExam();
    }



    public ExamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mLayout = inflater.inflate(R.layout.fragment_exam, container, false);
        ButterKnife.bind(this,mLayout);
        return mLayout;
    }

    /**
     *
     * @param database the choose of database
     * @param difficulty the choose of difficulty
     *                   the result of this function is to start
     *                   a new Activity that contains the questions for self-test
     *                   if the number is less than 5, give a toast to user
     */
    private void getSelfExam(String database , String difficulty){
        ExamService mExamService = mRetrofit.create(ExamService.class);
        Observable<ExamResponse> mExamObservable =
                mExamService.getExam(database, difficulty);
        mExamObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ExamResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: 订阅成功");
                    }

                    @Override
                    public void onNext(@NonNull ExamResponse examResponse) {
                        Log.i(TAG, "onNext: " + examResponse.getResults());
                        Intent mExamIntent = new Intent(getActivity(), ExamActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putParcelable("exam",examResponse);
                        mExamIntent.putExtras(mBundle);
                        mExamIntent.putExtra("type",0);//自测类别
                        if (examResponse.getResults() < 5){
                            Toast.makeText(getContext(), "题库题目数量不足", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        startActivity(mExamIntent);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(),"出错", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     *
     */
    private void getSpecialExam(){
        ExamSpecialService mService = mRetrofit.create(ExamSpecialService.class);
        Observable<ExamResponse> mObservable = mService.getSpecialExam();
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ExamResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: 特殊考试订阅成功");
                    }

                    @Override
                    public void onNext(@NonNull ExamResponse examResponse) {
//                        这个题目确定为5题，不会变更
                        Intent mExamIntent = new Intent(getActivity(),ExamActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putParcelable("exam",examResponse);
                        mExamIntent.putExtras(mBundle);
                        mExamIntent.putExtra("type",1);//特殊考试类别
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

}
