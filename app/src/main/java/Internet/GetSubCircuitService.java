package Internet;

import io.reactivex.Observable;
import model.subcircuit.SubCircuits;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2018/5/15.
 */

public interface GetSubCircuitService {
    @GET("GetSubsCircuit.action")
    Observable<SubCircuits> getContent(@Query("switchid") int id);
}
