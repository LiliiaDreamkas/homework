package lesson5.model;

import lombok.Data;

import java.util.List;

@Data
public class GetCategoryResponse {
    private int id;
    private String title;
    private List<Product> products;
}
