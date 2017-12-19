package Internet;

import io.reactivex.Observable;
import model.ChangePasswordResponse;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2017/11/29.
 */

public interface PasswordChangeService {
    @GET("AppUpdateUser.action")
    Observable<ChangePasswordResponse> changePassword(@Query("username") String username,
                                                      @Query("password") String password);
}
