package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Nutrient {
     private String name;
     private double amount;
     private String unit;
     private float percentOfDailyNeeds;
}
