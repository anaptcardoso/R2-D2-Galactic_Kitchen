package org.model.valueObject;

import jakarta.persistence.Embeddable;

@Embeddable
public class NutritionProfile {

    private int calories;
    private double protein;
    private double carbs;
    private double fat;

     // GETTERS / SETTERS

    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }

    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }

    public double getCarbs() { return carbs; }
    public void setCarbs(double carbs) { this.carbs = carbs; }

    public double getFat() { return fat; }
    public void setFat(double fat) { this.fat = fat; }
}
