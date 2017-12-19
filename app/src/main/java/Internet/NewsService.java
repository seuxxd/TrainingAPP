package Internet;

import io.reactivex.Observable;
import model.NewsResponse;
import retrofit2.http.GET;

/**
 * Created by SEUXXD on 2017/12/8.
 */

public interface NewsService {
    @GET("AppGetRealInfor.action")
    Observable<NewsResponse> getNews();
}
