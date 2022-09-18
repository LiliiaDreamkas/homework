package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MealPlannerItem {
    private long date;
    private int slot;
    private int position;
    private String type;
    private ItemValue value;

    public MealPlannerItem() {
    }

    public MealPlannerItem(long date, int slot, int position, String type, ItemValue value) {
        this.date = date;
        this.slot = slot;
        this.position = position;
        this.type = type;
        this.value = value;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ItemValue getValue() {
        return value;
    }

    public void setValue(ItemValue value) {
        this.value = value;
    }
}
