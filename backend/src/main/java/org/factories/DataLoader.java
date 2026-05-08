package org.factories;

import org.model.entity.Recipe;
import org.model.entity.UserProfile;
import org.persistence.daos.RecipeRepository;
import org.persistence.daos.UserProfileRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// @Component — Spring manages this class automatically
// ApplicationListener<ContextRefreshedEvent> — this code runs
// automatically when the app starts!
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    // We need the repositories to save the data in the database
    private final RecipeRepository recipeRepository;
    private final UserProfileRepository userProfileRepository;

    // Constructor injection — Spring injects the repositories automatically
    public DataLoader(RecipeRepository recipeRepository,
                      UserProfileRepository userProfileRepository) {
        this.recipeRepository = recipeRepository;
        this.userProfileRepository = userProfileRepository;
    }


    // onApplicationEvent — runs when the app starts
    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Only loads recipes if the database is empty
        // This prevents duplicate data every time the app restarts!
        if (recipeRepository.count() == 0) {
            loadRecipes();
        }

        // Only loads users if there are none yet
        // After you register, your data will be next to
        // Luke, Leia and Han!
        if (userProfileRepository.count() == 0) {
            loadUsers();
        }
    }


    // loadRecipes — saves the Star Wars recipes in the database
    private void loadRecipes() {
        List<Recipe> recipes = RecipeFactory.createSampleRecipes();
        recipeRepository.saveAll(recipes);
        System.out.println( recipes.size() + " Star Wars recipes loaded!");
    }


    // loadUsers — saves the demo users in the database
    private void loadUsers() {
        List<UserProfile> users = UserFactory.createSampleUsers();
        userProfileRepository.saveAll(users);
        System.out.println( users.size() + " Rebel Alliance users loaded!");
    }
}