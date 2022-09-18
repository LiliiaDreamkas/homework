package lesson3;

import io.restassured.path.json.JsonPath;
import model.AddToShoppingListRequest;
import model.Aisle;
import model.GetShoppingListResponse;
import model.ShoppingListItem;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShoppingListTest extends AbstractTest {
    private static long id;
    private static ShoppingListItem listItem;

    @Order(1)
    @Test
    public void addItemToShoppingListTest() {
        //given
        AddToShoppingListRequest body = new AddToShoppingListRequest("1 package baking powder", "Baking", true);

        //when
        listItem = addItemToShoppingList(body).then().extract().body().as(ShoppingListItem.class);

        //then
        id = listItem.getId();
        assertThat(id, not(nullValue()));
        assertThat(listItem.getName(), is("baking powder"));
        assertThat(listItem.getMeasures().getOriginal().getUnit(), is("package"));
        assertThat(listItem.getMeasures().getOriginal().getAmount(), is(1.0F));
        assertThat(listItem.getName(), is("baking powder"));
        assertThat(listItem.getMeasures().getMetric().getUnit(), is("pkg"));
        assertThat(listItem.getMeasures().getMetric().getAmount(), is(1.0F));
        assertThat(listItem.getName(), is("baking powder"));
        assertThat(listItem.getMeasures().getUs().getUnit(), is("pkg"));
        assertThat(listItem.getMeasures().getUs().getAmount(), is(1.0F));
        assertThat(listItem.isPantryItem(), is(false));
    }

    @Order(2)
    @Test
    public void getShoppingListTest() {
        //when
        GetShoppingListResponse response = getShoppingList().as(GetShoppingListResponse.class);

        //then
        Aisle currentAisle = response.getAisles().stream()
                .filter(ai -> ai.getAisle().equals("Baking")).findAny().orElseThrow();
        assertThat(currentAisle.getItems(), hasItem(listItem));
    }

    @Order(3)
    @Test
    public void deleteShoppingListTest() {
        //when
        JsonPath response = removeItemFromShoppingList(id).then().extract().jsonPath();

        //then
        assertThat(response.getString("status"), is("success"));
        GetShoppingListResponse resp = getShoppingList().as(GetShoppingListResponse.class);
        Aisle currentAisle = resp.getAisles().stream()
                .filter(ai -> ai.getAisle().equals("Baking")).findAny().orElseThrow();
        assertThat(currentAisle.getItems(), not(hasItem(listItem)));
    }
}
