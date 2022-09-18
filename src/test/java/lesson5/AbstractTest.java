package lesson5;

import com.fasterxml.jackson.databind.ObjectMapper;
import lesson5.model.ErrorBody;
import lesson5.model.Product;
import lesson6.db.dao.CategoriesMapper;
import lesson6.db.dao.ProductsMapper;
import lesson6.db.model.Categories;
import lesson6.db.model.Products;
import lesson6.db.model.ProductsExample;
import okhttp3.ResponseBody;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import retrofit2.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class AbstractTest {
    private static final String resource = "mybatis-config.xml";

    static ErrorBody getErrorBody(Response<ResponseBody> response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        assert response.errorBody() != null;
        return mapper.readValue(response.errorBody().charStream(), ErrorBody.class);
    }

    static Categories getCategoryById(long id) throws IOException {
        CategoriesMapper categoriesMapper = getMapper(CategoriesMapper.class);
        return categoriesMapper.selectByPrimaryKey(id);
    }

    static Products getProductById(long id) throws IOException {
        ProductsMapper productsMapper = getMapper(ProductsMapper.class);
        return productsMapper.selectByPrimaryKey(id);
    }

    static List<Products> getProductsByName(String title) throws IOException {
        ProductsMapper productsMapper = getMapper(ProductsMapper.class);
        ProductsExample example = new ProductsExample();
        example.createCriteria().andTitleEqualTo(title);
        return productsMapper.selectByExample(example);
    }

    static void removeProductById(long id) throws IOException {
        SqlSession session = createSession();
        ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);
        productsMapper.deleteByPrimaryKey(id);
        session.commit();
    }

    private static SqlSession createSession() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new
                SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession();
    }

    private static <T> T getMapper(Class<T> clazz) throws IOException {
        SqlSession session = createSession();
        return session.getMapper(clazz);
    }

    void compareProducts(long productId, Product expectedProduct) throws IOException {
        Products createdProduct = getProductById(productId);
        Categories productCategory = getCategoryById(createdProduct.getCategory_id());

        assertThat(createdProduct.getTitle(), is(expectedProduct.getTitle()));
        assertThat(createdProduct.getPrice(), is(expectedProduct.getPrice()));
        assertThat(productCategory.getTitle(), is(expectedProduct.getCategoryTitle()));
    }

}
