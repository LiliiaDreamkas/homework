package lesson5;

import lesson5.api.ProductService;
import lesson5.model.Product;
import lesson5.utils.RetrofitUtils;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

import static com.github.javafaker.Faker.instance;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateProductTest extends AbstractTest {
    static ProductService productService;

    private int productId;

    @BeforeAll
    static void beforeAll() {
        productService =
                RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @Test
    void createProduct() throws IOException {
        //given
        Product product = Product.builder().title(instance().food().ingredient()).price((int) (Math.random() * 10000)).categoryTitle("Food").build();

        //when
        productId = Objects.requireNonNull(productService.createProduct(product).execute().body()).getId();

        //then
        assertThat(productId, not(nullValue()));
        compareProducts(productId, product);
    }

    @Test
    void createProductWithoutNameAndPrice() throws IOException {
        //given
        Product product = Product.builder().categoryTitle("Food").build();

        //when
        productId = Objects.requireNonNull(productService.createProduct(product).execute().body()).getId();

        //then
        assertThat(productId, not(nullValue()));
        product.setPrice(0);
        compareProducts(productId, product);
    }

    @Test
    void tryToCreateProductWithId() throws IOException {
        //given
        Product product = new Product(1, "Test product created with id", 10, "Food");

        //when
        Response<ResponseBody> response = productService.createIncorrectProduct(product).execute();

        //then
        assertThat(response.isSuccessful(), is(false));
        assertThat(getErrorBody(response).getMessage(), is("Id must be null for new entity"));
        assertThat(getProductsByName(product.getTitle()).size(), is(0));
    }

    @Test
    void tryToCreateProductWithoutCategory() throws IOException {
        //given
        Product product = Product.builder().title("Test product created without category").price(10).build();

        //when
        Response<ResponseBody> response = productService.createIncorrectProduct(product).execute();

        //then
        assertThat(response.isSuccessful(), is(false));
        assertThat(getProductsByName(product.getTitle()).size(), is(0));
    }

    @Test
    void tryToCreateProductWithIncorrectCategory() throws IOException {
        Product product = Product.builder().title("Test product created with incorrect category").price(10).categoryTitle("Category").build();

        Response<ResponseBody> response = productService.createIncorrectProduct(product).execute();

        assertThat(response.isSuccessful(), is(false));
        assertThat(getProductsByName(product.getTitle()).size(), is(0));
    }

    @AfterEach
    void tearDownData() throws IOException {
        if (productId != 0) {
            removeProductById(productId);
        }
    }
}
