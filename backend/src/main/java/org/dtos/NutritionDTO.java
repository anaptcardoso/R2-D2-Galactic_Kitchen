package org.dtos;

import org.model.enums.DietType;

import java.util.Set;

public class NutritionDTO {

    private Double weight;
    private Double height;
    private String goal;
    private String activityLevel;
    private Set<DietType> dietPreferences;
    private String allergies;

    public NutritionDTO() {}

    public NutritionDTO(Double weight, Double height, String goal,
                        String activityLevel, Set<DietType> dietPreferences,
                        String allergies) {
        this.weight = weight;
        this.height = height;
        this.goal = goal;
        this.activityLevel = activityLevel;
        this.dietPreferences = dietPreferences;
        this.allergies = allergies;
    }

    // Getters

    public Double getWeight() { return weight; }
    public Double getHeight() { return height; }
    public String getGoal() { return goal; }
    public String getActivityLevel() { return activityLevel; }
    public Set<DietType> getDietPreferences() { return dietPreferences; }
    public String getAllergies() { return allergies; }

    // Setters

    public void setWeight(Double weight) { this.weight = weight; }
    public void setHeight(Double height) { this.height = height; }
    public void setGoal(String goal) { this.goal = goal; }
    public void setActivityLevel(String activityLevel) { this.activityLevel = activityLevel; }
    public void setDietPreferences(Set<DietType> dietPreferences) { this.dietPreferences = dietPreferences; }
    public void setAllergies(String allergies) { this.allergies = allergies; }

    //  Overrides

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NutritionDTO other)) return false;
        return java.util.Objects.equals(weight, other.weight) &&
                java.util.Objects.equals(height, other.height) &&
                java.util.Objects.equals(goal, other.goal);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(weight, height, goal);
    }

    @Override
    public String toString() {
        return "NutritionDTO{" +
                "goal='" + goal + '\'' +
                ", activityLevel='" + activityLevel + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                '}';
    }

}