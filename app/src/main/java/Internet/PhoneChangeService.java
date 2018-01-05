package Internet;

import io.reactivex.Observable;
import model.changeinfo.ChangeNumberResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2017/11/29.
 */

public interface PhoneChangeService {
    @GET("AppUpdateMobile.action")
    Observable<ChangeNumberResponse> changePhone(@Query("username") String username,
                                                 @Query("mobileno") String mobileno);
}
