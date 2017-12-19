package Internet;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2017/11/29.
 */

public interface ForgetPasswordService {
    @GET("AppForgetUser.action")
    Observable<ResponseBody> forgetPwdAndResetPwd(@Query("username") String username,
                                                  @Query("mobileno") String mobileno,
                                                  @Query("password") String newPwd);
}
