package Internet;

import io.reactivex.Observable;
import model.voltagegrade.VoltageGrades;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2018/5/15.
 */

public interface GetVoltageService {
    @GET("GetVoltageGrade.action")
    Observable<VoltageGrades> getContent(@Query("substationid") int id);
}
