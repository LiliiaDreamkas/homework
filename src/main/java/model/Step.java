package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Step {
    private int number;
    private String step;
    private List<RecipeStepItem> ingredients;
    private List<RecipeStepItem> equipment;
}
