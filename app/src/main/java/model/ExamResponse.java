package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/11/29.
 */

public class ExamResponse implements Parcelable {
    private int results;
    private QuestionData[] rows;

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public QuestionData[] getRows() {
        return rows;
    }

    public void setRows(QuestionData[] rows) {
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

    public ExamResponse() {
    }

    protected ExamResponse(Parcel in) {
        this.results = in.readInt();
        this.rows = in.createTypedArray(QuestionData.CREATOR);
    }

    public static final Parcelable.Creator<ExamResponse> CREATOR = new Parcelable.Creator<ExamResponse>() {
        @Override
        public ExamResponse createFromParcel(Parcel source) {
            return new ExamResponse(source);
        }

        @Override
        public ExamResponse[] newArray(int size) {
            return new ExamResponse[size];
        }
    };
}
