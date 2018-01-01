package Internet;

import io.reactivex.Observable;
import model.IndexRules;
import retrofit2.http.GET;

/**
 * Created by SEUXXD on 2017/12/29.
 */

public interface GetRulesService {
    @GET("AppGetRules.action")
    Observable<IndexRules> getRules();
}
