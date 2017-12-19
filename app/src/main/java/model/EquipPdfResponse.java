package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/12/12.
 */

public class EquipPdfResponse implements Parcelable {
    private boolean success;
    private FileInfo[] data;

    public FileInfo[] getData() {
        return data;
    }

    public void setData(FileInfo[] data) {
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

    public EquipPdfResponse() {
    }

    protected EquipPdfResponse(Parcel in) {
        this.success = in.readByte() != 0;
        this.data = in.createTypedArray(FileInfo.CREATOR);
    }

    public static final Parcelable.Creator<EquipPdfResponse> CREATOR = new Parcelable.Creator<EquipPdfResponse>() {
        @Override
        public EquipPdfResponse createFromParcel(Parcel source) {
            return new EquipPdfResponse(source);
        }

        @Override
        public EquipPdfResponse[] newArray(int size) {
            return new EquipPdfResponse[size];
        }
    };
}
