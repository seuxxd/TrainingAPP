package model.equipment;

import android.os.Parcel;
import android.os.Parcelable;

import model.fileandvideo.VideoInfo;

/**
 * Created by SEUXXD on 2017/12/12.
 */

public class EquipVideoResponse implements Parcelable {
    private boolean success;
    private VideoInfo[] data;

    public VideoInfo[] getData() {
        return data;
    }

    public void setData(VideoInfo[] data) {
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

    public EquipVideoResponse() {
    }

    protected EquipVideoResponse(Parcel in) {
        this.success = in.readByte() != 0;
        this.data = in.createTypedArray(VideoInfo.CREATOR);
    }

    public static final Parcelable.Creator<EquipVideoResponse> CREATOR = new Parcelable.Creator<EquipVideoResponse>() {
        @Override
        public EquipVideoResponse createFromParcel(Parcel source) {
            return new EquipVideoResponse(source);
        }

        @Override
        public EquipVideoResponse[] newArray(int size) {
            return new EquipVideoResponse[size];
        }
    };
}
