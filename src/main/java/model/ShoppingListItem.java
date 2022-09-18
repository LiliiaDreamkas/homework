package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingListItem {
    private long id;
    private String name;
    private Measures measures;
    private boolean pantryItem;
    private String aisle;
    private float cost;
    private long ingredientId;
}
