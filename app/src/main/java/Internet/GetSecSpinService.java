package Internet;

import io.reactivex.Observable;
import model.substation.SubStation;
import model.substation.Substations;
import retrofit2.http.GET;

/**
 * Created by SEUXXD on 2018/5/15.
 */

public interface GetSecSpinService {
    @GET("GetSubstation.action")
    Observable<Substations> getContent();
}
