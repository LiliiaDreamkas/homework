package lesson3;

import io.restassured.path.json.JsonPath;
import model.Ingredient;
import model.ItemValue;
import model.MealPlannerItem;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MealPlannerTest  extends AbstractTest {
    private static long itemId;

    @Order(1)
    @Test
    public void addItemsToMealPlannerTest() {
        //given
        MealPlannerItem item1 = new MealPlannerItem(System.currentTimeMillis() / 1000L, 1, 0, "INGREDIENTS",
                new ItemValue(asList(new Ingredient("first ingr"), new Ingredient("second ingr", "spoon", "2", ""))));
        MealPlannerItem item2 = new MealPlannerItem(System.currentTimeMillis() / 1000L, 1, 2, "MENU_ITEM",
                new ItemValue(378557, 1, "Pizza 73 BBQ Steak Pizza, 9", "png"));

        //when
        JsonPath response = addItemToMealPlan(asList(item1, item2)).then().extract().jsonPath();

        //then
        itemId = response.getLong("id");
        assertThat(itemId, not(nullValue()));
        assertThat(response.getString("status"), is("success"));
    }

    @Order(2)
    @Test
    public void removeItemFrom() {
        //when
        JsonPath response = removeItemFromMealPlan(itemId).then().extract().jsonPath();

        //then
        assertThat(response.getString("status"), is("success"));
    }
}
