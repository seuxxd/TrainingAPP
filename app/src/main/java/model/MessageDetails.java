package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/11/28.
 */

public class MessageDetails implements Parcelable {
    private String contentmes;
    private String inspeteam;
    private String loginname;
    private String note1;
    private String profile;
    private String pushtime;
    private String subnittime;
    private String trainMessageid;
    private String traintime;
    private String filePath;
    private int    userid;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContentmes() {
        return contentmes;
    }

    public void setContentmes(String contentmes) {
        this.contentmes = contentmes;
    }

    public String getInspeteam() {
        return inspeteam;
    }

    public void setInspeteam(String inspeteam) {
        this.inspeteam = inspeteam;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPushtime() {
        return pushtime;
    }

    public void setPushtime(String pushtime) {
        this.pushtime = pushtime;
    }

    public String getSubnittime() {
        return subnittime;
    }

    public void setSubnittime(String subnittime) {
        this.subnittime = subnittime;
    }

    public String getTrainMessageid() {
        return trainMessageid;
    }

    public void setTrainMessageid(String trainMessageid) {
        this.trainMessageid = trainMessageid;
    }

    public String getTraintime() {
        return traintime;
    }

    public void setTraintime(String traintime) {
        this.traintime = traintime;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public MessageDetails() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.contentmes);
        dest.writeString(this.inspeteam);
        dest.writeString(this.loginname);
        dest.writeString(this.note1);
        dest.writeString(this.profile);
        dest.writeString(this.pushtime);
        dest.writeString(this.subnittime);
        dest.writeString(this.trainMessageid);
        dest.writeString(this.traintime);
        dest.writeString(this.filePath);
        dest.writeInt(this.userid);
    }

    protected MessageDetails(Parcel in) {
        this.contentmes = in.readString();
        this.inspeteam = in.readString();
        this.loginname = in.readString();
        this.note1 = in.readString();
        this.profile = in.readString();
        this.pushtime = in.readString();
        this.subnittime = in.readString();
        this.trainMessageid = in.readString();
        this.traintime = in.readString();
        this.filePath = in.readString();
        this.userid = in.readInt();
    }

    public static final Creator<MessageDetails> CREATOR = new Creator<MessageDetails>() {
        @Override
        public MessageDetails createFromParcel(Parcel source) {
            return new MessageDetails(source);
        }

        @Override
        public MessageDetails[] newArray(int size) {
            return new MessageDetails[size];
        }
    };
}
