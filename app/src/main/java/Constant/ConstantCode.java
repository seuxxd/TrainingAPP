package Constant;

import java.util.Calendar;

/**
 * Created by SEUXXD on 2017/11/8.
 */

public class ConstantCode {
    //    打开扫描二维码Activity的请求参数
    public static final int SCAN_REQUEST_CODE     = 1;
    //    扫描二维码成功的结果参数
    public static final int SCAN_RESULT_OK        = -1;
    //    扫描二维码失败的结果参数
    public static final int SCAN_RESULT_FAILED    = -2;


    //   首页四个界面的对应种类
    public static final int INDEX_RULES_TYPE = 11;
    public static final int INDEX_PDF_TYPE = 12;
    public static final int INDEX_VIDEO_TYPE = 13;
    public static final int INDEX_ERROR_TYPE = 14;





    public static String getFormatTime(){
        Calendar mCalendar = Calendar.getInstance();

        String mMonth = String.valueOf(mCalendar.get(Calendar.MONTH) + 1);
        String mDay = String.valueOf(mCalendar.get(Calendar.DAY_OF_MONTH));
        if (mCalendar.get(Calendar.MONTH) + 1 < 10){
            mMonth = "0" + (mCalendar.get(Calendar.MONTH) + 1);
        }
        if (mCalendar.get(Calendar.DAY_OF_MONTH) < 10){
            mDay = "0" + mCalendar.get(Calendar.DAY_OF_MONTH);
        }

        return mCalendar.get(Calendar.YEAR)
                + "-"
                + mMonth
                + "-"
                + mDay;
    }
}
