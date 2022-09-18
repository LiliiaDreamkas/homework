package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {
    private long id;
    private String title;
    private String image;
    private String imageType;
    private List<String> diets;
    private List<String> cuisines;
    private List<String> dishTypes;
    private Nutrition nutrition;
    private List<AnalyzedInstruction> analyzedInstructions;
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private boolean dairyFree;
    private boolean veryHealthy;
    private boolean cheap;
    private boolean veryPopular;
    private boolean sustainable;
    private boolean lowFodmap;
    private String summary;
    private String creditsText;
    private String license;
    private String sourceName;
    private String sourceUrl;
    private String gaps;
    private int weightWatcherSmartPoints;
    private int preparationMinutes;
    private int cookingMinutes;
    private int aggregateLikes;
    private int healthScore;
    private int readyInMinutes;
    private int servings;
    private float pricePerServing;
}
