package org.dtos;

import org.model.enums.DietType;
import org.model.enums.DifficultyLevel;
import org.model.enums.MealType;

import java.util.List;
import java.util.Set;

public class RecipeDTO {
    private int id;
    private String name;
    private String description;
    private String category;
    private int preparationTime;
    private int servings;
    private int calories;
    private double protein;
    private double carbs;
    private double fat;
    private DifficultyLevel difficulty;
    private MealType mealType;
    private List<IngredientDTO> ingredient;
    private Set<DietType> dietTypes;
    private List<String> steps;
    private String tip;

    // Constructors

    public RecipeDTO() {} //cria o objeto sem preencher nenhum campo para JSON e JPA é preenchido com setters
    public RecipeDTO(int id, String name,String description, //Usado por nos no convertor
                     String category, int preparationTime,
                     int servings, int calories,
                     double protein, double carbs, double fat,
                     DifficultyLevel difficulty, MealType mealType,
                     List<IngredientDTO> ingredient, Set<DietType> dietTypes,
                     List<String> steps, String tip){
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.calories = calories;
        this.difficulty = difficulty;
        this.mealType = mealType;
        this.ingredient = ingredient;
        this.steps = steps;
        this.tip = tip;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.dietTypes = dietTypes;
    }

    //Getters and Setters
    public int getId() { return id; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public String getCategory() { return category; }

    public int getPreparationTime() { return preparationTime; }

    public int getServings() { return servings; }

    public int getCalories() { return calories; }

    public double getProtein() { return protein; }

    public double getCarbs() { return carbs; }

    public double getFat() { return fat; }

    public DifficultyLevel getDifficultyLevel() { return difficulty; }

    public MealType getMealType() { return mealType; }

    public List<IngredientDTO> getIngredients() { return ingredient; }

    public Set<DietType> getDietTypes() { return dietTypes; }

    public List<String> getSteps() { return steps; }

    public String getTip() { return tip; }

    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) { this.description = description; }

    public void setCategory(String category) { this.category = category; }

    public void setPreparationTime(int preparationTime) { this.preparationTime = preparationTime; }

    public void setServings(int servings) { this.servings = servings; }

    public void setCalories(int calories) { this.calories = calories; }

    public void setProtein(double protein) { this.protein = protein; }

    public void setCarbs(double carbs) { this.carbs = carbs; }

    public void setFat(double fat) { this.fat = fat; }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) { this.difficulty = difficultyLevel; }

    public void setMealType(MealType mealType) { this.mealType = mealType; }

    public void setIngredients(List<IngredientDTO> ingredients) { this.ingredient = ingredient; }

    public void setDietTypes(Set<DietType> dietTypes) { this.dietTypes = dietTypes; }

    public void setSteps(List<String> steps) { this.steps = steps; }

    public void setTip(String tip) { this.tip = tip; }


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
                ", preparationTime=" + preparationTime +
                ", difficultyLevel=" + difficulty +
                '}';
    }

}
