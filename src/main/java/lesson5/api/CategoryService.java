package lesson5.api;

import lesson5.model.GetCategoryResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryService {
    @GET("categories/{id}")
    Call<GetCategoryResponse> getCategory(@Path("id") int id);

    @GET("categories/{id}")
    Call<ResponseBody> getIncorrectCategory(@Path("id") int id);
}
