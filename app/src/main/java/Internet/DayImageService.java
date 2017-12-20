package Internet;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

/**
 * Created by SEUXXD on 2017/12/20.
 */

public interface DayImageService {
    @GET("http://guolin.tech/api/bing_pic")
    Observable<ResponseBody> getDayImage();
}
