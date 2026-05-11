package org.factories;

import org.model.entity.Ingredient;
import org.model.entity.Recipe;
import org.model.enums.DietType;
import org.model.enums.DifficultyLevel;
import org.model.enums.MealType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RecipeFactory {

    // Creates a list of sample Star Wars recipes
    // These recipes are used to populate the database when the app starts
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

    private static Ingredient ingredient(Recipe recipe, double qty, String unit, String name) {
        Ingredient ingredient = new Ingredient();
        ingredient.setQuantity(qty);
        ingredient.setUnit(unit);
        ingredient.setName(name);
        ingredient.setRecipe(recipe);
        return ingredient;
    }

    private static List<Ingredient> ingredients(Recipe recipe, Object... args) {
        List<Ingredient> list = new ArrayList<>();
        for (int i = 0; i < args.length; i += 3) {
            list.add(ingredient(recipe, ((Number) args[i]).doubleValue(), (String) args[i+1], (String) args[i+2]));
        }
        return list;
    }


    // Recipe 1 — Bantha Stew (Tatooine)
    // Inspired by the desert planet with two suns
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
        recipe.setSteps("1. Cut the bantha meat into large chunks and season with desert spices.\n2. Heat oil in a large pot and brown the meat on all sides.\n3. Add onions, garlic and desert herbs.\n4. Pour in broth and bring to a boil.\n5. Reduce heat and simmer for 45 minutes until tender.\n6. Adjust seasoning and serve hot.");
        recipe.setTip("Tip: Best served under a double sunset!");
        recipe.setIngredients(ingredients(recipe,
                500, "g", "Bantha meat",
                2, "units", "Onions",
                4, "cloves", "Garlic",
                2, "tbsp", "Desert spice blend",
                500, "ml", "Beef broth",
                2, "tbsp", "Olive oil",
                1, "tsp", "Salt",
                0.5, "tsp", "Black pepper"));

        return recipe;
    }


    // Recipe 2 — Coruscant Noodles
    // Inspired by the cosmopolitan and bustling city planet
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
        recipe.setSteps("1. Cook noodles according to package instructions.\n2. In a wok, heat sesame oil over high heat.\n3. Add garlic and ginger, stir-fry for 30 seconds.\n4. Add vegetables and stir-fry for 3 minutes.\n5. Add soy sauce and cooked noodles.\n6. Toss everything together and serve immediately.");
        recipe.setTip("Tip: Add extra spice if you're feeling like a Sith Lord!");
        recipe.setIngredients(ingredients(recipe,
                200, "g", "Rice noodles",
                2, "cloves", "Garlic",
                1, "tsp", "Fresh ginger",
                1, "tbsp", "Sesame oil",
                3, "tbsp", "Soy sauce",
                150, "g", "Mixed vegetables",
                1, "tbsp", "Chili flakes"));

        return recipe;
    }


    // Recipe 3 — Dagobah Swamp Soup
    // Inspired by the swamp planet where Yoda lived.
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
        recipe.setSteps("1. Heat oil in a large pot over medium heat.\n2. Add onion and cook until softened.\n3. Add broccoli, spinach and peas.\n4. Pour in vegetable broth and bring to a boil.\n5. Simmer for 15 minutes until vegetables are tender.\n6. Blend until smooth and season with salt and pepper.");
        recipe.setTip("Tip: Patience you must have when cooking this soup!");
        recipe.setIngredients(ingredients(recipe,
                200, "g", "Broccoli",
                150, "g", "Spinach",
                100, "g", "Green peas",
                1, "unit", "Onion",
                800, "ml", "Vegetable broth",
                1, "tbsp", "Olive oil",
                1, "tsp", "Salt"));

        return recipe;
    }


    // Recipe 4 — Ewok Forest Roast
    // Inspired by the Ewok celebrations on Endor
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
        recipe.setSteps("1. Preheat oven to 180°C.\n2. Season the roast generously with herbs and spices.\n3. Sear all sides in a hot pan with oil.\n4. Place in roasting pan with root vegetables.\n5. Roast for 90 minutes, basting every 30 minutes.\n6. Rest for 15 minutes before carving.");
        recipe.setTip("Tip: Celebrate like the Rebellion just won the war!");
        recipe.setIngredients(ingredients(recipe,
                1500, "g", "Pork roast",
                3, "units", "Carrots",
                4, "units", "Potatoes",
                2, "units", "Onions",
                4, "cloves", "Garlic",
                2, "tbsp", "Fresh rosemary",
                2, "tbsp", "Olive oil",
                1, "tsp", "Salt",
                1, "tsp", "Black pepper"));

        return recipe;
    }

    // Recipe 5 — Mandalorian Brisket
    // Inspired by the hearty cuisine of the Mandalorians
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
        recipe.setSteps("1. Rub brisket with spice mixture and refrigerate overnight.\n2. Preheat oven to 150°C.\n3. Sear brisket in a large Dutch oven until browned.\n4. Add onions, garlic and broth.\n5. Cover and cook in oven for 3 hours.\n6. Slice against the grain and serve with cooking juices.");
        recipe.setTip("Tip: This is the way... to cook a perfect brisket!");
        recipe.setIngredients(ingredients(recipe,
                1200, "g", "Beef brisket",
                2, "tbsp", "Smoked paprika",
                1, "tbsp", "Cumin",
                1, "tbsp", "Garlic powder",
                2, "units", "Onions",
                300, "ml", "Beef broth",
                2, "tbsp", "Olive oil",
                1, "tsp", "Salt"));

        return recipe;
    }

    // Recipe 6 — Mediterranean Chicken Bowl
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
        recipe.setSteps("1. Cook rice according to package instructions.\n2. Season chicken with Mediterranean spices and grill for 6 minutes each side.\n3. Slice chicken and arrange over rice.\n4. Add cucumber, tomatoes and olives.\n5. Crumble feta on top.\n6. Drizzle with olive oil and lemon juice.");
        recipe.setTip("Tip: Add olives and lemon juice for extra flavour!");
        recipe.setIngredients(ingredients(recipe,
                300, "g", "Chicken breast",
                150, "g", "Rice",
                100, "g", "Feta cheese",
                1, "unit", "Cucumber",
                2, "units", "Tomatoes",
                50, "g", "Olives",
                2, "tbsp", "Olive oil",
                1, "unit", "Lemon"));

        return recipe;
    }

    // Recipe 7 — Classic Pancakes
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
        recipe.setSteps("1. Mix flour, sugar, baking powder and salt in a bowl.\n2. Whisk milk, eggs and melted butter separately.\n3. Combine wet and dry ingredients until just mixed.\n4. Heat a non-stick pan over medium heat.\n5. Pour 1/4 cup batter per pancake.\n6. Cook until bubbles form, flip and cook 1 more minute.");
        recipe.setTip("Tip: Serve with maple syrup and fresh berries.");
        recipe.setIngredients(ingredients(recipe,
                200, "g", "Flour",
                2, "units", "Eggs",
                250, "ml", "Milk",
                2, "tbsp", "Butter",
                2, "tbsp", "Sugar",
                1, "tsp", "Baking powder",
                0.5, "tsp", "Salt"));

        return recipe;
    }

    // Recipe 8 — Avocado Toast
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
        recipe.setSteps("1. Toast the bread until golden and crispy.\n2. Halve the avocado and remove the pit.\n3. Scoop avocado into a bowl and mash with a fork.\n4. Season with lemon juice, salt and pepper.\n5. Spread avocado mixture onto toast.\n6. Top with chili flakes and serve immediately.");
        recipe.setTip("Tip: Add chili flakes for a spicy kick.");
        recipe.setIngredients(ingredients(recipe,
                2, "slices", "Sourdough bread",
                1, "unit", "Ripe avocado",
                1, "tbsp", "Lemon juice",
                0.5, "tsp", "Chili flakes",
                0.5, "tsp", "Salt",
                0.25, "tsp", "Black pepper"));

        return recipe;
    }

    // Recipe 9 — Beef Tacos
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
        recipe.setSteps("1. Brown ground beef in a pan over medium-high heat.\n2. Add taco seasoning and water, simmer for 5 minutes.\n3. Warm taco shells in the oven for 3 minutes.\n4. Fill shells with beef mixture.\n5. Top with lettuce, cheese, tomato and sour cream.\n6. Serve immediately with lime wedges.");
        recipe.setTip("Tip: Top with fresh cilantro and lime.");
        recipe.setIngredients(ingredients(recipe,
                400, "g", "Ground beef",
                6, "units", "Taco shells",
                1, "packet", "Taco seasoning",
                100, "g", "Shredded lettuce",
                100, "g", "Grated cheese",
                2, "units", "Tomatoes",
                100, "ml", "Sour cream"));

        return recipe;
    }

    // Recipe 10 — Veggie Omelette
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
        recipe.setSteps("1. Whisk eggs with salt and pepper in a bowl.\n2. Heat butter in a non-stick pan over medium heat.\n3. Sauté mushrooms, spinach and bell pepper for 2 minutes.\n4. Pour egg mixture over vegetables.\n5. Cook until edges set, then fold omelette in half.\n6. Serve immediately.");
        recipe.setTip("Tip: Use mushrooms and spinach for extra nutrition.");
        recipe.setIngredients(ingredients(recipe,
                3, "units", "Eggs",
                80, "g", "Mushrooms",
                50, "g", "Spinach",
                0.5, "unit", "Bell pepper",
                1, "tbsp", "Butter",
                0.5, "tsp", "Salt",
                0.25, "tsp", "Black pepper"));

        return recipe;
    }

    // Recipe 11 — Salmon with Vegetables
    private static Recipe createSalmonWithVegetables() {
        Recipe recipe = new Recipe();
        recipe.setName("Salmon with Vegetables");
        recipe.setDescription("Oven-baked salmon served with roasted vegetables.");
        recipe.setCategory("OMNIVORE");
        recipe.setPreparationTime(40);
        recipe.setServings(2);
        recipe.setCalories(510);
        recipe.setProtein(42.0);
        recipe.setCarbs(18.0);
        recipe.setFat(28.0);
        recipe.setDifficultyLevel(DifficultyLevel.MEDIUM);
        recipe.setMealType(MealType.DINNER);
        recipe.setDietTypes(Set.of(DietType.OMNIVORE, DietType.GLUTEN_FREE));
        recipe.setSteps("1. Preheat oven to 200°C.\n2. Place salmon fillets on a baking tray.\n3. Season with lemon, dill, salt and pepper.\n4. Arrange vegetables around the salmon.\n5. Drizzle everything with olive oil.\n6. Bake for 20-25 minutes until salmon flakes easily.");
        recipe.setTip("Tip: Lemon and dill pair perfectly with salmon.");
        recipe.setIngredients(ingredients(recipe,
                300, "g", "Salmon fillets",
                200, "g", "Broccoli",
                2, "units", "Carrots",
                1, "unit", "Lemon",
                2, "tbsp", "Fresh dill",
                2, "tbsp", "Olive oil",
                1, "tsp", "Salt"));

        return recipe;
    }

    // Recipe 12 — Creamy Mushroom Pasta
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
        recipe.setSteps("1. Cook pasta according to package instructions.\n2. Sauté sliced mushrooms in butter until golden.\n3. Add garlic and cook for 1 minute.\n4. Pour in cream and simmer for 5 minutes.\n5. Toss pasta with the sauce.\n6. Serve topped with parmesan and fresh parsley.");
        recipe.setTip("Tip: Use fresh parsley before serving.");
        recipe.setIngredients(ingredients(recipe,
                300, "g", "Pasta",
                250, "g", "Mushrooms",
                200, "ml", "Heavy cream",
                2, "cloves", "Garlic",
                2, "tbsp", "Butter",
                50, "g", "Parmesan cheese",
                1, "tbsp", "Fresh parsley"));

        return recipe;
    }

    // Recipe 13 — Chicken Caesar Salad
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
        recipe.setSteps("1. Season chicken breast and grill for 6 minutes each side.\n2. Let chicken rest, then slice into strips.\n3. Tear romaine lettuce into a large bowl.\n4. Add croutons and parmesan shavings.\n5. Drizzle Caesar dressing over the salad.\n6. Top with sliced chicken and serve.");
        recipe.setTip("Tip: Add homemade croutons for extra crunch.");
        recipe.setIngredients(ingredients(recipe,
                250, "g", "Chicken breast",
                1, "head", "Romaine lettuce",
                50, "g", "Parmesan cheese",
                80, "ml", "Caesar dressing",
                50, "g", "Croutons",
                1, "tsp", "Black pepper"));

        return recipe;
    }

    // Recipe 14 — Lentil Curry
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
        recipe.setSteps("1. Sauté onion and garlic in oil until softened.\n2. Add curry powder, cumin and turmeric, cook 1 minute.\n3. Add rinsed lentils and coconut milk.\n4. Pour in vegetable broth and bring to a boil.\n5. Simmer for 25 minutes until lentils are tender.\n6. Season with salt and serve with rice.");
        recipe.setTip("Tip: Serve with basmati rice.");
        recipe.setIngredients(ingredients(recipe,
                250, "g", "Red lentils",
                400, "ml", "Coconut milk",
                400, "ml", "Vegetable broth",
                1, "unit", "Onion",
                3, "cloves", "Garlic",
                2, "tbsp", "Curry powder",
                1, "tsp", "Cumin",
                1, "tsp", "Turmeric"));

        return recipe;
    }

    // Recipe 15 — Banana Smoothie
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
        recipe.setSteps("1. Peel the banana and break into chunks.\n2. Add banana, milk and yogurt to blender.\n3. Add honey if desired.\n4. Blend until smooth and creamy.\n5. Pour into a glass and serve immediately.");
        recipe.setTip("Tip: Add peanut butter for extra protein.");
        recipe.setIngredients(ingredients(recipe,
                2, "units", "Ripe bananas",
                250, "ml", "Milk",
                100, "g", "Greek yogurt",
                1, "tbsp", "Honey"));

        return recipe;
    }

    // Recipe 16 — Grilled Turkey Burger
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
        recipe.setSteps("1. Mix ground turkey with onion, garlic and seasoning.\n2. Form into 2 patties.\n3. Grill over medium heat for 6 minutes per side.\n4. Toast buns on the grill for 1 minute.\n5. Assemble burgers with lettuce, tomato and condiments.\n6. Serve with a side salad.");
        recipe.setTip("Tip: Add avocado slices for extra flavour.");
        recipe.setIngredients(ingredients(recipe,
                400, "g", "Ground turkey",
                2, "units", "Burger buns",
                0.5, "unit", "Onion",
                2, "cloves", "Garlic",
                1, "tsp", "Salt",
                1, "tsp", "Black pepper",
                2, "leaves", "Lettuce",
                1, "unit", "Tomato"));

        return recipe;
    }

    // Recipe 17 — Greek Yogurt Parfait
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
        recipe.setSteps("1. Spoon half the yogurt into a glass.\n2. Add a layer of granola.\n3. Add a layer of fresh berries.\n4. Repeat layers with remaining yogurt and fruit.\n5. Top with granola and a drizzle of honey.\n6. Serve immediately or refrigerate for up to 2 hours.");
        recipe.setTip("Tip: Use honey for natural sweetness.");
        recipe.setIngredients(ingredients(recipe,
                200, "g", "Greek yogurt",
                50, "g", "Granola",
                100, "g", "Mixed berries",
                1, "tbsp", "Honey"));

        return recipe;
    }

    // Recipe 18 — Shrimp Stir Fry
    private static Recipe createShrimpStirFry() {
        Recipe recipe = new Recipe();
        recipe.setName("Shrimp Stir Fry");
        recipe.setDescription("Quick shrimp stir fry with vegetables and soy sauce.");
        recipe.setCategory("OMNIVORE");
        recipe.setPreparationTime(25);
        recipe.setServings(2);
        recipe.setCalories(390);
        recipe.setProtein(30.0);
        recipe.setCarbs(26.0);
        recipe.setFat(14.0);
        recipe.setDifficultyLevel(DifficultyLevel.MEDIUM);
        recipe.setMealType(MealType.DINNER);
        recipe.setDietTypes(Set.of(DietType.OMNIVORE));
        recipe.setSteps("1. Heat oil in a wok over high heat.\n2. Add garlic and ginger, stir-fry for 30 seconds.\n3. Add shrimp and cook for 2-3 minutes until pink.\n4. Add vegetables and stir-fry for 3 minutes.\n5. Pour in soy sauce and sesame oil.\n6. Serve over jasmine rice.");
        recipe.setTip("Tip: Serve with jasmine rice.");
        recipe.setIngredients(ingredients(recipe,
                300, "g", "Shrimp",
                200, "g", "Mixed vegetables",
                3, "cloves", "Garlic",
                1, "tsp", "Fresh ginger",
                3, "tbsp", "Soy sauce",
                1, "tbsp", "Sesame oil",
                1, "tbsp", "Vegetable oil"));

        return recipe;
    }

    // Recipe 19 — Tomato Basil Soup
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
        recipe.setSteps("1. Sauté onion and garlic in olive oil until soft.\n2. Add canned tomatoes and vegetable broth.\n3. Bring to a boil and simmer for 20 minutes.\n4. Add fresh basil and blend until smooth.\n5. Season with salt, pepper and a pinch of sugar.\n6. Serve hot with crusty bread.");
        recipe.setTip("Tip: Serve with toasted bread.");
        recipe.setIngredients(ingredients(recipe,
                800, "g", "Canned tomatoes",
                1, "unit", "Onion",
                3, "cloves", "Garlic",
                500, "ml", "Vegetable broth",
                20, "g", "Fresh basil",
                2, "tbsp", "Olive oil",
                1, "tsp", "Salt"));

        return recipe;
    }

    // Recipe 20 — Chicken Alfredo
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
        recipe.setSteps("1. Cook fettuccine according to package instructions.\n2. Grill chicken breast until cooked through, then slice.\n3. Melt butter in a pan, add garlic and cream.\n4. Simmer sauce for 5 minutes and add parmesan.\n5. Toss pasta with sauce and chicken.\n6. Serve with freshly grated parmesan.");
        recipe.setTip("Tip: Use freshly grated parmesan cheese.");
        recipe.setIngredients(ingredients(recipe,
                400, "g", "Fettuccine",
                400, "g", "Chicken breast",
                300, "ml", "Heavy cream",
                100, "g", "Parmesan cheese",
                3, "cloves", "Garlic",
                2, "tbsp", "Butter",
                1, "tsp", "Salt"));

        return recipe;
    }

    // Recipe 21 — Quinoa Salad
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
        recipe.setSteps("1. Cook quinoa in water for 15 minutes, then cool.\n2. Dice cucumber and tomatoes.\n3. Whisk together lemon juice, olive oil, salt and pepper.\n4. Combine quinoa and vegetables in a bowl.\n5. Pour dressing over and toss well.\n6. Garnish with fresh mint and serve.");
        recipe.setTip("Tip: Add mint leaves for freshness.");
        recipe.setIngredients(ingredients(recipe,
                180, "g", "Quinoa",
                1, "unit", "Cucumber",
                2, "units", "Tomatoes",
                1, "unit", "Lemon",
                3, "tbsp", "Olive oil",
                10, "g", "Fresh mint",
                1, "tsp", "Salt"));

        return recipe;
    }

    // Recipe 22 — BBQ Chicken Pizza
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
        recipe.setSteps("1. Preheat oven to 220°C.\n2. Roll out pizza dough on a floured surface.\n3. Spread BBQ sauce over the dough.\n4. Top with cooked chicken, red onion and mozzarella.\n5. Bake for 15-18 minutes until crust is golden.\n6. Garnish with fresh coriander and serve.");
        recipe.setTip("Tip: Use red onions for extra flavour.");
        recipe.setIngredients(ingredients(recipe,
                300, "g", "Pizza dough",
                200, "g", "Cooked chicken",
                150, "ml", "BBQ sauce",
                200, "g", "Mozzarella cheese",
                0.5, "unit", "Red onion",
                10, "g", "Fresh coriander"));

        return recipe;
    }

    // Recipe 23 — Spinach Risotto
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
        recipe.setSteps("1. Heat broth in a saucepan and keep warm.\n2. Sauté onion in butter until translucent.\n3. Add arborio rice and toast for 2 minutes.\n4. Add broth ladle by ladle, stirring constantly.\n5. Stir in spinach and parmesan at the end.\n6. Season and serve immediately.");
        recipe.setTip("Tip: Stir constantly for the perfect texture.");
        recipe.setIngredients(ingredients(recipe,
                250, "g", "Arborio rice",
                150, "g", "Spinach",
                1000, "ml", "Vegetable broth",
                1, "unit", "Onion",
                2, "tbsp", "Butter",
                60, "g", "Parmesan cheese",
                1, "tsp", "Salt"));

        return recipe;
    }

    // Recipe 24 — Baked Sweet Potatoes
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
        recipe.setSteps("1. Preheat oven to 200°C.\n2. Wash and scrub sweet potatoes.\n3. Cut into wedges and place on a baking tray.\n4. Drizzle with olive oil and sprinkle with herbs.\n5. Bake for 25-30 minutes until tender and caramelised.\n6. Sprinkle with cinnamon and serve.");
        recipe.setTip("Tip: Sprinkle cinnamon for extra sweetness.");
        recipe.setIngredients(ingredients(recipe,
                2, "units", "Sweet potatoes",
                2, "tbsp", "Olive oil",
                1, "tsp", "Cinnamon",
                1, "tsp", "Dried thyme",
                0.5, "tsp", "Salt"));

        return recipe;
    }

    // Recipe 25 — Tuna Sandwich
    private static Recipe createTunaSandwich() {
        Recipe recipe = new Recipe();
        recipe.setName("Tuna Sandwich");
        recipe.setDescription("Classic tuna sandwich with lettuce and tomato.");
        recipe.setCategory("OMNIVORE");
        recipe.setPreparationTime(10);
        recipe.setServings(1);
        recipe.setCalories(330);
        recipe.setProtein(22.0);
        recipe.setCarbs(28.0);
        recipe.setFat(12.0);
        recipe.setDifficultyLevel(DifficultyLevel.EASY);
        recipe.setMealType(MealType.LUNCH);
        recipe.setDietTypes(Set.of(DietType.OMNIVORE));
        recipe.setSteps("1. Drain tuna and mix with mayonnaise.\n2. Add diced onion, celery, salt and pepper.\n3. Toast the bread until golden.\n4. Layer lettuce and tomato on one slice.\n5. Spread tuna mixture on top.\n6. Close the sandwich and serve.");
        recipe.setTip("Tip: Toast the bread for better texture.");
        recipe.setIngredients(ingredients(recipe,
                160, "g", "Canned tuna",
                2, "slices", "Bread",
                2, "tbsp", "Mayonnaise",
                2, "leaves", "Lettuce",
                1, "unit", "Tomato",
                0.25, "unit", "Onion",
                0.5, "tsp", "Salt"));

        return recipe;
    }

    // Recipe 26 — Chocolate Brownie
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
        recipe.setSteps("1. Preheat oven to 180°C and grease a baking tin.\n2. Melt butter and chocolate together.\n3. Whisk in sugar, then eggs one at a time.\n4. Fold in flour and cocoa powder.\n5. Pour into tin and bake for 25-30 minutes.\n6. Cool before cutting into squares.");
        recipe.setTip("Tip: Serve warm with vanilla ice cream.");
        recipe.setIngredients(ingredients(recipe,
                150, "g", "Dark chocolate",
                120, "g", "Butter",
                200, "g", "Sugar",
                3, "units", "Eggs",
                80, "g", "Flour",
                30, "g", "Cocoa powder",
                0.5, "tsp", "Salt"));

        return recipe;
    }

    // Recipe 27 — Strawberry Cheesecake
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
        recipe.setSteps("1. Mix crushed biscuits with melted butter and press into tin.\n2. Beat cream cheese with sugar and vanilla until smooth.\n3. Fold in whipped cream.\n4. Pour filling over biscuit base.\n5. Refrigerate for at least 4 hours.\n6. Top with fresh strawberries before serving.");
        recipe.setTip("Tip: Chill for at least 4 hours before serving.");
        recipe.setIngredients(ingredients(recipe,
                500, "g", "Cream cheese",
                200, "g", "Digestive biscuits",
                100, "g", "Butter",
                150, "g", "Sugar",
                200, "ml", "Heavy cream",
                300, "g", "Fresh strawberries",
                1, "tsp", "Vanilla extract"));

        return recipe;
    }

    // Recipe 28 — Banana Muffins
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
        recipe.setSteps("1. Preheat oven to 180°C and line muffin tin.\n2. Mash ripe bananas in a bowl.\n3. Mix in melted butter, sugar and egg.\n4. Fold in flour and baking soda.\n5. Divide batter into muffin cups.\n6. Bake for 20-25 minutes until golden.");
        recipe.setTip("Tip: Add chocolate chips for extra sweetness.");
        recipe.setIngredients(ingredients(recipe,
                3, "units", "Ripe bananas",
                200, "g", "Flour",
                100, "g", "Sugar",
                60, "g", "Butter",
                1, "unit", "Egg",
                1, "tsp", "Baking soda",
                0.5, "tsp", "Salt"));

        return recipe;
    }

    // Recipe 29 — Protein Energy Balls
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
        recipe.setSteps("1. Mix oats, peanut butter, honey and chocolate chips in a bowl.\n2. Stir until well combined.\n3. Refrigerate for 30 minutes.\n4. Roll mixture into small balls.\n5. Place on a baking sheet.\n6. Refrigerate for another 30 minutes before serving.");
        recipe.setTip("Tip: Keep refrigerated for better texture.");
        recipe.setIngredients(ingredients(recipe,
                200, "g", "Rolled oats",
                120, "g", "Peanut butter",
                60, "ml", "Honey",
                60, "g", "Chocolate chips",
                30, "g", "Chia seeds"));

        return recipe;
    }

    // Recipe 30 — Yogurt Fruit Bowl
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
        recipe.setSteps("1. Spoon Greek yogurt into a bowl.\n2. Wash and prepare fresh fruits.\n3. Arrange fruits on top of the yogurt.\n4. Drizzle with honey.\n5. Top with granola if desired.\n6. Serve immediately.");
        recipe.setTip("Tip: Top with granola for extra crunch.");
        recipe.setIngredients(ingredients(recipe,
                200, "g", "Greek yogurt",
                100, "g", "Mixed fresh fruits",
                1, "tbsp", "Honey",
                30, "g", "Granola"));

        return recipe;
    }

    // Recipe 31 — Vanilla Ice Cream Sundae
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
        recipe.setSteps("1. Scoop vanilla ice cream into serving glasses.\n2. Drizzle with chocolate syrup.\n3. Add whipped cream on top.\n4. Sprinkle with chopped nuts.\n5. Add a cherry on top.\n6. Serve immediately.");
        recipe.setTip("Tip: Add cherries on top for a classic touch.");
        recipe.setIngredients(ingredients(recipe,
                4, "scoops", "Vanilla ice cream",
                3, "tbsp", "Chocolate syrup",
                50, "ml", "Whipped cream",
                30, "g", "Chopped nuts",
                2, "units", "Maraschino cherries"));

        return recipe;
    }

    // Recipe 32 — Hummus with Veggies
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
        recipe.setSteps("1. Blend chickpeas, tahini, lemon juice and garlic until smooth.\n2. Add olive oil and blend again.\n3. Season with salt and cumin.\n4. Transfer to a serving bowl.\n5. Drizzle with olive oil and sprinkle paprika.\n6. Serve with carrot and cucumber sticks.");
        recipe.setTip("Tip: Paprika and olive oil make great toppings.");
        recipe.setIngredients(ingredients(recipe,
                400, "g", "Canned chickpeas",
                3, "tbsp", "Tahini",
                1, "unit", "Lemon",
                2, "cloves", "Garlic",
                3, "tbsp", "Olive oil",
                2, "units", "Carrots",
                1, "unit", "Cucumber"));

        return recipe;
    }

    // Recipe 33 — Apple Pie
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
        recipe.setSteps("1. Preheat oven to 190°C.\n2. Prepare pie crust and line tin with half the pastry.\n3. Peel, core and slice apples, mix with sugar and cinnamon.\n4. Fill pie crust with apple mixture.\n5. Cover with remaining pastry and crimp edges.\n6. Bake for 45-50 minutes until golden.");
        recipe.setTip("Tip: Serve warm with whipped cream.");
        recipe.setIngredients(ingredients(recipe,
                6, "units", "Apples",
                300, "g", "Pie crust pastry",
                100, "g", "Sugar",
                2, "tsp", "Cinnamon",
                1, "tbsp", "Butter",
                1, "tbsp", "Lemon juice"));

        return recipe;
    }

    // Recipe 34 — Roasted Chickpeas
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
        recipe.setSteps("1. Preheat oven to 200°C.\n2. Drain and rinse chickpeas, pat dry with paper towel.\n3. Toss with olive oil and spices.\n4. Spread on a baking tray in a single layer.\n5. Roast for 25-30 minutes, shaking halfway through.\n6. Cool before serving for maximum crunch.");
        recipe.setTip("Tip: Bake until extra crispy.");
        recipe.setIngredients(ingredients(recipe,
                400, "g", "Canned chickpeas",
                2, "tbsp", "Olive oil",
                1, "tsp", "Smoked paprika",
                1, "tsp", "Cumin",
                0.5, "tsp", "Cayenne pepper",
                1, "tsp", "Salt"));

        return recipe;
    }

    // Recipe 35 — Chocolate Chip Cookies
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
        recipe.setSteps("1. Preheat oven to 175°C.\n2. Cream butter and sugars until fluffy.\n3. Beat in eggs and vanilla extract.\n4. Mix in flour, baking soda and salt.\n5. Fold in chocolate chips.\n6. Drop spoonfuls onto baking sheet and bake for 10-12 minutes.");
        recipe.setTip("Tip: Use dark chocolate for richer flavour.");
        recipe.setIngredients(ingredients(recipe,
                225, "g", "Butter",
                200, "g", "Sugar",
                2, "units", "Eggs",
                280, "g", "Flour",
                200, "g", "Chocolate chips",
                1, "tsp", "Vanilla extract",
                1, "tsp", "Baking soda"));

        return recipe;
    }

    // Recipe 36 — Peanut Butter Toast
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
        recipe.setSteps("1. Toast the bread until golden and crispy.\n2. Spread peanut butter generously on toast.\n3. Slice banana into rounds.\n4. Arrange banana slices on top.\n5. Sprinkle with cinnamon.\n6. Serve immediately.");
        recipe.setTip("Tip: Sprinkle cinnamon on top.");
        recipe.setIngredients(ingredients(recipe,
                2, "slices", "Whole wheat bread",
                2, "tbsp", "Peanut butter",
                1, "unit", "Banana",
                0.5, "tsp", "Cinnamon"));

        return recipe;
    }

    // Recipe 37 — Lemon Tart
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
        recipe.setSteps("1. Make shortcrust pastry and blind bake for 15 minutes at 180°C.\n2. Whisk eggs, sugar, lemon juice and zest together.\n3. Melt butter and stir into egg mixture.\n4. Pour lemon curd into pastry case.\n5. Bake for 20 minutes until just set.\n6. Cool completely and dust with powdered sugar.");
        recipe.setTip("Tip: Dust with powdered sugar before serving.");
        recipe.setIngredients(ingredients(recipe,
                200, "g", "Shortcrust pastry",
                4, "units", "Lemons",
                4, "units", "Eggs",
                150, "g", "Sugar",
                80, "g", "Butter",
                2, "tbsp", "Powdered sugar"));

        return recipe;
    }

    // Recipe 38 — Mixed Nuts Bowl
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
        recipe.setSteps("1. Measure out almonds, walnuts and cashews.\n2. Mix together in a bowl.\n3. Add a pinch of sea salt if desired.\n4. Portion into serving bowls.\n5. Serve as a snack or alongside a meal.");
        recipe.setTip("Tip: Store in airtight containers.");
        recipe.setIngredients(ingredients(recipe,
                50, "g", "Almonds",
                50, "g", "Walnuts",
                50, "g", "Cashews",
                0.5, "tsp", "Sea salt"));

        return recipe;
    }

    // Recipe 39 — Rice Pudding
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
        recipe.setSteps("1. Combine rice and milk in a saucepan.\n2. Bring to a gentle simmer over medium heat.\n3. Add sugar, vanilla and cinnamon.\n4. Cook for 35 minutes, stirring frequently.\n5. Remove from heat when creamy.\n6. Serve warm or chilled.");
        recipe.setTip("Tip: Serve chilled or warm.");
        recipe.setIngredients(ingredients(recipe,
                150, "g", "Short grain rice",
                800, "ml", "Milk",
                60, "g", "Sugar",
                1, "tsp", "Vanilla extract",
                1, "tsp", "Cinnamon",
                30, "g", "Butter"));

        return recipe;
    }

    // Recipe 40 — Cheese Crackers Plate
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
        recipe.setSteps("1. Arrange crackers on a serving plate.\n2. Slice cheese into thin pieces.\n3. Place cheese slices on crackers.\n4. Add grapes or olives on the side.\n5. Garnish with fresh herbs if desired.\n6. Serve immediately.");
        recipe.setTip("Tip: Pair with grapes or olives.");
        recipe.setIngredients(ingredients(recipe,
                150, "g", "Assorted cheese",
                100, "g", "Crackers",
                100, "g", "Grapes",
                30, "g", "Olives"));
        return recipe;
    }
}