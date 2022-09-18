package model;

import lombok.Data;

@Data
public class AddToShoppingListRequest {
    private String item;
    private String aisle;
    private boolean parse;

    public AddToShoppingListRequest(String item, String aisle, boolean parse) {
        this.item = item;
        this.aisle = aisle;
        this.parse = parse;
    }
}
