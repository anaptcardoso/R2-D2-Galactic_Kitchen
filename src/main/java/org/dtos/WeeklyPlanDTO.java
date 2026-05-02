package org.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class WeeklyPlanDTO {
    private int id;
    private int userId;
    private LocalDate weekStart;
    private LocalDate weekEnd;

    // lista de receitas do plano semanal
    private List<RecipeDTO> recipes;

    // lista de compras gerada automaticamente
    private List<String> shoppingList;


    public WeeklyPlanDTO(){}
    public WeeklyPlanDTO(int id, int userId, LocalDate weekStart, LocalDate weekEnd,
                         List<RecipeDTO> recipes, List<String> shoppingList) {
        this.id = id;
        this.userId = userId;
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
        this.recipes = recipes;
        this.shoppingList = shoppingList;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getWeekStart() {
        return weekStart;
    }

    public LocalDate getWeekEnd() {
        return weekEnd;
    }

    public List<RecipeDTO> getMeals() {
        return recipes;
    }

    public List<String> getShoppingList() {
        return shoppingList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setWeekStart(LocalDate weekStart) {
        this.weekStart = weekStart;
    }

    public void setWeekEnd(LocalDate weekEnd) {
        this.weekEnd = weekEnd;
    }

    public void setMeals(List<RecipeDTO> recipes) {
        this.recipes = recipes;
    }

    public void setShoppingList(List<String> shoppingList) {
        this.shoppingList = shoppingList;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof WeeklyPlanDTO other)) return false;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "PlanDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", weekStart=" + weekStart +
                ", weekEnd=" + weekEnd +
                '}';
    }
}
