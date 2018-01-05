package Internet;

import io.reactivex.Observable;
import model.questions.ExamResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2017/12/20.
 */

public interface ExamSpecialService {
    @GET("APPGetPushTrain.action")
    Observable<ExamResponse> getSpecialExam(@Query("username") String username,
                                            @Query("testtime") String testtime);
}
