package Internet;

import io.reactivex.Observable;
import model.sign.SignResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2017/11/26.
 */

public interface SignService {
    @GET("AppSignRec.action")
    Observable<SignResponse> doSign(@Query("username") String username,
                                    @Query("subtime") String subtime);
}
