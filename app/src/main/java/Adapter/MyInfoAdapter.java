package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seuxxd.trainingapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SEUXXD on 2017/11/2.
 */

public class MyInfoAdapter extends BaseAdapter{

    private static final int ITEM = 0;
    private static final int SEPERATOR = 1;
    private static final int TYPE_MAX_COUNT = 2;
    private Context mContext;
    private int[] mTitleImageList;
    private String[] mTitleStringList;
    private List<Integer> mImageList;
    private List<String> mSeperatorList;
    public MyInfoAdapter(Context context , int[] ints , String[] strings) {
        mContext = context;
        mTitleImageList = ints;
        mTitleStringList = strings;
        mImageList = new ArrayList<>();
        mSeperatorList = new ArrayList<>();
//        因为要分割，所以取0，2为分割线
        for (int i = 0 ; i < strings.length ; i ++){
            if (i == 0 || i == 1){
                mImageList.add(0);
                mSeperatorList.add("");
            }
            mImageList.add(ints[i]);
            mSeperatorList.add(strings[i]);
        }
    }

    @Override
    public Object getItem(int position) {
        return mSeperatorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return mSeperatorList.get(position).equals("") ? SEPERATOR : ITEM;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        SeperatorViewHolder mSeperatorViewHolder = null;
        int mType = getItemViewType(position);
        if (convertView == null){
            switch (mType){
                case ITEM:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_myinfo_list,parent,false);
                    mViewHolder = new ViewHolder(convertView);
                    convertView.setTag(mViewHolder);
                    break;
                case SEPERATOR:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_seperator,parent,false);
                    mSeperatorViewHolder = new SeperatorViewHolder(convertView);
                    convertView.setTag(mSeperatorViewHolder);
                    break;
            }

        }
        else{
            switch (mType){
                case ITEM:
                    mViewHolder = (ViewHolder) convertView.getTag();
                    break;
                case SEPERATOR:
                    mSeperatorViewHolder = (SeperatorViewHolder) convertView.getTag();
                    break;
            }
        }
        switch (mType){
            case ITEM:
                mViewHolder.mImageView.setImageResource(mImageList.get(position));
                mViewHolder.mTextView.setText(mSeperatorList.get(position));
                break;
            case SEPERATOR:
                mSeperatorViewHolder.mSeperatorView.setText("");
                break;
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return mSeperatorList.size();
    }


    static class ViewHolder{

        @BindView(R.id.myInfo_icon)
        ImageView mImageView;
        @BindView(R.id.myInfo_text)
        TextView  mTextView;


        public ViewHolder(View view) {
            ButterKnife.bind(this,view);

        }
    }

    static class SeperatorViewHolder{
        @BindView(R.id.item_seperator)
        TextView mSeperatorView;

        public SeperatorViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}
