package model.voltagegrade;

/**
 * Created by SEUXXD on 2018/5/15.
 */

public class VoltageGrade {
    private String contactname;
    private String fax;
    private String note;
    private String phone;
    private int substationid;
    private int voltagegradeid;
    private String voltagegradename;

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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

    public int getVoltagegradeid() {
        return voltagegradeid;
    }

    public void setVoltagegradeid(int voltagegradeid) {
        this.voltagegradeid = voltagegradeid;
    }

    public String getVoltagegradename() {
        return voltagegradename;
    }

    public void setVoltagegradename(String voltagegradename) {
        this.voltagegradename = voltagegradename;
    }
}
