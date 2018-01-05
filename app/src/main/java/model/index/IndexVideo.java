package model.index;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/12/30.
 */

public class IndexVideo implements Parcelable {
    private int results;
    private IndexVideoDetails[] wmv;

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public IndexVideoDetails[] getWmv() {
        return wmv;
    }

    public void setWmv(IndexVideoDetails[] wmv) {
        this.wmv = wmv;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.results);
        dest.writeTypedArray(this.wmv, flags);
    }

    public IndexVideo() {
    }

    protected IndexVideo(Parcel in) {
        this.results = in.readInt();
        this.wmv = in.createTypedArray(IndexVideoDetails.CREATOR);
    }

    public static final Parcelable.Creator<IndexVideo> CREATOR = new Parcelable.Creator<IndexVideo>() {
        @Override
        public IndexVideo createFromParcel(Parcel source) {
            return new IndexVideo(source);
        }

        @Override
        public IndexVideo[] newArray(int size) {
            return new IndexVideo[size];
        }
    };
}
