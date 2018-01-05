package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.seuxxd.trainingapp.R;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.equipment.EquipmentData;

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
        @BindView(R.id.equip_type)
        TextView mTypeText;
        @BindView(R.id.equip_voltage)
        TextView mVoltageText;
        @BindView(R.id.equip_name)
        TextView mNameText;
        @BindView(R.id.equip_maker)
        TextView mMakerText;
        @BindView(R.id.equip_brand_type)
        TextView mBrandText;
        @BindString(R.string.equip_type)
        String mStringType;
        @BindString(R.string.equip_voltage)
        String mStringVoltage;
        @BindString(R.string.equip_brand)
        String mStringBrand;
        @BindString(R.string.equip_name)
        String mStringName;
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
        mHolder.mTypeText.setText(mHolder.mStringType + mDataList.get(position).getEquipmenttype());
        mHolder.mVoltageText.setText(mHolder.mStringVoltage + mDataList.get(position).getVoltagegrade());
        mHolder.mNameText.setText(mHolder.mStringName + mDataList.get(position).getEquipname());
        mHolder.mBrandText.setText(mHolder.mStringBrand + mDataList.get(position).getNamemodel());
        mHolder.mMakerText.setText(mHolder.mStringMaker + mDataList.get(position).getNamemanufa());
        return convertView;
    }
}
