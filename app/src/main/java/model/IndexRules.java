package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/12/29.
 */

public class IndexRules implements Parcelable {
    private int results;
    private IndexRulesDetails[] pdf;

    public IndexRulesDetails[] getPdf() {
        return pdf;
    }

    public void setPdf(IndexRulesDetails[] pdf) {
        this.pdf = pdf;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.results);
        dest.writeTypedArray(this.pdf, flags);
    }

    public IndexRules() {
    }

    protected IndexRules(Parcel in) {
        this.results = in.readInt();
        this.pdf = in.createTypedArray(IndexRulesDetails.CREATOR);
    }

    public static final Parcelable.Creator<IndexRules> CREATOR = new Parcelable.Creator<IndexRules>() {
        @Override
        public IndexRules createFromParcel(Parcel source) {
            return new IndexRules(source);
        }

        @Override
        public IndexRules[] newArray(int size) {
            return new IndexRules[size];
        }
    };
}
