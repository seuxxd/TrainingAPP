package model.news;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/12/8.
 */

public class NewsResponse implements Parcelable{
    private String results;
    private NewsDetail[] rows;

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public NewsDetail[] getRows() {
        return rows;
    }

    public void setRows(NewsDetail[] rows) {
        this.rows = rows;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.results);
        dest.writeTypedArray(this.rows, flags);
    }

    public NewsResponse() {
    }

    protected NewsResponse(Parcel in) {
        this.results = in.readString();
        this.rows = in.createTypedArray(NewsDetail.CREATOR);
    }

    public static final Creator<NewsResponse> CREATOR = new Creator<NewsResponse>() {
        @Override
        public NewsResponse createFromParcel(Parcel source) {
            return new NewsResponse(source);
        }

        @Override
        public NewsResponse[] newArray(int size) {
            return new NewsResponse[size];
        }
    };
}
