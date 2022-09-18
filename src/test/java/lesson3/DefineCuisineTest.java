package lesson3;

import model.CuisineResponse;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefineCuisineTest extends AbstractTest {

    @Test
    public void onePossibleCuisine() {
        //given
        Map<String, String> params = new HashMap<>() {{
            put("language", "en");
        }};

        Map<String, String> formParams = new HashMap<>() {{
            put("title", "Hamburger");
            put("ingredientList", "Bun, cutlet, tomato, onion, cheese");
        }};

        //when
        CuisineResponse response = defineCuisine(params, formParams).then().extract().as(CuisineResponse.class);

        //then
        assertThat(response.getCuisine(), equalTo("American"));
        assertThat(response.getCuisines(), hasItem("American"));
        assertThat(response.getCuisines().size(), is(1));
        assertThat(response.getConfidence() > 0, is(true));
    }

    @Test
    public void severalPossibleCuisine() {
        //given
        Map<String, String> params = new HashMap<>() {{
            put("language", "en");
        }};

        Map<String, String> formParams = new HashMap<>() {{
            put("title", "Lasagne");
            put("ingredientList", "Minced meat, tomatoes, onion, carrot, basil, milk");
        }};

        //when
        CuisineResponse response = defineCuisine(params, formParams).then().extract().as(CuisineResponse.class);

        //then
        assertThat(response.getCuisine(), equalTo("Mediterranean"));
        assertThat(response.getCuisines(), hasItem("Mediterranean"));
        assertThat(response.getCuisines(), hasItem("Italian"));
        assertThat(response.getConfidence() > 0, is(true));
    }

    @Test
    public void germanLanguage() {
        //given
        Map<String, String> params = new HashMap<>() {{
            put("language", "de");
        }};

        Map<String, String> formParams = new HashMap<>() {{
            put("title", "Schnitzel");
            put("ingredientList", "Schweinefleisch, Ei, Mehl, Semmelbrösel");
        }};

        //when
        CuisineResponse response = defineCuisine(params, formParams).then().extract().as(CuisineResponse.class);

        //then
        assertThat(response.getCuisines(), hasItem("German"));
        assertThat(response.getConfidence() > 0, is(true));
    }

    @Test
    public void complexName() {
        //given
        Map<String, String> params = new HashMap<>() {{
            put("language", "en");
        }};

        Map<String, String> formParams = new HashMap<>() {{
            put("title", "fish and chips");
            put("ingredientList", "Fish, potatoes");
        }};

        //when
        CuisineResponse response = defineCuisine(params, formParams).then().extract().as(CuisineResponse.class);

        //then
        assertThat(response.getCuisines(), hasItem("British"));
        assertThat(response.getCuisines(), hasItem("English"));
        assertThat(response.getConfidence() > 0, is(true));
    }

    @Test  //default response
    public void noTitle() {
        //given
        Map<String, String> params = new HashMap<>() {{
            put("language", "en");
        }};

        Map<String, String> formParams = new HashMap<>() {{
            put("ingredientList", "Fish, potatoes");
        }};

        //when
        CuisineResponse response = defineCuisine(params, formParams).then().extract().as(CuisineResponse.class);

        //then
        assertThat(response.getCuisine(), equalTo("Mediterranean"));
        assertThat(response.getCuisines(), hasItem("Mediterranean"));
        assertThat(response.getCuisines(), hasItem("Italian"));
        assertThat(response.getCuisines(), hasItem("European"));
        assertThat(response.getConfidence(), equalTo(0.0F));
    }
}
