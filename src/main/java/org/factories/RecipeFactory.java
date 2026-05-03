package org.factories;

import org.model.entity.Recipe;
import org.model.enums.DietType;
import org.model.enums.DifficultyLevel;
import org.model.enums.MealType;

import java.util.List;
import java.util.Set;

public class RecipeFactory {

    // Cria uma lista de receitas Star Wars de exemplo
    // Estas receitas são usadas para popular a base de dados quando a app arranca
    public static List<Recipe> createSampleRecipes() {
        return List.of(
                createBanthaStew(),
                createCoruscantNoodles(),
                createDagobahSoup(),
                createEwokRoast(),
                createMandalorianBrisket()
        );
    }

    // Receita 1 — Bantha Stew (Tatooine)
    // Inspirada no planeta desértico com dois sóis
    private static Recipe createBanthaStew() {
        Recipe recipe = new Recipe();
        recipe.setName("Bantha Stew");
        recipe.setDescription("A hearty stew from the desert planet Tatooine, " +
                "made with Bantha meat and desert spices.");
        recipe.setCategory("OMNIVORE");
        recipe.setPreparationTime(60);
        recipe.setServings(4);
        recipe.setCalories(450);
        recipe.setProtein(35.0);
        recipe.setCarbs(20.0);
        recipe.setFat(15.0);
        recipe.setDifficultyLevel(DifficultyLevel.MEDIUM);
        recipe.setMealType(MealType.DINNER);
        recipe.setDietTypes(Set.of(DietType.OMNIVORE));
        recipe.setTip("Best served under a double sunset!");
        return recipe;
    }


    // Receita 2 — Coruscant Noodles
    // Inspirada no planeta cidade, cosmopolita e agitado
    private static Recipe createCoruscantNoodles() {
        Recipe recipe = new Recipe();
        recipe.setName("Coruscant Noodles");
        recipe.setDescription("A quick and flavourful noodle dish from the " +
                "city-planet Coruscant, inspired by its multicultural cuisine.");
        recipe.setCategory("VEGAN");
        recipe.setPreparationTime(20);
        recipe.setServings(2);
        recipe.setCalories(320);
        recipe.setProtein(12.0);
        recipe.setCarbs(55.0);
        recipe.setFat(8.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.LUNCH);
        recipe.setDietTypes(Set.of(DietType.VEGAN));
        recipe.setTip("Add extra spice if you're feeling like a Sith Lord!");
        return recipe;
    }


    // Receita 3 — Dagobah Swamp Soup
    // Inspirada no planeta pântano onde Yoda vivia
    private static Recipe createDagobahSoup() {
        Recipe recipe = new Recipe();
        recipe.setName("Dagobah Swamp Soup");
        recipe.setDescription("A mysterious green soup full of herbs and vegetables, " +
                "just like Master Yoda would make in his swamp hut.");
        recipe.setCategory("VEGAN");
        recipe.setPreparationTime(30);
        recipe.setServings(3);
        recipe.setCalories(180);
        recipe.setProtein(8.0);
        recipe.setCarbs(25.0);
        recipe.setFat(5.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.LUNCH);
        recipe.setDietTypes(Set.of(DietType.VEGAN, DietType.GLUTEN_FREE));
        recipe.setTip("Patience you must have when cooking this soup!");
        return recipe;
    }


    // Receita 4 — Ewok Forest Roast
    // Inspirada nas celebrações dos Ewoks em Endor
    private static Recipe createEwokRoast() {
        Recipe recipe = new Recipe();
        recipe.setName("Ewok Forest Roast");
        recipe.setDescription("A festive roast inspired by the Ewok celebrations " +
                "on the forest moon of Endor.");
        recipe.setCategory("OMNIVORE");
        recipe.setPreparationTime(120);
        recipe.setServings(6);
        recipe.setCalories(580);
        recipe.setProtein(45.0);
        recipe.setCarbs(15.0);
        recipe.setFat(25.0);
        recipe.setDifficultyLevel(DifficultyLevel.HARD);
        recipe.setMealType(MealType.DINNER);
        recipe.setDietTypes(Set.of(DietType.OMNIVORE, DietType.GLUTEN_FREE));
        recipe.setTip("Celebrate like the Rebellion just won the war!");
        return recipe;
    }

    // Receita 5 — Mandalorian Brisket
    // Inspirada na culinária robusta dos Mandalorianos
    private static Recipe createMandalorianBrisket() {
        Recipe recipe = new Recipe();
        recipe.setName("Mandalorian Brisket");
        recipe.setDescription("A slow-cooked brisket with bold spices, " +
                "fit for a Mandalorian warrior on a long journey.");
        recipe.setCategory("OMNIVORE");
        recipe.setPreparationTime(180);
        recipe.setServings(5);
        recipe.setCalories(620);
        recipe.setProtein(50.0);
        recipe.setCarbs(10.0);
        recipe.setFat(30.0);
        recipe.setDifficultyLevel(DifficultyLevel.HARD);
        recipe.setMealType(MealType.DINNER);
        recipe.setDietTypes(Set.of(DietType.OMNIVORE, DietType.KETO));
        recipe.setTip("This is the way... to cook a perfect brisket!");
        return recipe;
    }
}