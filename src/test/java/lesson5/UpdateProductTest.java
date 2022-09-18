package lesson5;

import lesson5.api.ProductService;
import lesson5.model.Product;
import lesson5.utils.RetrofitUtils;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UpdateProductTest extends AbstractTest {
    static ProductService productService;

    private int productId;
    private Product initialProduct;

    @BeforeAll
    static void beforeAll() {
        productService =
                RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @BeforeEach
    void setUpData() throws IOException {
        initialProduct = Product.builder().title("Product for update").price(25).categoryTitle("Food").build();

        Response<Product> response = productService.createProduct(initialProduct).execute();

        assert response.body() != null;
        productId = response.body().getId();
    }

    @Test
    void fullProductUpdate() throws IOException {
        Product updatedProduct = Product.builder().id(productId).title("Test gadget").price((int) (Math.random() * 10000)).categoryTitle("Electronic").build();;

        productService.modifyProduct(updatedProduct).execute();

        compareProducts(productId, updatedProduct);
    }

    @Test
    void updateProductWithoutNameAndPrice() throws IOException {
        Product updatedProduct = Product.builder().id(productId).categoryTitle("Electronic").build();

        productService.modifyProduct(updatedProduct).execute();

        updatedProduct.setPrice(0);
        compareProducts(productId, updatedProduct);
    }

    @Test
    void tryToUpdateProductWithoutId() throws IOException {
        Product updatedProduct = Product.builder().title("Updated product").price(10).categoryTitle("Electronic").build();

        Response<ResponseBody> response = productService.modifyProductWithIncorrectData(updatedProduct).execute();

        assertThat(response.isSuccessful(), is(false));
        assertThat(getErrorBody(response).getMessage(), is("Id must be not null for new entity"));
        compareProducts(productId, initialProduct);
    }

    @Test
    void tryToCreateProductWithoutCategory() throws IOException {
        Product updatedProduct = Product.builder().id(productId).title("Test product").price(10).build();

        Response<ResponseBody> response = productService.modifyProductWithIncorrectData(updatedProduct).execute();

        assertThat(response.isSuccessful(), is(false));
        compareProducts(productId, initialProduct);
    }

    @Test
    void tryToCreateProductWithIncorrectCategory() throws IOException {
        Product updatedProduct = Product.builder().id(productId).title("Test product").price(10).categoryTitle("Category").build();

        Response<ResponseBody> response = productService.createIncorrectProduct(updatedProduct).execute();

        assertThat(response.isSuccessful(), is(false));
        compareProducts(productId, initialProduct);
    }

    @AfterEach
    void tearDownData() throws IOException {
        if (productId != 0) {
            removeProductById(productId);
        }
    }
}
