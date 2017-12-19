package model;

/**
 * Created by SEUXXD on 2017/12/9.
 */

public class SuggestionResponse {
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
