package Internet;

import io.reactivex.Observable;
import model.questions.ScoreSubmitResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2017/11/29.
 */

public interface ExamResultService {

    @GET("AppAddTraingrade.action")
    Observable<ScoreSubmitResponse> uploadResult(@Query("username") String username,
                                                 @Query("traingrade") String traingrade,
                                                 @Query("subtime") String subtime,
                                                 @Query("database") String database,
                                                 @Query("difficulty") String difficulty);
}
