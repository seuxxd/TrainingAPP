package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.seuxxd.trainingapp.R;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.EquipmentData;

/**
 * Created by SEUXXD on 2017/12/12.
 */

public class EquipmentAdapter extends BaseAdapter {
    private Context mContext;
    private List<EquipmentData> mDataList;

    public EquipmentAdapter(Context context, List<EquipmentData> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    static class ViewHolder{
        @BindView(R.id.equip_id)
        TextView mIdText;
        @BindView(R.id.equip_name)
        TextView mNameText;
        @BindView(R.id.equip_model)
        TextView mModelText;
        @BindView(R.id.equip_function)
        TextView mFunctionText;
        @BindView(R.id.equip_maker)
        TextView mMakerText;
        @BindString(R.string.equip_id)
        String mStringId;
        @BindString(R.string.equip_name)
        String mStringName;
        @BindString(R.string.equip_model)
        String mStringModel;
        @BindString(R.string.equip_function)
        String mStringFunction;
        @BindString(R.string.equip_maker)
        String mStringMaker;


        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_equip_info_list,parent,false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        }
        else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mIdText.setText(mHolder.mStringId + mDataList.get(position).getCabinetID());
        mHolder.mNameText.setText(mHolder.mStringName + mDataList.get(position).getEquipmentName());
        mHolder.mModelText.setText(mHolder.mStringModel + mDataList.get(position).getEquipmodel());
        mHolder.mFunctionText.setText(mHolder.mStringFunction + mDataList.get(position).getFunction());
        mHolder.mMakerText.setText(mHolder.mStringMaker + mDataList.get(position).getManufinfor());
        return convertView;
    }
}
