package model.index;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/12/29.
 */

public class IndexErrorDetails implements Parcelable {
    private String note;
    private String subtime;
    private String typicanalyid;
    private String typicanalyname;
    private String typicanalypath;
    private String typicanalytype;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSubtime() {
        return subtime;
    }

    public void setSubtime(String subtime) {
        this.subtime = subtime;
    }

    public String getTypicanalyid() {
        return typicanalyid;
    }

    public void setTypicanalyid(String typicanalyid) {
        this.typicanalyid = typicanalyid;
    }

    public String getTypicanalyname() {
        return typicanalyname;
    }

    public void setTypicanalyname(String typicanalyname) {
        this.typicanalyname = typicanalyname;
    }

    public String getTypicanalypath() {
        return typicanalypath;
    }

    public void setTypicanalypath(String typicanalypath) {
        this.typicanalypath = typicanalypath;
    }

    public String getTypicanalytype() {
        return typicanalytype;
    }

    public void setTypicanalytype(String typicanalytype) {
        this.typicanalytype = typicanalytype;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.note);
        dest.writeString(this.subtime);
        dest.writeString(this.typicanalyid);
        dest.writeString(this.typicanalyname);
        dest.writeString(this.typicanalypath);
        dest.writeString(this.typicanalytype);
    }

    public IndexErrorDetails() {
    }

    protected IndexErrorDetails(Parcel in) {
        this.note = in.readString();
        this.subtime = in.readString();
        this.typicanalyid = in.readString();
        this.typicanalyname = in.readString();
        this.typicanalypath = in.readString();
        this.typicanalytype = in.readString();
    }

    public static final Parcelable.Creator<IndexErrorDetails> CREATOR = new Parcelable.Creator<IndexErrorDetails>() {
        @Override
        public IndexErrorDetails createFromParcel(Parcel source) {
            return new IndexErrorDetails(source);
        }

        @Override
        public IndexErrorDetails[] newArray(int size) {
            return new IndexErrorDetails[size];
        }
    };
}
