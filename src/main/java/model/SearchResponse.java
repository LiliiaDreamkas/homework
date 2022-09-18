package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {
    private List<Recipe> results;
    private int offset;
    private int number;
    private int totalResults;
}
