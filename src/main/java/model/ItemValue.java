package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemValue {
    private List<Ingredient> ingredients;
    private long id;
    private int servings;
    private String title;
    private String imageType;

    public ItemValue() {
    }

    public ItemValue(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ItemValue(long id, int servings, String title, String imageType) {
        this.id = id;
        this.servings = servings;
        this.title = title;
        this.imageType = imageType;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
