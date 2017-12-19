package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seuxxd.trainingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SEUXXD on 2017/12/11.
 */

public class PersonDetailAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mLists;
    private int[] mDrawables;

    public PersonDetailAdapter(Context context, int[] drawables, String[] lists) {
        mContext = context;
        mDrawables = drawables;
        mLists = lists;
    }

    @Override
    public int getCount() {
        return mLists.length;
    }

    @Override
    public Object getItem(int position) {
        return mLists[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_myinfo_list,parent,false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }
        else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mImageView.setImageResource(mDrawables[position]);
        mHolder.mTextView.setText(mLists[position]);
        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.myInfo_icon)
        ImageView mImageView;
        @BindView(R.id.myInfo_text)
        TextView mTextView;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}
