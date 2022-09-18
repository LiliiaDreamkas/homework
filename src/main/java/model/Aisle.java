package model;

import lombok.Data;

import java.util.List;

@Data
public class Aisle {
    private String aisle;
    private List<ShoppingListItem> items;
}
