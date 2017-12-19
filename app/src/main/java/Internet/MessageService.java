package Internet;

import io.reactivex.Observable;
import model.MessageResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2017/11/27.
 */

public interface MessageService {
    @GET("AppPushMessage.action")
    Observable<MessageResponse> getMessage(@Query("username") String username,
                                           @Query("traintime") String traintime);
}
