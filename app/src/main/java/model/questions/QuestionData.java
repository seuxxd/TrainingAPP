package model.questions;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/11/29.
 */

public class QuestionData implements Parcelable {
    private String answer;
    private String aoption;
    private String boption;
    private String coption;
    private String doption;
    private String note;
    private String questionbank;
    private String questionid;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAoption() {
        return aoption;
    }

    public void setAoption(String aoption) {
        this.aoption = aoption;
    }

    public String getBoption() {
        return boption;
    }

    public void setBoption(String boption) {
        this.boption = boption;
    }

    public String getCoption() {
        return coption;
    }

    public void setCoption(String coption) {
        this.coption = coption;
    }

    public String getDoption() {
        return doption;
    }

    public void setDoption(String doption) {
        this.doption = doption;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getQuestionbank() {
        return questionbank;
    }

    public void setQuestionbank(String questionbank) {
        this.questionbank = questionbank;
    }

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.answer);
        dest.writeString(this.aoption);
        dest.writeString(this.boption);
        dest.writeString(this.coption);
        dest.writeString(this.doption);
        dest.writeString(this.note);
        dest.writeString(this.questionbank);
        dest.writeString(this.questionid);
    }

    public QuestionData() {
    }

    protected QuestionData(Parcel in) {
        this.answer = in.readString();
        this.aoption = in.readString();
        this.boption = in.readString();
        this.coption = in.readString();
        this.doption = in.readString();
        this.note = in.readString();
        this.questionbank = in.readString();
        this.questionid = in.readString();
    }

    public static final Parcelable.Creator<QuestionData> CREATOR = new Parcelable.Creator<QuestionData>() {
        @Override
        public QuestionData createFromParcel(Parcel source) {
            return new QuestionData(source);
        }

        @Override
        public QuestionData[] newArray(int size) {
            return new QuestionData[size];
        }
    };
}
