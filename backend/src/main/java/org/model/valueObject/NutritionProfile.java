package org.model.valueObject;

import jakarta.persistence.*;
import org.model.entity.UserProfile;
import org.model.enums.DietType;

import java.util.Objects;
import java.util.Set;

@Embeddable
public class NutritionProfile { //cliente

    // Nutritional data
    private Double weight;
    private Double height;
    private String goal;
    private String activityLevel;

    // Food preferences
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DietType> dietPreferences;

    // Allergies
    private String allergies;

    // Constructor

    public NutritionProfile() {}

    public NutritionProfile(Double weight, Double height,
                            String goal, String activityLevel,
                            Set<DietType> dietPreferences, String allergies) {
        this.weight = weight;
        this.height = height;
        this.goal = goal;
        this.activityLevel = activityLevel;
        this.dietPreferences = dietPreferences;
        this.allergies = allergies;
    }



    // GETTERS / SETTERS
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public Set<DietType> getDietPreferences() {
        return dietPreferences;
    }

    public void setDietPreferences(Set<DietType> dietPreferences) {
        this.dietPreferences = dietPreferences;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof NutritionProfile other)) return false;
        return Objects.equals(weight, other.weight) &&
                Objects.equals(height, other.height) &&
                Objects.equals(goal, other.goal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, height, goal);
    }

    @Override
    public String toString() {
        return "NutritionProfile{" +
                "goal='" + goal + '\'' +
                ", activityLevel='" + activityLevel + '\'' +
                ", dietPreferences=" + dietPreferences +
                '}';
    }


}
