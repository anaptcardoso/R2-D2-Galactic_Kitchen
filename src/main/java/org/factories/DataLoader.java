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

// @Component — o Spring gere esta classe automaticamente
// ApplicationListener<ContextRefreshedEvent> — este código corre
// automaticamente quando a app arranca!
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    // Precisamos dos repositories para guardar os dados na BD
    private final RecipeRepository recipeRepository;
    private final UserProfileRepository userProfileRepository;

    // Constructor injection — o Spring injeta os repositories automaticamente
    public DataLoader(RecipeRepository recipeRepository,
                      UserProfileRepository userProfileRepository) {
        this.recipeRepository = recipeRepository;
        this.userProfileRepository = userProfileRepository;
    }


    // onApplicationEvent — corre quando a app arranca
    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Só carrega receitas se a BD estiver vazia
        // Assim não duplica dados cada vez que a app reinicia!
        if (recipeRepository.count() == 0) {
            loadRecipes();
        }

        // Só carrega utilizadores se não houver nenhum ainda
        // Depois de te registares, os teus dados ficam ao lado
        // do Luke, Leia e Han!
        if (userProfileRepository.count() == 0) {
            loadUsers();
        }
    }


    // loadRecipes — guarda as receitas Star Wars na BD
    private void loadRecipes() {
        List<Recipe> recipes = RecipeFactory.createSampleRecipes();
        recipeRepository.saveAll(recipes);
        System.out.println( recipes.size() + " Star Wars recipes loaded!");
    }


    // loadUsers — guarda os utilizadores de demonstração na BD
    private void loadUsers() {
        List<UserProfile> users = UserFactory.createSampleUsers();
        userProfileRepository.saveAll(users);
        System.out.println( users.size() + " Rebel Alliance users loaded!");
    }
}