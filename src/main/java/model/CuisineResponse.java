package model;

import lombok.Data;

import java.util.List;

@Data
public class CuisineResponse {
    private String cuisine;
    private List<String> cuisines;
    private float confidence;
}
