package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/12/29.
 */

public class IndexVideoDetails implements Parcelable{
    private String note;
    private String subtime;
    private int videocourid;
    private String videocourname;
    private String videocourpath;
    private String videocourtype;

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

    public int getVideocourid() {
        return videocourid;
    }

    public void setVideocourid(int videocourid) {
        this.videocourid = videocourid;
    }

    public String getVideocourname() {
        return videocourname;
    }

    public void setVideocourname(String videocourname) {
        this.videocourname = videocourname;
    }

    public String getVideocourpath() {
        return videocourpath;
    }

    public void setVideocourpath(String videocourpath) {
        this.videocourpath = videocourpath;
    }

    public String getVideocourtype() {
        return videocourtype;
    }

    public void setVideocourtype(String videocourtype) {
        this.videocourtype = videocourtype;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.note);
        dest.writeString(this.subtime);
        dest.writeInt(this.videocourid);
        dest.writeString(this.videocourname);
        dest.writeString(this.videocourpath);
        dest.writeString(this.videocourtype);
    }

    public IndexVideoDetails() {
    }

    protected IndexVideoDetails(Parcel in) {
        this.note = in.readString();
        this.subtime = in.readString();
        this.videocourid = in.readInt();
        this.videocourname = in.readString();
        this.videocourpath = in.readString();
        this.videocourtype = in.readString();
    }

    public static final Creator<IndexVideoDetails> CREATOR = new Creator<IndexVideoDetails>() {
        @Override
        public IndexVideoDetails createFromParcel(Parcel source) {
            return new IndexVideoDetails(source);
        }

        @Override
        public IndexVideoDetails[] newArray(int size) {
            return new IndexVideoDetails[size];
        }
    };
}
