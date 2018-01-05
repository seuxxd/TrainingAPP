package Internet;

import io.reactivex.Observable;
import model.equipment.EquipVideoResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2017/12/12.
 */

public interface EquipVideoService {
    @GET("AppDevsMovie.action")
    Observable<EquipVideoResponse> getVideoInfo(@Query("unitid") String unitid);
}
