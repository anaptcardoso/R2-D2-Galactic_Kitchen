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
                createMandalorianBrisket(),
                createMediterraneanChickenBowl(),
                createClassicPancakes(),
                createAvocadoToast(),
                createBeefTacos(),
                createVeggieOmelette(),
                createSalmonWithVegetables(),
                createCreamyMushroomPasta(),
                createChickenCaesarSalad(),
                createLentilCurry(),
                createBananaSmoothie(),
                createGrilledTurkeyBurger(),
                createGreekYogurtParfait(),
                createShrimpStirFry(),
                createTomatoBasilSoup(),
                createChickenAlfredo(),
                createQuinoaSalad(),
                createBBQChickenPizza(),
                createSpinachRisotto(),
                createBakedSweetPotatoes(),
                createTunaSandwich(),
                createChocolateBrownie(),
                createStrawberryCheesecake(),
                createBananaMuffins(),
                createProteinEnergyBalls(),
                createYogurtFruitBowl(),
                createIceCreamSundae(),
                createHummusWithVeggies(),
                createApplePie(),
                createRoastedChickpeas(),
                createChocolateChipCookies(),
                createPeanutButterToast(),
                createLemonTart(),
                createMixedNutsBowl(),
                createRicePudding(),
                createCheeseCrackersPlate()
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

    // Receita 6 — Mediterranean Chicken Bowl
    private static Recipe createMediterraneanChickenBowl() {
        Recipe recipe = new Recipe();
        recipe.setName("Mediterranean Chicken Bowl");
        recipe.setDescription("A healthy bowl with grilled chicken, rice, vegetables and feta cheese.");
        recipe.setCategory("OMNIVORE");
        recipe.setPreparationTime(35);
        recipe.setServings(2);
        recipe.setCalories(480);
        recipe.setProtein(38.0);
        recipe.setCarbs(42.0);
        recipe.setFat(16.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.LUNCH);
        recipe.setDietTypes(Set.of(DietType.OMNIVORE));
        recipe.setTip("Add olives and lemon juice for extra flavour!");
        return recipe;
    }

    // Receita 7 — Classic Pancakes
    private static Recipe createClassicPancakes() {
        Recipe recipe = new Recipe();
        recipe.setName("Classic Pancakes");
        recipe.setDescription("Fluffy homemade pancakes perfect for breakfast.");
        recipe.setCategory("VEGETARIAN");
        recipe.setPreparationTime(20);
        recipe.setServings(4);
        recipe.setCalories(320);
        recipe.setProtein(9.0);
        recipe.setCarbs(45.0);
        recipe.setFat(10.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.BREAKFAST);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN));
        recipe.setTip("Serve with maple syrup and fresh berries.");
        return recipe;
    }

    // Receita 8 — Avocado Toast
    private static Recipe createAvocadoToast() {
        Recipe recipe = new Recipe();
        recipe.setName("Avocado Toast");
        recipe.setDescription("Crunchy toast topped with smashed avocado and spices.");
        recipe.setCategory("VEGAN");
        recipe.setPreparationTime(10);
        recipe.setServings(1);
        recipe.setCalories(250);
        recipe.setProtein(6.0);
        recipe.setCarbs(22.0);
        recipe.setFat(14.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.BREAKFAST);
        recipe.setDietTypes(Set.of(DietType.VEGAN));
        recipe.setTip("Add chili flakes for a spicy kick.");
        return recipe;
    }

    // Receita 9 — Beef Tacos
    private static Recipe createBeefTacos() {
        Recipe recipe = new Recipe();
        recipe.setName("Beef Tacos");
        recipe.setDescription("Mexican-style tacos filled with seasoned beef and fresh toppings.");
        recipe.setCategory("OMNIVORE");
        recipe.setPreparationTime(30);
        recipe.setServings(3);
        recipe.setCalories(540);
        recipe.setProtein(30.0);
        recipe.setCarbs(38.0);
        recipe.setFat(24.0);
        recipe.setDifficultyLevel(DifficultyLevel.MEDIUM);
        recipe.setMealType(MealType.DINNER);
        recipe.setDietTypes(Set.of(DietType.OMNIVORE));
        recipe.setTip("Top with fresh cilantro and lime.");
        return recipe;
    }

    // Receita 10 — Veggie Omelette
    private static Recipe createVeggieOmelette() {
        Recipe recipe = new Recipe();
        recipe.setName("Veggie Omelette");
        recipe.setDescription("A light omelette packed with fresh vegetables.");
        recipe.setCategory("VEGETARIAN");
        recipe.setPreparationTime(15);
        recipe.setServings(1);
        recipe.setCalories(280);
        recipe.setProtein(18.0);
        recipe.setCarbs(8.0);
        recipe.setFat(18.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.BREAKFAST);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN, DietType.GLUTEN_FREE));
        recipe.setTip("Use mushrooms and spinach for extra nutrition.");
        return recipe;
    }

    // Receita 11 — Salmon with Vegetables
    private static Recipe createSalmonWithVegetables() {
        Recipe recipe = new Recipe();
        recipe.setName("Salmon with Vegetables");
        recipe.setDescription("Oven-baked salmon served with roasted vegetables.");
        recipe.setCategory("PESCATARIAN");
        recipe.setPreparationTime(40);
        recipe.setServings(2);
        recipe.setCalories(510);
        recipe.setProtein(42.0);
        recipe.setCarbs(18.0);
        recipe.setFat(28.0);
        recipe.setDifficultyLevel(DifficultyLevel.MEDIUM);
        recipe.setMealType(MealType.DINNER);
        recipe.setDietTypes(Set.of(DietType.OMNIVORE, DietType.GLUTEN_FREE));
        recipe.setTip("Lemon and dill pair perfectly with salmon.");
        return recipe;
    }

    // Receita 12 — Creamy Mushroom Pasta
    private static Recipe createCreamyMushroomPasta() {
        Recipe recipe = new Recipe();
        recipe.setName("Creamy Mushroom Pasta");
        recipe.setDescription("Pasta in a creamy mushroom sauce with parmesan cheese.");
        recipe.setCategory("VEGETARIAN");
        recipe.setPreparationTime(25);
        recipe.setServings(3);
        recipe.setCalories(460);
        recipe.setProtein(14.0);
        recipe.setCarbs(58.0);
        recipe.setFat(18.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.DINNER);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN));
        recipe.setTip("Use fresh parsley before serving.");
        return recipe;
    }

    // Receita 13 — Chicken Caesar Salad
    private static Recipe createChickenCaesarSalad() {
        Recipe recipe = new Recipe();
        recipe.setName("Chicken Caesar Salad");
        recipe.setDescription("Fresh romaine lettuce with grilled chicken and Caesar dressing.");
        recipe.setCategory("OMNIVORE");
        recipe.setPreparationTime(20);
        recipe.setServings(2);
        recipe.setCalories(390);
        recipe.setProtein(34.0);
        recipe.setCarbs(12.0);
        recipe.setFat(20.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.LUNCH);
        recipe.setDietTypes(Set.of(DietType.OMNIVORE));
        recipe.setTip("Add homemade croutons for extra crunch.");
        return recipe;
    }

    // Receita 14 — Lentil Curry
    private static Recipe createLentilCurry() {
        Recipe recipe = new Recipe();
        recipe.setName("Lentil Curry");
        recipe.setDescription("A warm and comforting curry made with lentils and coconut milk.");
        recipe.setCategory("VEGAN");
        recipe.setPreparationTime(40);
        recipe.setServings(4);
        recipe.setCalories(350);
        recipe.setProtein(16.0);
        recipe.setCarbs(42.0);
        recipe.setFat(12.0);
        recipe.setDifficultyLevel(DifficultyLevel.MEDIUM);
        recipe.setMealType(MealType.DINNER);
        recipe.setDietTypes(Set.of(DietType.VEGAN, DietType.GLUTEN_FREE));
        recipe.setTip("Serve with basmati rice.");
        return recipe;
    }

    // Receita 15 — Banana Smoothie
    private static Recipe createBananaSmoothie() {
        Recipe recipe = new Recipe();
        recipe.setName("Banana Smoothie");
        recipe.setDescription("A creamy smoothie with banana and milk.");
        recipe.setCategory("VEGETARIAN");
        recipe.setPreparationTime(5);
        recipe.setServings(1);
        recipe.setCalories(210);
        recipe.setProtein(7.0);
        recipe.setCarbs(35.0);
        recipe.setFat(4.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.SNACK);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN));
        recipe.setTip("Add peanut butter for extra protein.");
        return recipe;
    }

    // Receita 16 — Grilled Turkey Burger
    private static Recipe createGrilledTurkeyBurger() {
        Recipe recipe = new Recipe();
        recipe.setName("Grilled Turkey Burger");
        recipe.setDescription("Juicy turkey burger served in a toasted bun.");
        recipe.setCategory("OMNIVORE");
        recipe.setPreparationTime(30);
        recipe.setServings(2);
        recipe.setCalories(430);
        recipe.setProtein(32.0);
        recipe.setCarbs(28.0);
        recipe.setFat(18.0);
        recipe.setDifficultyLevel(DifficultyLevel.MEDIUM);
        recipe.setMealType(MealType.DINNER);
        recipe.setDietTypes(Set.of(DietType.OMNIVORE));
        recipe.setTip("Add avocado slices for extra flavour.");
        return recipe;
    }

    // Receita 17 — Greek Yogurt Parfait
    private static Recipe createGreekYogurtParfait() {
        Recipe recipe = new Recipe();
        recipe.setName("Greek Yogurt Parfait");
        recipe.setDescription("Layers of yogurt, granola and fresh fruit.");
        recipe.setCategory("VEGETARIAN");
        recipe.setPreparationTime(10);
        recipe.setServings(1);
        recipe.setCalories(270);
        recipe.setProtein(15.0);
        recipe.setCarbs(30.0);
        recipe.setFat(8.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.BREAKFAST);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN));
        recipe.setTip("Use honey for natural sweetness.");
        return recipe;
    }

    // Receita 18 — Shrimp Stir Fry
    private static Recipe createShrimpStirFry() {
        Recipe recipe = new Recipe();
        recipe.setName("Shrimp Stir Fry");
        recipe.setDescription("Quick shrimp stir fry with vegetables and soy sauce.");
        recipe.setCategory("PESCATARIAN");
        recipe.setPreparationTime(25);
        recipe.setServings(2);
        recipe.setCalories(390);
        recipe.setProtein(30.0);
        recipe.setCarbs(26.0);
        recipe.setFat(14.0);
        recipe.setDifficultyLevel(DifficultyLevel.MEDIUM);
        recipe.setMealType(MealType.DINNER);
        recipe.setDietTypes(Set.of(DietType.OMNIVORE));
        recipe.setTip("Serve with jasmine rice.");
        return recipe;
    }

    // Receita 19 — Tomato Basil Soup
    private static Recipe createTomatoBasilSoup() {
        Recipe recipe = new Recipe();
        recipe.setName("Tomato Basil Soup");
        recipe.setDescription("Classic tomato soup with fresh basil.");
        recipe.setCategory("VEGETARIAN");
        recipe.setPreparationTime(30);
        recipe.setServings(3);
        recipe.setCalories(190);
        recipe.setProtein(5.0);
        recipe.setCarbs(24.0);
        recipe.setFat(7.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.LUNCH);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN, DietType.GLUTEN_FREE));
        recipe.setTip("Serve with toasted bread.");
        return recipe;
    }

    // Receita 20 — Chicken Alfredo
    private static Recipe createChickenAlfredo() {
        Recipe recipe = new Recipe();
        recipe.setName("Chicken Alfredo");
        recipe.setDescription("Creamy Alfredo pasta with grilled chicken.");
        recipe.setCategory("OMNIVORE");
        recipe.setPreparationTime(35);
        recipe.setServings(4);
        recipe.setCalories(620);
        recipe.setProtein(38.0);
        recipe.setCarbs(52.0);
        recipe.setFat(28.0);
        recipe.setDifficultyLevel(DifficultyLevel.MEDIUM);
        recipe.setMealType(MealType.DINNER);
        recipe.setDietTypes(Set.of(DietType.OMNIVORE));
        recipe.setTip("Use freshly grated parmesan cheese.");
        return recipe;
    }

    // Receita 21 — Quinoa Salad
    private static Recipe createQuinoaSalad() {
        Recipe recipe = new Recipe();
        recipe.setName("Quinoa Salad");
        recipe.setDescription("Fresh quinoa salad with cucumber, tomato and lemon dressing.");
        recipe.setCategory("VEGAN");
        recipe.setPreparationTime(20);
        recipe.setServings(2);
        recipe.setCalories(290);
        recipe.setProtein(10.0);
        recipe.setCarbs(38.0);
        recipe.setFat(9.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.LUNCH);
        recipe.setDietTypes(Set.of(DietType.VEGAN, DietType.GLUTEN_FREE));
        recipe.setTip("Add mint leaves for freshness.");
        return recipe;
    }

    // Receita 22 — BBQ Chicken Pizza
    private static Recipe createBBQChickenPizza() {
        Recipe recipe = new Recipe();
        recipe.setName("BBQ Chicken Pizza");
        recipe.setDescription("Homemade pizza topped with BBQ chicken and mozzarella.");
        recipe.setCategory("OMNIVORE");
        recipe.setPreparationTime(45);
        recipe.setServings(4);
        recipe.setCalories(700);
        recipe.setProtein(36.0);
        recipe.setCarbs(68.0);
        recipe.setFat(30.0);
        recipe.setDifficultyLevel(DifficultyLevel.HARD);
        recipe.setMealType(MealType.DINNER);
        recipe.setDietTypes(Set.of(DietType.OMNIVORE));
        recipe.setTip("Use red onions for extra flavour.");
        return recipe;
    }

    // Receita 23 — Spinach Risotto
    private static Recipe createSpinachRisotto() {
        Recipe recipe = new Recipe();
        recipe.setName("Spinach Risotto");
        recipe.setDescription("Creamy risotto cooked with spinach and parmesan.");
        recipe.setCategory("VEGETARIAN");
        recipe.setPreparationTime(40);
        recipe.setServings(3);
        recipe.setCalories(420);
        recipe.setProtein(11.0);
        recipe.setCarbs(54.0);
        recipe.setFat(16.0);
        recipe.setDifficultyLevel(DifficultyLevel.MEDIUM);
        recipe.setMealType(MealType.DINNER);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN, DietType.GLUTEN_FREE));
        recipe.setTip("Stir constantly for the perfect texture.");
        return recipe;
    }

    // Receita 24 — Baked Sweet Potatoes
    private static Recipe createBakedSweetPotatoes() {
        Recipe recipe = new Recipe();
        recipe.setName("Baked Sweet Potatoes");
        recipe.setDescription("Roasted sweet potatoes with herbs and olive oil.");
        recipe.setCategory("VEGAN");
        recipe.setPreparationTime(35);
        recipe.setServings(2);
        recipe.setCalories(240);
        recipe.setProtein(4.0);
        recipe.setCarbs(42.0);
        recipe.setFat(6.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.SNACK);
        recipe.setDietTypes(Set.of(DietType.VEGAN, DietType.GLUTEN_FREE));
        recipe.setTip("Sprinkle cinnamon for extra sweetness.");
        return recipe;
    }

    // Receita 25 — Tuna Sandwich
    private static Recipe createTunaSandwich() {
        Recipe recipe = new Recipe();
        recipe.setName("Tuna Sandwich");
        recipe.setDescription("Classic tuna sandwich with lettuce and tomato.");
        recipe.setCategory("PESCATARIAN");
        recipe.setPreparationTime(10);
        recipe.setServings(1);
        recipe.setCalories(330);
        recipe.setProtein(22.0);
        recipe.setCarbs(28.0);
        recipe.setFat(12.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.LUNCH);
        recipe.setDietTypes(Set.of(DietType.OMNIVORE));
        recipe.setTip("Toast the bread for better texture.");
        return recipe;
    }

    // Receita 26 — Chocolate Brownie
    private static Recipe createChocolateBrownie() {
        Recipe recipe = new Recipe();
        recipe.setName("Chocolate Brownie");
        recipe.setDescription("Rich and fudgy chocolate brownies with a crispy top.");
        recipe.setCategory("DESSERT");
        recipe.setPreparationTime(40);
        recipe.setServings(6);
        recipe.setCalories(380);
        recipe.setProtein(5.0);
        recipe.setCarbs(45.0);
        recipe.setFat(20.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.DESSERT);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN));
        recipe.setTip("Serve warm with vanilla ice cream.");
        return recipe;
    }

    // Receita 26 — Strawberry Cheesecake
    private static Recipe createStrawberryCheesecake() {
        Recipe recipe = new Recipe();
        recipe.setName("Strawberry Cheesecake");
        recipe.setDescription("Creamy cheesecake topped with fresh strawberries.");
        recipe.setCategory("DESSERT");
        recipe.setPreparationTime(90);
        recipe.setServings(8);
        recipe.setCalories(420);
        recipe.setProtein(7.0);
        recipe.setCarbs(36.0);
        recipe.setFat(27.0);
        recipe.setDifficultyLevel(DifficultyLevel.MEDIUM);
        recipe.setMealType(MealType.DESSERT);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN));
        recipe.setTip("Chill for at least 4 hours before serving.");
        return recipe;
    }

    // Receita 27 — Banana Muffins
    private static Recipe createBananaMuffins() {
        Recipe recipe = new Recipe();
        recipe.setName("Banana Muffins");
        recipe.setDescription("Soft banana muffins perfect for breakfast or snacks.");
        recipe.setCategory("SNACK");
        recipe.setPreparationTime(30);
        recipe.setServings(6);
        recipe.setCalories(210);
        recipe.setProtein(4.0);
        recipe.setCarbs(32.0);
        recipe.setFat(7.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.SNACK);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN));
        recipe.setTip("Add chocolate chips for extra sweetness.");
        return recipe;
    }

    // Receita 28 — Protein Energy Balls
    private static Recipe createProteinEnergyBalls() {
        Recipe recipe = new Recipe();
        recipe.setName("Protein Energy Balls");
        recipe.setDescription("Healthy no-bake energy balls with oats and peanut butter.");
        recipe.setCategory("SNACK");
        recipe.setPreparationTime(15);
        recipe.setServings(12);
        recipe.setCalories(110);
        recipe.setProtein(5.0);
        recipe.setCarbs(10.0);
        recipe.setFat(5.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.SNACK);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN));
        recipe.setTip("Keep refrigerated for better texture.");
        return recipe;
    }

    // Receita 29 — Yogurt Fruit Bowl
    private static Recipe createYogurtFruitBowl() {
        Recipe recipe = new Recipe();
        recipe.setName("Yogurt Fruit Bowl");
        recipe.setDescription("Greek yogurt served with fresh fruits and honey.");
        recipe.setCategory("SNACK");
        recipe.setPreparationTime(10);
        recipe.setServings(1);
        recipe.setCalories(240);
        recipe.setProtein(14.0);
        recipe.setCarbs(28.0);
        recipe.setFat(7.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.SNACK);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN, DietType.GLUTEN_FREE));
        recipe.setTip("Top with granola for extra crunch.");
        return recipe;
    }

    // Receita 30 — Vanilla Ice Cream Sundae
    private static Recipe createIceCreamSundae() {
        Recipe recipe = new Recipe();
        recipe.setName("Vanilla Ice Cream Sundae");
        recipe.setDescription("Classic vanilla ice cream with chocolate syrup and nuts.");
        recipe.setCategory("DESSERT");
        recipe.setPreparationTime(5);
        recipe.setServings(2);
        recipe.setCalories(350);
        recipe.setProtein(6.0);
        recipe.setCarbs(38.0);
        recipe.setFat(18.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.DESSERT);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN));
        recipe.setTip("Add cherries on top for a classic touch.");
        return recipe;
    }

    // Receita 31 — Hummus with Veggies
    private static Recipe createHummusWithVeggies() {
        Recipe recipe = new Recipe();
        recipe.setName("Hummus with Veggies");
        recipe.setDescription("Creamy hummus served with fresh carrot and cucumber sticks.");
        recipe.setCategory("SNACK");
        recipe.setPreparationTime(15);
        recipe.setServings(2);
        recipe.setCalories(180);
        recipe.setProtein(6.0);
        recipe.setCarbs(16.0);
        recipe.setFat(10.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.SNACK);
        recipe.setDietTypes(Set.of(DietType.VEGAN, DietType.GLUTEN_FREE));
        recipe.setTip("Paprika and olive oil make great toppings.");
        return recipe;
    }

    // Receita 32 — Apple Pie
    private static Recipe createApplePie() {
        Recipe recipe = new Recipe();
        recipe.setName("Apple Pie");
        recipe.setDescription("Traditional apple pie with cinnamon and flaky crust.");
        recipe.setCategory("DESSERT");
        recipe.setPreparationTime(80);
        recipe.setServings(8);
        recipe.setCalories(410);
        recipe.setProtein(4.0);
        recipe.setCarbs(52.0);
        recipe.setFat(20.0);
        recipe.setDifficultyLevel(DifficultyLevel.MEDIUM);
        recipe.setMealType(MealType.DESSERT);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN));
        recipe.setTip("Serve warm with whipped cream.");
        return recipe;
    }

    // Receita 33 — Roasted Chickpeas
    private static Recipe createRoastedChickpeas() {
        Recipe recipe = new Recipe();
        recipe.setName("Roasted Chickpeas");
        recipe.setDescription("Crunchy roasted chickpeas with spicy seasoning.");
        recipe.setCategory("SNACK");
        recipe.setPreparationTime(35);
        recipe.setServings(3);
        recipe.setCalories(160);
        recipe.setProtein(8.0);
        recipe.setCarbs(18.0);
        recipe.setFat(6.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.SNACK);
        recipe.setDietTypes(Set.of(DietType.VEGAN, DietType.GLUTEN_FREE));
        recipe.setTip("Bake until extra crispy.");
        return recipe;
    }

    // Receita 34 — Chocolate Chip Cookies
    private static Recipe createChocolateChipCookies() {
        Recipe recipe = new Recipe();
        recipe.setName("Chocolate Chip Cookies");
        recipe.setDescription("Classic homemade cookies loaded with chocolate chips.");
        recipe.setCategory("DESSERT");
        recipe.setPreparationTime(25);
        recipe.setServings(12);
        recipe.setCalories(190);
        recipe.setProtein(2.0);
        recipe.setCarbs(24.0);
        recipe.setFat(9.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.DESSERT);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN));
        recipe.setTip("Use dark chocolate for richer flavour.");
        return recipe;
    }

    // Receita 35 — Peanut Butter Toast
    private static Recipe createPeanutButterToast() {
        Recipe recipe = new Recipe();
        recipe.setName("Peanut Butter Toast");
        recipe.setDescription("Toasted bread topped with peanut butter and banana slices.");
        recipe.setCategory("SNACK");
        recipe.setPreparationTime(5);
        recipe.setServings(1);
        recipe.setCalories(290);
        recipe.setProtein(10.0);
        recipe.setCarbs(28.0);
        recipe.setFat(15.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.SNACK);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN));
        recipe.setTip("Sprinkle cinnamon on top.");
        return recipe;
    }

    // Receita 36 — Lemon Tart
    private static Recipe createLemonTart() {
        Recipe recipe = new Recipe();
        recipe.setName("Lemon Tart");
        recipe.setDescription("Tangy lemon tart with buttery crust.");
        recipe.setCategory("DESSERT");
        recipe.setPreparationTime(70);
        recipe.setServings(6);
        recipe.setCalories(340);
        recipe.setProtein(5.0);
        recipe.setCarbs(40.0);
        recipe.setFat(17.0);
        recipe.setDifficultyLevel(DifficultyLevel.MEDIUM);
        recipe.setMealType(MealType.DESSERT);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN));
        recipe.setTip("Dust with powdered sugar before serving.");
        return recipe;
    }

    // Receita 37 — Mixed Nuts Bowl
    private static Recipe createMixedNutsBowl() {
        Recipe recipe = new Recipe();
        recipe.setName("Mixed Nuts Bowl");
        recipe.setDescription("A healthy mix of almonds, walnuts and cashews.");
        recipe.setCategory("SNACK");
        recipe.setPreparationTime(2);
        recipe.setServings(2);
        recipe.setCalories(220);
        recipe.setProtein(7.0);
        recipe.setCarbs(9.0);
        recipe.setFat(18.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.SNACK);
        recipe.setDietTypes(Set.of(DietType.VEGAN, DietType.GLUTEN_FREE, DietType.KETO));
        recipe.setTip("Store in airtight containers.");
        return recipe;
    }

    // Receita 38 — Rice Pudding
    private static Recipe createRicePudding() {
        Recipe recipe = new Recipe();
        recipe.setName("Rice Pudding");
        recipe.setDescription("Creamy rice pudding flavoured with cinnamon and vanilla.");
        recipe.setCategory("DESSERT");
        recipe.setPreparationTime(45);
        recipe.setServings(4);
        recipe.setCalories(260);
        recipe.setProtein(6.0);
        recipe.setCarbs(38.0);
        recipe.setFat(9.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.DESSERT);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN, DietType.GLUTEN_FREE));
        recipe.setTip("Serve chilled or warm.");
        return recipe;
    }

    // Receita 39 — Cheese Crackers Plate
    private static Recipe createCheeseCrackersPlate() {
        Recipe recipe = new Recipe();
        recipe.setName("Cheese Crackers Plate");
        recipe.setDescription("Simple snack with cheese slices and crispy crackers.");
        recipe.setCategory("SNACK");
        recipe.setPreparationTime(5);
        recipe.setServings(2);
        recipe.setCalories(310);
        recipe.setProtein(12.0);
        recipe.setCarbs(22.0);
        recipe.setFat(18.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.SNACK);
        recipe.setDietTypes(Set.of(DietType.VEGETARIAN));
        recipe.setTip("Pair with grapes or olives.");
        return recipe;
    }
}