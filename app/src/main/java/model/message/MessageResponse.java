package model.message;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/11/28.
 */

public class MessageResponse implements Parcelable {
    private int results;
    private MessageDetails[] rows;

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public MessageDetails[] getRows() {
        return rows;
    }

    public void setRows(MessageDetails[] rows) {
        this.rows = rows;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.results);
        dest.writeTypedArray(this.rows, flags);
    }

    public MessageResponse() {
    }

    protected MessageResponse(Parcel in) {
        this.results = in.readInt();
        this.rows = in.createTypedArray(MessageDetails.CREATOR);
    }

    public static final Parcelable.Creator<MessageResponse> CREATOR = new Parcelable.Creator<MessageResponse>() {
        @Override
        public MessageResponse createFromParcel(Parcel source) {
            return new MessageResponse(source);
        }

        @Override
        public MessageResponse[] newArray(int size) {
            return new MessageResponse[size];
        }
    };
}
