package model.index;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/12/29.
 */

public class IndexPdfDetails implements Parcelable{
    private String docucourid;
    private String docucourname;
    private String docucourpath;
    private String docucourtype;
    private String note;
    private String subtime;

    public String getDocucourid() {
        return docucourid;
    }

    public void setDocucourid(String docucourid) {
        this.docucourid = docucourid;
    }

    public String getDocucourname() {
        return docucourname;
    }

    public void setDocucourname(String docucourname) {
        this.docucourname = docucourname;
    }

    public String getDocucourpath() {
        return docucourpath;
    }

    public void setDocucourpath(String docucourpath) {
        this.docucourpath = docucourpath;
    }

    public String getDocucourtype() {
        return docucourtype;
    }

    public void setDocucourtype(String docucourtype) {
        this.docucourtype = docucourtype;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.docucourid);
        dest.writeString(this.docucourname);
        dest.writeString(this.docucourpath);
        dest.writeString(this.docucourtype);
        dest.writeString(this.note);
        dest.writeString(this.subtime);
    }

    public IndexPdfDetails() {
    }

    protected IndexPdfDetails(Parcel in) {
        this.docucourid = in.readString();
        this.docucourname = in.readString();
        this.docucourpath = in.readString();
        this.docucourtype = in.readString();
        this.note = in.readString();
        this.subtime = in.readString();
    }

    public static final Creator<IndexPdfDetails> CREATOR = new Creator<IndexPdfDetails>() {
        @Override
        public IndexPdfDetails createFromParcel(Parcel source) {
            return new IndexPdfDetails(source);
        }

        @Override
        public IndexPdfDetails[] newArray(int size) {
            return new IndexPdfDetails[size];
        }
    };
}
