package org.dtos;

import org.model.enums.DifficultyLevel;
import org.model.enums.MealType;

import java.util.List;

public class RecipeDTO {
    private int id;
    private String name;
    private String description;
    private String category;
    private int durationMinutes;
    private int servings;
    private int calories;
    private DifficultyLevel difficulty;
    private MealType mealType;
    private List<String> ingredients;
    private List<String> steps;
    private String tip;

    // Constructors

    public RecipeDTO() {} //cria o objeto sem preencher nenhum campo para JSON e JPA é preenchido com setters
    public RecipeDTO(int id, String name,String description, //Usado por nos no convertor
                     String category, int durationMinutes,
                     int servings, int calories,
                     DifficultyLevel difficulty,
                     MealType mealType, List<String> ingredients,
                     List<String> steps, String tip){
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.durationMinutes = durationMinutes;
        this.servings = servings;
        this.calories = calories;
        this.difficulty = difficulty;
        this.mealType = mealType;
        this.ingredients = ingredients;
        this.steps = steps;
        this.tip = tip;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public int getServings() {
        return servings;
    }

    public int getCalories() {
        return calories;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public MealType getMealType() {
        return mealType;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public String getTip() {
        return tip;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setDifficulty(DifficultyLevel difficulty) {
        this.difficulty = difficulty;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    //overrides

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true; // comparação se o objeto éigual a si proprio
        if(!(obj instanceof  RecipeDTO other)) return false;  //other outros tipos sem ser object
        return id == other.id;
    }

    @Override
    public String toString() {
        return "RecipeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", durationMinutes=" + durationMinutes +
                ", difficulty=" + difficulty +
                '}';
    }
}
