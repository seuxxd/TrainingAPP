package Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.seuxxd.trainingapp.ExamActivity;
import com.example.seuxxd.trainingapp.R;

import org.greenrobot.eventbus.EventBus;

import Constant.SourceConstant;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.FifthAnswer;
import model.QuestionData;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {



    private static final String TAG = "QuestionFragment";
    public QuestionFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.question_toolbar)
    Toolbar mQuestionToolbar;
    @BindView(R.id.toolbar_text)
    TextView mToolbarText;
    @BindView(R.id.toolbar_icon)
    ImageView mToolbarIcon;
    @BindView(R.id.question_main)
    TextView mQuestionText;
    @BindView(R.id.choose_group)
    RadioGroup mGroup;
    @BindView(R.id.choose_a)
    RadioButton mButtonA;
    @BindView(R.id.choose_b)
    RadioButton mButtonB;
    @BindView(R.id.choose_c)
    RadioButton mButtonC;
    @BindView(R.id.choose_d)
    RadioButton mButtonD;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle mBundle = getArguments();
        QuestionData mData = mBundle.getParcelable("data");
        final String mIndex = String.valueOf(mBundle.getInt("index"));
        final int mType = mBundle.getInt("type");
        View mLayout = inflater.inflate(R.layout.fragment_question, container, false);
        ButterKnife.bind(this,mLayout);
        mQuestionToolbar.setTitle("");
        mToolbarIcon.setImageResource(SourceConstant.newInstance().getQuestionNumber()[Integer.valueOf(mIndex)-1]);
        mToolbarText.setText("题目" + mIndex);
        mQuestionText.setMovementMethod(ScrollingMovementMethod.getInstance());
        final SharedPreferences.Editor mEditor = getActivity().getSharedPreferences("answer", Context.MODE_PRIVATE).edit();
        if (mData != null){
            String mQuestionMain = mData.getQuestionbank();
            String mChooseA      = mData.getAoption();
            String mChooseB      = mData.getBoption();
            String mChooseC      = mData.getCoption();
            String mChooseD      = mData.getDoption();
//            final String mQuestionId   = mData.getQuestionid();
            final String mAnswer       = mData.getAnswer();
            Log.i(TAG, "onCreateView: mAnswer" + mAnswer);
            mQuestionText.setText(mQuestionMain);
            mButtonA.setText(mChooseA);
            mButtonB.setText(mChooseB);
            mButtonC.setText(mChooseC);
            mButtonD.setText(mChooseD);
            mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    Log.i(TAG, "onCheckedChanged: " + checkedId);
//                    setArguments和getArguments方法因为初始化顺序问题为产生空数据
//                    所以只好使用intent传递数据
                    Intent mIntent = new Intent();
                    mIntent.putExtra("right"+mIndex,mAnswer);
                    mEditor.putString("right"+mIndex,mAnswer);
                    mEditor.putInt("type",mType);
                    Log.i(TAG, "index: " + mIndex);
                    switch (checkedId){
                        case R.id.choose_a:
                            mIntent.putExtra(mIndex,"A");
                            mEditor.putString(mIndex,"A");
                            break;
                        case R.id.choose_b:
                            mIntent.putExtra(mIndex,"B");
                            mEditor.putString(mIndex,"B");
                            break;
                        case R.id.choose_c:
                            mIntent.putExtra(mIndex,"C");
                            mEditor.putString(mIndex,"C");
                            break;
                        case R.id.choose_d:
                            mIntent.putExtra(mIndex,"D");
                            mEditor.putString(mIndex,"D");
                            break;
                        default:
                            break;
                    }
                    mEditor.apply();
                    if (mIndex.equals("5"))
                        EventBus.getDefault().post(new FifthAnswer());
                    getActivity().setIntent(mIntent);
                    Log.i(TAG, "onCheckedChanged: " + getActivity().getSharedPreferences("answer",Context.MODE_PRIVATE).getInt("type",100));
                }
            });

        }
        return mLayout;
    }

}
