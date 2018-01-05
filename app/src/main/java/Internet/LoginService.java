package Internet;

//import io.reactivex.Observable;
import io.reactivex.Observable;
import model.login.LoginResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2017/11/5.
 */

public interface LoginService {

    //登陆
    @GET("AppLoginUser.action")
    Observable<LoginResponse> doLogin(@Query("username") String username,
                                      @Query("password")  String password);

//    @GET("AppLoginUser.action")
//    Observable<ResponseBody> doLogin(@Query("username") String username,
//                                     @Query("password")  String password);

}
