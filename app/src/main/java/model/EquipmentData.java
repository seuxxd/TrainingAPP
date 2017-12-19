package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEUXXD on 2017/11/27.
 */

public class EquipmentData implements Parcelable{
    private int cabinetID;
    private String dangerpoint;
    private String equipmentName;
    private String equipmodel;
    private String equipopera;
    private String function;
    private String instruction;
    private String knowledge;
    private String manufinfor;
    private String note1;
    private String schematic;
    private int unitid;
    private String whdefect;
    private String wiring;
//    private String[] pdfname;
//    private String[] messageid;
//    private String[] videoname;
//    private String[] videoPath;

//    public String[] getPdfname() {
//        return pdfname;
//    }
//
//    public void setPdfname(String[] pdfname) {
//        this.pdfname = pdfname;
//    }
//
//    public String[] getVideoname() {
//        return videoname;
//    }
//
//    public void setVideoname(String[] videoname) {
//        this.videoname = videoname;
//    }
//
//    public String[] getMessageid() {
//        return messageid;
//    }
//
//    public void setMessageid(String[] messageid) {
//        this.messageid = messageid;
//    }
//
//    public String[] getVideoPath() {
//        return videoPath;
//    }
//
//    public void setVideoPath(String[] videoPath) {
//        this.videoPath = videoPath;
//    }

    public int getCabinetID() {
        return cabinetID;
    }

    public void setCabinetID(int cabinetID) {
        this.cabinetID = cabinetID;
    }

    public String getDangerpoint() {
        return dangerpoint;
    }

    public void setDangerpoint(String dangerpoint) {
        this.dangerpoint = dangerpoint;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmodel() {
        return equipmodel;
    }

    public void setEquipmodel(String equipmodel) {
        this.equipmodel = equipmodel;
    }

    public String getEquipopera() {
        return equipopera;
    }

    public void setEquipopera(String equipopera) {
        this.equipopera = equipopera;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public String getManufinfor() {
        return manufinfor;
    }

    public void setManufinfor(String manufinfor) {
        this.manufinfor = manufinfor;
    }

    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public String getSchematic() {
        return schematic;
    }

    public void setSchematic(String schematic) {
        this.schematic = schematic;
    }

    public int getUnitid() {
        return unitid;
    }

    public void setUnitid(int unitid) {
        this.unitid = unitid;
    }

    public String getWhdefect() {
        return whdefect;
    }

    public void setWhdefect(String whdefect) {
        this.whdefect = whdefect;
    }

    public String getWiring() {
        return wiring;
    }

    public void setWiring(String wiring) {
        this.wiring = wiring;
    }

    public EquipmentData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cabinetID);
        dest.writeString(this.dangerpoint);
        dest.writeString(this.equipmentName);
        dest.writeString(this.equipmodel);
        dest.writeString(this.equipopera);
        dest.writeString(this.function);
        dest.writeString(this.instruction);
        dest.writeString(this.knowledge);
        dest.writeString(this.manufinfor);
        dest.writeString(this.note1);
        dest.writeString(this.schematic);
        dest.writeInt(this.unitid);
        dest.writeString(this.whdefect);
        dest.writeString(this.wiring);
//        dest.writeStringArray(this.pdfname);
//        dest.writeStringArray(this.messageid);
//        dest.writeStringArray(this.videoname);
//        dest.writeStringArray(this.videoPath);
    }

    protected EquipmentData(Parcel in) {
        this.cabinetID = in.readInt();
        this.dangerpoint = in.readString();
        this.equipmentName = in.readString();
        this.equipmodel = in.readString();
        this.equipopera = in.readString();
        this.function = in.readString();
        this.instruction = in.readString();
        this.knowledge = in.readString();
        this.manufinfor = in.readString();
        this.note1 = in.readString();
        this.schematic = in.readString();
        this.unitid = in.readInt();
        this.whdefect = in.readString();
        this.wiring = in.readString();
//        this.pdfname = in.createStringArray();
//        this.messageid = in.createStringArray();
//        this.videoname = in.createStringArray();
//        this.videoPath = in.createStringArray();
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
