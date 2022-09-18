package lesson5.api;

import lesson5.model.Product;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ProductService {
    @GET("products")
    Call<List<Product>> getProducts();

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") int id);

    @GET("products/{id}")
    Call<ResponseBody> getNonExistedProduct(@Path("id") int id);

    @POST("products")
    Call<Product> createProduct(@Body Product product);

    @POST("products")
    Call<ResponseBody> createIncorrectProduct(@Body Product product);

    @PUT("products")
    Call<Product> modifyProduct(@Body Product product);

    @PUT("products")
    Call<ResponseBody> modifyProductWithIncorrectData(@Body Product product);

    @DELETE("products/{id}")
    Call<ResponseBody> removeProduct(@Path("id") int id);
}
