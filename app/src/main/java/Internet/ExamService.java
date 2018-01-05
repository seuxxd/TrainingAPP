package Internet;

import io.reactivex.Observable;
import model.questions.ExamResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2017/11/28.
 */

public interface ExamService {
    @GET("AppGetQuestion.action")
    Observable<ExamResponse> getExam(@Query("database") String database,
                                     @Query("difficulty") String difficulty);
}
