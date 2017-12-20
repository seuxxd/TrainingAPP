package Internet;

import io.reactivex.Observable;
import model.ExamResponse;
import retrofit2.http.GET;

/**
 * Created by SEUXXD on 2017/12/20.
 */

public interface ExamSpecialService {
    @GET()
    Observable<ExamResponse> getSpecialExam();
}
