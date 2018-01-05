package Internet;

import io.reactivex.Observable;
import model.suggestion.SuggestionResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SEUXXD on 2017/12/9.
 */

public interface SuggestionService {
    @GET("AppOpinSug.action")
    Observable<SuggestionResponse> submitSuggestion(@Query("title") String title,
                                                    @Query("content") String content,
                                                    @Query("username") String username,
                                                    @Query("subtime") String subtime);
}
