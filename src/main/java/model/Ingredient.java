package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingredient {
    private String name;
    private String unit;
    private String amount;
    private String image;

    public Ingredient() {
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(String name, String unit, String amount, String image) {
        this.name = name;
        this.unit = unit;
        this.amount = amount;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
