package model;

import lombok.Data;

import java.util.List;

@Data
public class GetShoppingListResponse {
    private List<Aisle> aisles;
    private float cost;
    private long startDate;
    private long endDate;
}
