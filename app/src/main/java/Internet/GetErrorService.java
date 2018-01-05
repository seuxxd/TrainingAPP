package Internet;

import io.reactivex.Observable;
import model.index.IndexError;
import retrofit2.http.GET;

/**
 * Created by SEUXXD on 2017/12/20.
 */

public interface GetErrorService {
    @GET("AppGetTypics.action")
    Observable<IndexError> getError();
}
