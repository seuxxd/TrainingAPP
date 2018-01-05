package model.login;

/**
 * Created by SEUXXD on 2017/11/5.
 */
//没有使用m前缀是因为自动生成的json格式是按照类里面字段定义的
public class UserInfo {
    private String username;
    private String password;

    public UserInfo() {
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
}
