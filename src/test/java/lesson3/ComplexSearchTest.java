package lesson3;

import model.Recipe;
import model.SearchResponse;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ComplexSearchTest extends AbstractTest {

    @Test
    public void noFiltersTest() {
        //when
        SearchResponse response = complexSearch(new HashMap<>()).as(SearchResponse.class);

        //then
        assertTrue(response.getResults().size() > 0);
        assertEquals(0, response.getOffset());
        assertEquals(10, response.getNumber());
        assertTrue(response.getTotalResults() > 0);
    }

    @Test
    public void dietTest() {
        //given
        String diet = "gluten free";
        Map<String, String> params = new HashMap<>() {{
            put("diet", diet);
            put("addRecipeInformation", "true");
        }};

        //when
        List<Recipe> recipes = complexSearch(params).then().extract().jsonPath().getList("results", Recipe.class);

        //then
        recipes.forEach(recipe -> assertTrue(recipe.getDiets().contains(diet)));
    }

    @Test
    public void sortingTest() {
        //given
        Map<String, String> params = new HashMap<>() {{
            put("sort", "protein");
            put("sortDirection", "asc");
        }};

        //when
        List<Float> protein = complexSearch(params).then().extract().jsonPath().getList("results.nutrition.nutrients.amount.flatten()");

        //then
        Float minProtein = protein.get(0);
        for (int i = 1; i < protein.size(); i++) {
            assertTrue(protein.get(i) >= minProtein);
            minProtein = protein.get(i);
        }
    }

    @Test
    public void findByWord() {
        //given
        String keyWord = "banana";
        Map<String, String> params = new HashMap<>() {{
            put("titleMatch", keyWord);
        }};

        //when
        List<String> titles = complexSearch(params).then().extract().jsonPath().getList("results.title.flatten()");

        //then
        titles.forEach(title -> assertTrue(title.toLowerCase().contains(keyWord)));
    }

    @Test
    public void excludeCuisines() {
        //given
        String frenchCuisine = "French";
        String italianCuisine = "Italian";
        Map<String, String> params = new HashMap<>() {{
            put("excludeCuisine", frenchCuisine + ", " + italianCuisine);
            put("addRecipeInformation", "true");
        }};

        //when
        List<Recipe> recipes = complexSearch(params).then().extract().jsonPath().getList("results", Recipe.class);

        //then
        recipes.forEach(recipe -> assertFalse(recipe.getCuisines().contains(frenchCuisine) || recipe.getCuisines().contains(italianCuisine)));
    }
}
