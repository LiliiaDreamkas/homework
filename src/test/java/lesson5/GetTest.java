package lesson5;

import lesson5.api.ProductService;
import lesson5.model.Product;
import lesson5.utils.RetrofitUtils;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetTest extends AbstractTest {
    static ProductService productService;

    private static Product existingProduct;

    @BeforeAll
    static void beforeAll() throws IOException {
        productService =
                RetrofitUtils.getRetrofit().create(ProductService.class);

        Product product = Product.builder().title("Test Product").price(25).categoryTitle("Food").build();
        existingProduct = productService.createProduct(product).execute().body();
    }

    @Test
    void getCreatedProduct() throws IOException {
        Product response = productService.getProduct(existingProduct.getId()).execute().body();

        assert response != null;
        assertThat(response.getId(), is(existingProduct.getId()));
        assertThat(response.getTitle(), is(existingProduct.getTitle()));
        assertThat(response.getPrice(), is(existingProduct.getPrice()));
        assertThat(response.getCategoryTitle(), is(existingProduct.getCategoryTitle()));
    }

    @Test
    void tryToGetIncorrectProduct() throws IOException {
        Response<ResponseBody> response = productService.getNonExistedProduct(0).execute();

        assertThat(response.isSuccessful(), is(false));
        assertThat(getErrorBody(response).getMessage(), is("Unable to find product with id: 0"));
    }

    @Test
    void getProductsContainsCreatedProduct() throws IOException {
        List<Product> products = productService.getProducts().execute().body();

        assertThat(products, hasItem(existingProduct));
    }

    @AfterAll
    static void tearDown() {
        productService.removeProduct(existingProduct.getId());
    }
}
