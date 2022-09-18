package lesson5;

import lesson5.api.CategoryService;
import lesson5.model.GetCategoryResponse;
import lesson5.utils.RetrofitUtils;
import lesson6.db.model.Categories;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CategoryTest extends AbstractTest{
    static CategoryService categoryService;
    static int categoryId = 1;

    @BeforeAll
    static void beforeAll() {
        categoryService =
                RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @Test
    void getCategoryByExistingIdTest() throws IOException {
        //given
        Categories expectedCategory = getCategoryById(categoryId);

        //when
        Response<GetCategoryResponse> response = categoryService.getCategory(categoryId).execute();

        //then
        assertThat(response.isSuccessful(), is(true));
        assertThat(response.body().getId(), equalTo(categoryId));
        assertThat(response.body().getTitle(), equalTo(expectedCategory.getTitle()));
        response.body().getProducts().forEach(product ->
                assertThat(product.getCategoryTitle(), equalTo(expectedCategory.getTitle())));
    }

    @Test
    void tryToGetNonExistingCategory() throws IOException {
        Response<ResponseBody> response = categoryService.getIncorrectCategory(10000).execute();


        assertThat(response.isSuccessful(), is(false));
        assertThat(getErrorBody(response).getMessage(), is("Unable to find category with id: 10000"));
    }
}
