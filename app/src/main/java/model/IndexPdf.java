package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/12/29.
 */

public class IndexPdf implements Parcelable {
    private int results;
    private IndexPdfDetails[] pdf;
    private IndexVideoDetails[] wmv;

    public IndexPdfDetails[] getPdf() {
        return pdf;
    }

    public void setPdf(IndexPdfDetails[] pdf) {
        this.pdf = pdf;
    }

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
        dest.writeTypedArray(this.pdf, flags);
        dest.writeTypedArray(this.wmv, flags);
    }

    public IndexPdf() {
    }

    protected IndexPdf(Parcel in) {
        this.results = in.readInt();
        this.pdf = in.createTypedArray(IndexPdfDetails.CREATOR);
        this.wmv = in.createTypedArray(IndexVideoDetails.CREATOR);
    }

    public static final Creator<IndexPdf> CREATOR = new Creator<IndexPdf>() {
        @Override
        public IndexPdf createFromParcel(Parcel source) {
            return new IndexPdf(source);
        }

        @Override
        public IndexPdf[] newArray(int size) {
            return new IndexPdf[size];
        }
    };
}
