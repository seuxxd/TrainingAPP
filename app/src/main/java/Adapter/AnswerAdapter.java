package Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seuxxd.trainingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SEUXXD on 2017/12/7.
 */

public class AnswerAdapter extends BaseAdapter {
    private Context mContext;
    private List<Integer> mDrawables;
    private List<String> mRightAnswers;
    private List<String> mAnswer;
    private List<Integer> mColor;

    public AnswerAdapter(Context context , List<String> answer, List<Integer> drawables) {
        mAnswer = answer;
        mDrawables = drawables;
        mContext = context;
    }

//    public AnswerAdapter(List<String> answer, List<Integer> color, Context context, List<Integer> drawables) {
//        mAnswer = answer;
//        mColor = color;
//        mContext = context;
//        mDrawables = drawables;
//    }

    public AnswerAdapter(List<String> answer, List<Integer> color, Context context, List<Integer> drawables, List<String> rightAnswers) {
        mAnswer = answer;
        mColor = color;
        mContext = context;
        mDrawables = drawables;
        mRightAnswers = rightAnswers;
    }

    @Override
    public int getCount() {
        return mDrawables.size();
    }

    @Override
    public Object getItem(int position) {
        return mDrawables.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        @BindView(R.id.answer_image)
        ImageView mImageView;
        @BindView(R.id.answer_text)
        TextView  mTextView;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_answer_list,parent,false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }
        else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mImageView.setImageResource(mDrawables.get(position));
        mHolder.mTextView.setText(mAnswer.get(position));
//        1是正确，2是错误
        if (mColor != null && mRightAnswers != null){
            String temp = mAnswer.get(position) + "正确答案是：" + mRightAnswers.get(position);
            mHolder.mTextView.setText(temp);
            if (mColor.get(position) == 1){
                mHolder.mTextView.setBackgroundResource(R.color.rightColor);
            }
            else {
                mHolder.mTextView.setBackgroundColor(Color.RED);
            }
        }
        return convertView;
    }
}
