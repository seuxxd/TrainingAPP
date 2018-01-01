package Internet;



import io.reactivex.Observable;
import model.IndexPdf;
import retrofit2.http.GET;

/**
 * Created by SEUXXD on 2017/12/20.
 */

public interface GetPPTService {
    @GET("AppGetDocus.action")
    Observable<IndexPdf> getPdf();
}
