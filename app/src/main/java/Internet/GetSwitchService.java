package Internet;

import io.reactivex.Observable;
import model.vswitch.VSwitchs;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2018/5/15.
 */

public interface GetSwitchService {
    @GET("GetSwitch.action")
    Observable<VSwitchs> getContent(@Query("voltagegradeid") int id);
}
