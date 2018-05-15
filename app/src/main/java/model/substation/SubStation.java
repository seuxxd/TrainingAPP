package model.substation;

/**
 * Created by SEUXXD on 2018/5/15.
 */

public class SubStation {
    private String contactName;
    private String fax;
    private int inspectionid;
    private String note;
    private String phone;
    private int substationid;
    private String substationname;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public int getInspectionid() {
        return inspectionid;
    }

    public void setInspectionid(int inspectionid) {
        this.inspectionid = inspectionid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSubstationid() {
        return substationid;
    }

    public void setSubstationid(int substationid) {
        this.substationid = substationid;
    }

    public String getSubstationname() {
        return substationname;
    }

    public void setSubstationname(String substationname) {
        this.substationname = substationname;
    }
}
