package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.seuxxd.trainingapp.PDFActivity;
import com.example.seuxxd.trainingapp.R;
import com.example.seuxxd.trainingapp.VideoPlayerActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SEUXXD on 2017/12/10.
 */

public class PdfVideoAdapter extends BaseAdapter {


    private Context mContext;
    private String[] mNameList;
    private String[] mFilePathList;
    private int mMode;//1->pdf,2->video

    public PdfVideoAdapter(Context context, String[] filePathList, String[] nameList , int mode) {
        mContext = context;
        mFilePathList = filePathList;
        mNameList = nameList;
        mMode = mode;
    }

    @Override
    public int getCount() {
        return mNameList.length;
    }

    @Override
    public Object getItem(int position) {
        return mNameList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder{
        @BindView(R.id.pdf_video_name)
        TextView mNameView;
        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null){
            convertView = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.custom_pdf_video_list,parent,false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }
        else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mNameView.setText(mNameList[position]);
        mHolder.mNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMode == 1){
                    Intent mIntent = new Intent(mContext, PDFActivity.class);
                    mIntent.putExtra("equipath",mFilePathList[position]);
                    mContext.startActivity(mIntent);
                }
                else if (mMode == 2){
                    Intent mIntent = new Intent(mContext, VideoPlayerActivity.class);
                    mIntent.putExtra("equipath",mFilePathList[position]);
                    mContext.startActivity(mIntent);
                }
            }
        });
        return convertView;
    }
}
