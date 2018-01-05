package model.index;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/12/29.
 */

public class IndexError implements Parcelable {
    private int results;
    private IndexErrorDetails[] pdf;
    private IndexErrorDetails[] wmv;

    public IndexErrorDetails[] getPdf() {
        return pdf;
    }

    public void setPdf(IndexErrorDetails[] pdf) {
        this.pdf = pdf;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public IndexErrorDetails[] getWmv() {
        return wmv;
    }

    public void setWmv(IndexErrorDetails[] wmv) {
        this.wmv = wmv;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.results);
        dest.writeTypedArray(this.pdf, flags);
        dest.writeTypedArray(this.wmv, flags);
    }

    public IndexError() {
    }

    protected IndexError(Parcel in) {
        this.results = in.readInt();
        this.pdf = in.createTypedArray(IndexErrorDetails.CREATOR);
        this.wmv = in.createTypedArray(IndexErrorDetails.CREATOR);
    }

    public static final Parcelable.Creator<IndexError> CREATOR = new Parcelable.Creator<IndexError>() {
        @Override
        public IndexError createFromParcel(Parcel source) {
            return new IndexError(source);
        }

        @Override
        public IndexError[] newArray(int size) {
            return new IndexError[size];
        }
    };
}
