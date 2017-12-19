package Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    @BindView(R.id.difficulty_choose)
    Spinner mDifficultyChooser;
    @BindView(R.id.database_choose)
    Spinner mDatabaseChooser;
    @BindView(R.id.start_exam)
    Button mStartExamButton;
    @OnClick(R.id.start_exam)
    public void startExam(){
        Log.i(TAG, "难度: " + mDifficultyChoosePosition );
        Log.i(TAG, "题库: " + mDatabaseChoosePosition );
        SharedPreferences.Editor mEditor = getActivity()
                .getSharedPreferences("answer", Context.MODE_PRIVATE)
                .edit();
        mEditor.putInt("database",mDatabaseChoosePosition);
        mEditor.putInt("difficulty",mDifficultyChoosePosition);
        mEditor.apply();
        ExamService mExamService = mRetrofit.create(ExamService.class);
        Observable<ExamResponse> mExamObservable =
                mExamService.getExam(
                        String.valueOf(mDatabaseChoosePosition),
                        String.valueOf(mDifficultyChoosePosition));
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



    public ExamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mLayout = inflater.inflate(R.layout.fragment_exam, container, false);
        ButterKnife.bind(this,mLayout);
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
        return mLayout;
    }

}
