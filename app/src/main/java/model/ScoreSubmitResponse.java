package model;

/**
 * Created by SEUXXD on 2017/12/8.
 */

public class ScoreSubmitResponse {
    private boolean success;
    private String text;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getText() {
        return text;
    }
}
