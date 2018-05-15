package model.vswitch;

/**
 * Created by SEUXXD on 2018/5/15.
 */

public class VSwitch {
    private String contactname;
    private String fax;
    private String note;
    private String phone;
    private int switchid;
    private String switchname;
    private int voltagegradeid;

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

    public int getSwitchid() {
        return switchid;
    }

    public void setSwitchid(int switchid) {
        this.switchid = switchid;
    }

    public String getSwitchname() {
        return switchname;
    }

    public void setSwitchname(String switchname) {
        this.switchname = switchname;
    }

    public int getVoltagegradeid() {
        return voltagegradeid;
    }

    public void setVoltagegradeid(int voltagegradeid) {
        this.voltagegradeid = voltagegradeid;
    }
}
