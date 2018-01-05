package Internet;

import io.reactivex.Observable;
import model.equipment.EquipmentResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2017/11/27.
 */

public interface EquipmentService {
    @GET("AppDevsMessage.action")
    Observable<EquipmentResponse> getEquipmentInfo(@Query("unitid") String unitid);
}
