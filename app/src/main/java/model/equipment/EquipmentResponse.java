package model.equipment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/11/27.
 */

public class EquipmentResponse implements Parcelable{
    private boolean success;
    private EquipmentData[] data;

    public EquipmentData[] getData() {
        return data;
    }

    public void setData(EquipmentData[] data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeTypedArray(this.data, flags);
    }

    public EquipmentResponse() {
    }

    protected EquipmentResponse(Parcel in) {
        this.success = in.readByte() != 0;
        this.data = in.createTypedArray(EquipmentData.CREATOR);
    }

    public static final Creator<EquipmentResponse> CREATOR = new Creator<EquipmentResponse>() {
        @Override
        public EquipmentResponse createFromParcel(Parcel source) {
            return new EquipmentResponse(source);
        }

        @Override
        public EquipmentResponse[] newArray(int size) {
            return new EquipmentResponse[size];
        }
    };
}
