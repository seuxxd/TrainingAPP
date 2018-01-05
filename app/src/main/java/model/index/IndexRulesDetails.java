package model.index;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/12/29.
 */

public class IndexRulesDetails implements Parcelable {
    private String note;
    private int rulearticid;
    private String rulearticname;
    private String rulearticpath;
    private String ruleartictype;
    private String subtime;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getRulearticid() {
        return rulearticid;
    }

    public void setRulearticid(int rulearticid) {
        this.rulearticid = rulearticid;
    }

    public String getRulearticname() {
        return rulearticname;
    }

    public void setRulearticname(String rulearticname) {
        this.rulearticname = rulearticname;
    }

    public String getRulearticpath() {
        return rulearticpath;
    }

    public void setRulearticpath(String rulearticpath) {
        this.rulearticpath = rulearticpath;
    }

    public String getRuleartictype() {
        return ruleartictype;
    }

    public void setRuleartictype(String ruleartictype) {
        this.ruleartictype = ruleartictype;
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
        dest.writeInt(this.rulearticid);
        dest.writeString(this.rulearticname);
        dest.writeString(this.rulearticpath);
        dest.writeString(this.ruleartictype);
        dest.writeString(this.subtime);
    }

    public IndexRulesDetails() {
    }

    protected IndexRulesDetails(Parcel in) {
        this.note = in.readString();
        this.rulearticid = in.readInt();
        this.rulearticname = in.readString();
        this.rulearticpath = in.readString();
        this.ruleartictype = in.readString();
        this.subtime = in.readString();
    }

    public static final Parcelable.Creator<IndexRulesDetails> CREATOR = new Parcelable.Creator<IndexRulesDetails>() {
        @Override
        public IndexRulesDetails createFromParcel(Parcel source) {
            return new IndexRulesDetails(source);
        }

        @Override
        public IndexRulesDetails[] newArray(int size) {
            return new IndexRulesDetails[size];
        }
    };
}
