package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/12/8.
 */

public class NewsDetail implements Parcelable {
    private String note ;
    private String realchain ;
    private String realinforid ;
    private String realinforname ;
    private String realinforpic ;
    private String subtime ;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRealchain() {
        return realchain;
    }

    public void setRealchain(String realchain) {
        this.realchain = realchain;
    }

    public String getRealinforid() {
        return realinforid;
    }

    public void setRealinforid(String realinforid) {
        this.realinforid = realinforid;
    }

    public String getRealinforname() {
        return realinforname;
    }

    public void setRealinforname(String realinforname) {
        this.realinforname = realinforname;
    }

    public String getRealinforpic() {
        return realinforpic;
    }

    public void setRealinforpic(String realinforpic) {
        this.realinforpic = realinforpic;
    }

    public String getSubtime() {
        return subtime;
    }

    public void setSubtime(String subtime) {
        this.subtime = subtime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.note);
        dest.writeString(this.realchain);
        dest.writeString(this.realinforid);
        dest.writeString(this.realinforname);
        dest.writeString(this.realinforpic);
        dest.writeString(this.subtime);
    }

    public NewsDetail() {
    }

    protected NewsDetail(Parcel in) {
        this.note = in.readString();
        this.realchain = in.readString();
        this.realinforid = in.readString();
        this.realinforname = in.readString();
        this.realinforpic = in.readString();
        this.subtime = in.readString();
    }

    public static final Creator<NewsDetail> CREATOR = new Creator<NewsDetail>() {
        @Override
        public NewsDetail createFromParcel(Parcel source) {
            return new NewsDetail(source);
        }

        @Override
        public NewsDetail[] newArray(int size) {
            return new NewsDetail[size];
        }
    };
}
