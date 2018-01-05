package model.equipment;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/11/27.
 */

public class EquipmentData implements Parcelable{

    private String commistime;
    private int equipmentid;
    private String equipmenttype;
    private String equipname;
    private String namemanufa;
    private String namemodel;
    private String note;
    private String specimodel;
    private int subscircuitid;
    private String voltagegrade;

    public String getCommistime() {
        return commistime;
    }

    public void setCommistime(String commistime) {
        this.commistime = commistime;
    }

    public int getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(int equipmentid) {
        this.equipmentid = equipmentid;
    }

    public String getEquipmenttype() {
        return equipmenttype;
    }

    public void setEquipmenttype(String equipmenttype) {
        this.equipmenttype = equipmenttype;
    }

    public String getEquipname() {
        return equipname;
    }

    public void setEquipname(String equipname) {
        this.equipname = equipname;
    }

    public String getNamemanufa() {
        return namemanufa;
    }

    public void setNamemanufa(String namemanufa) {
        this.namemanufa = namemanufa;
    }

    public String getNamemodel() {
        return namemodel;
    }

    public void setNamemodel(String namemodel) {
        this.namemodel = namemodel;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSpecimodel() {
        return specimodel;
    }

    public void setSpecimodel(String specimodel) {
        this.specimodel = specimodel;
    }

    public int getSubscircuitid() {
        return subscircuitid;
    }

    public void setSubscircuitid(int subscircuitid) {
        this.subscircuitid = subscircuitid;
    }

    public String getVoltagegrade() {
        return voltagegrade;
    }

    public void setVoltagegrade(String voltagegrade) {
        this.voltagegrade = voltagegrade;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.commistime);
        dest.writeInt(this.equipmentid);
        dest.writeString(this.equipmenttype);
        dest.writeString(this.equipname);
        dest.writeString(this.namemanufa);
        dest.writeString(this.namemodel);
        dest.writeString(this.note);
        dest.writeString(this.specimodel);
        dest.writeInt(this.subscircuitid);
        dest.writeString(this.voltagegrade);
    }

    public EquipmentData() {
    }

    protected EquipmentData(Parcel in) {
        this.commistime = in.readString();
        this.equipmentid = in.readInt();
        this.equipmenttype = in.readString();
        this.equipname = in.readString();
        this.namemanufa = in.readString();
        this.namemodel = in.readString();
        this.note = in.readString();
        this.specimodel = in.readString();
        this.subscircuitid = in.readInt();
        this.voltagegrade = in.readString();
    }

    public static final Creator<EquipmentData> CREATOR = new Creator<EquipmentData>() {
        @Override
        public EquipmentData createFromParcel(Parcel source) {
            return new EquipmentData(source);
        }

        @Override
        public EquipmentData[] newArray(int size) {
            return new EquipmentData[size];
        }
    };
}
