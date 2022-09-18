package lesson5;

import lesson5.api.ProductService;
import lesson5.model.Product;
import lesson5.utils.RetrofitUtils;
import lesson6.db.model.Products;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteTest extends AbstractTest {
    static ProductService productService;

    @BeforeAll
    static void beforeAll() {
        productService =
                RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @Test
    void removeProduct() throws IOException {
        Product product = Product.builder().title("Product for update").price(25).categoryTitle("Food").build();
        int productId = productService.createProduct(product).execute().body().getId();

        Response<ResponseBody> response = productService.removeProduct(productId).execute();

        assertThat(response.isSuccessful(), is(true));
        Products deletedProduct = getProductById(productId);
        assertThat(deletedProduct, is(nullValue()));
    }

    @Test
    void tryToRemoveProductByIncorrectId() throws IOException {
        Response<ResponseBody> response = productService.removeProduct(0).execute();

        assertThat(response.isSuccessful(), is(false));
    }
}
