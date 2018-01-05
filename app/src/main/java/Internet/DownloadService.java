package Internet;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by SEUXXD on 2017/11/26.
 */

public interface DownloadService {

//    "unitjson!showRainMesdpdf.action"   下载
//    @Streaming
    @GET("AppTrainPdf.action") //在线
    Observable<ResponseBody> doDownload(@Query("equipath") String equipath);

//    @Streaming
//    @GET("{filename}") //测试
//    Observable<ResponseBody> doTestPdf(@Path("filename") String filename);
}
