package Internet;

import io.reactivex.Observable;
import model.IndexVideo;
import retrofit2.http.GET;

/**
 * Created by SEUXXD on 2017/12/20.
 */

public interface GetVideoService {
    @GET("AppGetVideos.action")
    Observable<IndexVideo> getPdfVideo();
}
