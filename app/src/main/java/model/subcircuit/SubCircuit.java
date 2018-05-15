package model.subcircuit;

/**
 * Created by SEUXXD on 2018/5/15.
 */

public class SubCircuit {
    private String contactname;
    private String fax;
    private String note;
    private String phone;
    private int subscircuitid;
    private String subcircuitname;
    private int switchid;

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

    public int getSubscircuitid() {
        return subscircuitid;
    }

    public void setSubscircuitid(int subscircuitid) {
        this.subscircuitid = subscircuitid;
    }

    public String getSubcircuitname() {
        return subcircuitname;
    }

    public void setSubcircuitname(String subcircuitname) {
        this.subcircuitname = subcircuitname;
    }

    public int getSwitchid() {
        return switchid;
    }

    public void setSwitchid(int switchid) {
        this.switchid = switchid;
    }
}
