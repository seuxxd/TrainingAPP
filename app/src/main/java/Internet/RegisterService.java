package Internet;

import io.reactivex.Observable;
import model.register.RegisterInfo;
import model.register.RegisterResponse;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2017/11/9.
 */

public interface RegisterService {
//    注册的URL
    @POST("")
    Observable<String> doRegister(@Body RegisterInfo mRegisterInfo);

//    注册的方法，使用Retrofit2和RxJava2实现，参数分别为
    /**
     *  @param username  Register name for user
     *  @param password  Password for user
     *  @param phonenumber  basic information for user
     *  @return the response of Server
    * */
    @GET("AppRegistUser.action")
    Observable<RegisterResponse> doRegister(@Query("username") String username,
                                            @Query("password") String password,
                                            @Query("mobileno") String phonenumber,
                                            @Query("department") String department);
}
