package model;

/**
 * Created by SEUXXD on 2017/11/7.
 */

//没有使用m前缀是因为自动生成的json格式是按照类里面字段定义的
public class RegisterInfo {
    private String username;
    private String password;
    private int phone;
    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
