package Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seuxxd.trainingapp.R;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Adapter.AnswerAdapter;
import Constant.URLConstant;
import Internet.ExamResultService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.FifthAnswer;
import model.ScoreSubmitResponse;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFinishFragment extends Fragment {


    private Intent mIntent;


    private List<Integer> mDrawables;
    private List<String>  mAnswers;//记录你的答案
    private List<String> mRightAnswers;//记录正确答案
    private List<Integer>  mResultColors;
//    private String mRealAnswer;
//    private String mYourAnswer;


    private AnswerAdapter mAdapter;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChange(FifthAnswer mAnswer){
        SharedPreferences mPreferences = getActivity().getSharedPreferences("answer",Context.MODE_PRIVATE);
        String mThisAnswer = mPreferences.getString("5","");
        mAnswers.add(4,mThisAnswer);
        mAdapter.notifyDataSetChanged();
    }


    @BindView(R.id.question_answer)
    Toolbar mToolbar;
    @BindView(R.id.your_answer)
    ListView mAnswerList;
    @BindView(R.id.submit_question)
    Button mSubmitButton;
    @OnClick(R.id.submit_question)
    public void onSubmit(){
        if (mIntent == null)
            return;
        int mCount = 0;
        String mResult = "";
        SharedPreferences mPreferences = getActivity()
                .getSharedPreferences("answer", Context.MODE_PRIVATE);
        mRightAnswers = new ArrayList<>();
        mResultColors = new ArrayList<>();
        for (int i = 1 ; i <= 5 ; i ++){

            String mRealAnswer = mPreferences.getString("right"+i,"B").toUpperCase();
            String mYourAnswer = mPreferences.getString(String.valueOf(i),"A").toUpperCase();
            Log.i(TAG, "onSubmit: real" + mRealAnswer);
            Log.i(TAG, "onSubmit: your" + mYourAnswer);
            if (mRealAnswer.equals(mYourAnswer)){
                mCount ++;
                Log.i(TAG, "onSubmit: " + mCount);
                mRightAnswers.add(mRealAnswer);
                mResultColors.add(1);
            }
            else{
                mRightAnswers.add(mRealAnswer);
                mResultColors.add(2);
            }

        }
        final int mDatabase = mPreferences.getInt("database",0);
        final int mDifficulty = mPreferences.getInt("difficulty",0);
        Calendar mCalendar = Calendar.getInstance();
        String mYear  = String.valueOf(mCalendar.get(Calendar.YEAR));
        String mMonth,mDay;
        int month = mCalendar.get(Calendar.MONTH)+1;
        if (month < 10)
            mMonth = "0"+month;
        else
            mMonth = String.valueOf(month);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        if (day < 10)
            mDay = "0" + day;
        else
            mDay   = String.valueOf(day);
        final String mDataFormat = mYear + "-" + mMonth + "-" + mDay;
        mPreferences = getActivity()
                .getSharedPreferences("user", Context.MODE_PRIVATE);
        final String mUsername = mPreferences.getString("userName","");
        final int mScore = mCount * 20;
        String mScoreResult = "";
        switch (mScore){
            case 100:
                mScoreResult = "满分，学得不错，继续努力！";
                break;
            case 80:
                mScoreResult = "80分，再认真一点，继续努力！";
                break;
            case 60:
            case 40:
            case 20:
            case 0:
                mScoreResult = mScore + "分，同志仍需努力！";
                break;
            default:
                mScoreResult = "继续努力！";
                break;
        }
        Log.i(TAG, "onSubmit: 成绩" + mScore);
        AlertDialog mDialog = new AlertDialog.Builder(getContext())
                .setTitle("最终成绩")
                .setMessage(mScoreResult)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Retrofit mRetrofit = new Retrofit.Builder()
                                .baseUrl(URLConstant.BASE_URL)
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        ExamResultService mExamResultService = mRetrofit.create(ExamResultService.class);
                        Observable<ScoreSubmitResponse> mExamResultObservable = mExamResultService.uploadResult(
                                mUsername,
                                String.valueOf(mScore),
                                mDataFormat,
                                String.valueOf(mDatabase),
                                String.valueOf(mDifficulty));
                        mExamResultObservable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ScoreSubmitResponse>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {
                                        Log.i(TAG, "onSubscribe: 订阅成功");
                                    }

                                    @Override
                                    public void onNext(@NonNull ScoreSubmitResponse responseBody) {
                                        if (responseBody.isSuccess()){
                                            Toast.makeText(getContext(), "成绩已提交成功", Toast.LENGTH_SHORT).show();
//                                            在这里展示具体题目的正确和错误
                                            mAdapter = new AnswerAdapter(
                                                    mAnswers,
                                                    mResultColors,
                                                    getContext(),
                                                    mDrawables,
                                                    mRightAnswers
                                                    );
                                            mAdapter.notifyDataSetChanged();
                                            mAnswerList.setAdapter(mAdapter);

                                        }
                                        else
                                            Toast.makeText(getContext(), "成绩提交失败", Toast.LENGTH_SHORT).show();
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
                })
                .show();

    }

    private static final String TAG = "QuestionFinishFragment";

    public QuestionFinishFragment() {
        // Required empty public constructor
    }

    private volatile static QuestionFinishFragment mFragment;

    public static QuestionFinishFragment newInstance(){
        if (mFragment == null){
            synchronized(QuestionFinishFragment.class) {
                if (mFragment == null)
                    return new QuestionFinishFragment();
            }
        }
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mLayout = inflater.inflate(R.layout.fragment_question_finish, container, false);
        ButterKnife.bind(this,mLayout);
        mToolbar.setTitle("");
        ((TextView)mToolbar.findViewById(R.id.toolbar_text)).setText("成绩界面");
        mIntent = getActivity().getIntent();
        mDrawables = new ArrayList<>();
        mDrawables.add(R.drawable.choice_1);
        mDrawables.add(R.drawable.choice_2);
        mDrawables.add(R.drawable.choice_3);
        mDrawables.add(R.drawable.choice_4);
        mDrawables.add(R.drawable.choice_5);
        mAnswers = new ArrayList<>();
        for (int i = 1 ; i <= 5 ; i ++){
            SharedPreferences mPreferences = getActivity().getSharedPreferences("answer",Context.MODE_PRIVATE);
            String mYourAnswer = mPreferences.getString(String.valueOf(i),"");
            mAnswers.add(mYourAnswer);
        }
        mAdapter = new AnswerAdapter(getContext(),mAnswers,mDrawables);
        mAnswerList.setDividerHeight(5);
        mAnswerList.setAdapter(mAdapter);
        return mLayout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
    public void onResume() {
        Log.i(TAG, "onResume: finish on Resume loaded");
        super.onResume();
        SharedPreferences mPreferences = getActivity().getSharedPreferences("answer",Context.MODE_PRIVATE);
        String mAnswer = mPreferences.getString("5","");
        mAnswers.add(4,mAnswer);
        mAdapter.notifyDataSetChanged();
    }
}
