package model.register;

/**
 * Created by SEUXXD on 2017/11/9.
 */

//注册返回信息实体
public class RegisterResponse {
    private boolean success;
    private String text;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
