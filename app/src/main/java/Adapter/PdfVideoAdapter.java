package Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seuxxd.trainingapp.PDFActivity;
import com.example.seuxxd.trainingapp.R;
import com.example.seuxxd.trainingapp.VideoPlayerActivity;

import java.util.List;

import Constant.ConstantCode;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SEUXXD on 2017/12/10.
 */

public class PdfVideoAdapter extends BaseAdapter {


    private static final String TAG = "PdfVideoAdapter";

    private Context mContext;
    private String[] mNameList;
    private String[] mFilePathList;
    private int mMode;
    //ConstantCode.INDEX_PDF_TYPE
    //ConstantCode.INDEX_VIDEO_TYPE

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
                switch (mMode){
                    case ConstantCode.INDEX_PDF_TYPE:
                    case ConstantCode.INDEX_RULES_TYPE:
                        Intent mIntent1 = new Intent(mContext, PDFActivity.class);
                        mIntent1.putExtra("equipath",mFilePathList[position]);
                        mContext.startActivity(mIntent1);
                        break;
                    case ConstantCode.INDEX_VIDEO_TYPE:
                        Intent mIntent2 = new Intent(mContext, VideoPlayerActivity.class);
                        mIntent2.putExtra("equipath",mFilePathList[position]);
                        mContext.startActivity(mIntent2);
                        break;
                    case ConstantCode.INDEX_ERROR_TYPE:
                        //判断需要打开的activity类型
                        Log.i(TAG, "onClick: " + mFilePathList[position]);


                        char[] chars = mFilePathList[position].toCharArray();
                        if (chars[chars.length-1] == 'f'){
                            Intent mIntent3 = new Intent(mContext, PDFActivity.class);
                            mIntent3.putExtra("equipath",mFilePathList[position]);
                            mContext.startActivity(mIntent3);
                        }
                        else if (chars[chars.length-1] == '4' ||
                                 chars[chars.length-1] == 'v'){
                            Intent mIntent4 = new Intent(mContext, VideoPlayerActivity.class);
                            mIntent4.putExtra("equipath",mFilePathList[position]);
                            mContext.startActivity(mIntent4);
                        }
                        else {
                            Toast.makeText(mContext, "文件格式有误，请联系后台管理员", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        return convertView;
    }
}
