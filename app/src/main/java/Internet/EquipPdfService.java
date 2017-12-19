package Internet;

import io.reactivex.Observable;
import model.EquipPdfResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2017/12/12.
 */

public interface EquipPdfService {
    @GET("AppDevsFile.action")
    Observable<EquipPdfResponse> getPdfInfo(@Query("unitid") String unitid);
}
