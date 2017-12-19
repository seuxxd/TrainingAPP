package Constant;

import com.example.seuxxd.trainingapp.R;

/**
 * Created by SEUXXD on 2017/11/2.
 */

public class SourceConstant {

    private static SourceConstant sMSourceConstant;
    public SourceConstant() {

    }

    public static SourceConstant newInstance(){
        if (sMSourceConstant == null)
            return new SourceConstant();
        else
            return sMSourceConstant;
    }

    private String[] mMyInfoStringList = {"未签到","个人资料","意见与建议","退出登录"};
    private String[] mMyInfoStringList2 = {"已签到","个人资料","意见与建议","退出登录"};

    private int[] mMyInfoImageList = {
            R.drawable.suggestion,
            R.drawable.info,
            R.drawable.message,
            R.drawable.setting};
    private int[] mMyInfoImageList2 = {
            R.drawable.sign_ok_logo,
            R.drawable.info,
            R.drawable.message,
            R.drawable.setting};

    private int[] mQuestionNumber = {
            R.drawable.choice_1,
            R.drawable.choice_2,
            R.drawable.choice_3,
            R.drawable.choice_4,
            R.drawable.choice_5};

    public int[] getQuestionNumber() {
        return mQuestionNumber;
    }

    public static SourceConstant getMSourceConstant() {
        return sMSourceConstant;
    }

    public int[] getMyInfoImageList2() {
        return mMyInfoImageList2;
    }

    public void setMyInfoImageList2(int[] myInfoImageList2) {
        mMyInfoImageList2 = myInfoImageList2;
    }

    public String[] getMyInfoStringList() {
        return mMyInfoStringList;
    }

    public int[] getMyInfoImageList() {
        return mMyInfoImageList;
    }

    public String[] getMyInfoStringList2() {
        return mMyInfoStringList2;
    }
}
