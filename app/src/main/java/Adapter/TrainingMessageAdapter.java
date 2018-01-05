package Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seuxxd.trainingapp.PDFActivity;
import com.example.seuxxd.trainingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.message.MessageDetails;

/**
 * Created by SEUXXD on 2017/12/6.
 */

public class TrainingMessageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<MessageDetails> mList;


    private static final String TAG = "TrainingMessageAdapter";

    public TrainingMessageAdapter(Context context, ArrayList<MessageDetails> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        @BindView(R.id.training_content)
        TextView mContent;
        @BindView(R.id.training_name)
        TextView mName;
        @BindView(R.id.training_time)
        TextView mTime;
        @BindView(R.id.read_pdf)
        ImageView mImageView;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_message_list,parent,false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }
        else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mContent.setText(mList.get(position).getContentmes());
        mHolder.mName.setText(mList.get(position).getLoginname());
        mHolder.mTime.setText(mList.get(position).getTraintime());
        final String mEquipath = mList.get(position).getProfile();
        Log.i(TAG, "getView: " + mEquipath);
        mHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, PDFActivity.class);
                mIntent.putExtra("equipath",mEquipath);
                mContext.startActivity(mIntent);
            }
        });
        return convertView;
    }
}
