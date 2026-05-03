package org.factories;

import org.model.entity.UserProfile;
import org.model.enums.DietType;

import java.time.LocalDate;
import java.util.List;

public class UserFactory {

    // Cria uma lista de utilizadores de exemplo
    // Estes utilizadores são usados para popular a base de dados quando a app arranca
    public static List<UserProfile> createSampleUsers() {
        return List.of(
                createLuke(),
                createLeia(),
                createHan()
        );
    }


    // Utilizador 1 — Luke Skywalker
    // Jedi em treino — dieta equilibrada
    private static UserProfile createLuke() {
        UserProfile user = new UserProfile();
        user.setFirstName("Luke");
        user.setLastName("Skywalker");
        user.setEmail("luke@rebellion.com");
        user.setPhone("123456789");
        user.setDateOfBirth(LocalDate.of(19, 5, 25));
        user.setCountry("Tatooine");
        user.setWeight(75.0);
        user.setHeight(1.72);
        user.setGoal("Become a Jedi Master");
        user.setActivityLevel("very active");
        user.setDailyCalories(2800);
        user.setDietType(DietType.OMNIVORE);
        user.setAllergies(List.of());
        user.setBio("I am a Jedi, like my father before me.");
        return user;
    }


    // Utilizador 2 — Leia Organa
    // Princesa e líder da Rebelião — dieta saudável
    private static UserProfile createLeia() {
        UserProfile user = new UserProfile();
        user.setFirstName("Leia");
        user.setLastName("Organa");
        user.setEmail("leia@rebellion.com");
        user.setPhone("987654321");
        user.setDateOfBirth(LocalDate.of(19, 5, 25));
        user.setCountry("Alderaan");
        user.setWeight(58.0);
        user.setHeight(1.50);
        user.setGoal("Maintain fitness for the Rebellion");
        user.setActivityLevel("active");
        user.setDailyCalories(2000);
        user.setDietType(DietType.VEGETARIAN);
        user.setAllergies(List.of("gluten"));
        user.setBio("Help me, Obi-Wan Kenobi. You're my only hope.");
        return user;
    }


    // Utilizador 3 — Han Solo
    // Contrabandista — come o que aparecer!
    private static UserProfile createHan() {
        UserProfile user = new UserProfile();
        user.setFirstName("Han");
        user.setLastName("Solo");
        user.setEmail("han@millenniumfalcon.com");
        user.setPhone("555123456");
        user.setDateOfBirth(LocalDate.of(29, 7, 13));
        user.setCountry("Corellia");
        user.setWeight(85.0);
        user.setHeight(1.80);
        user.setGoal("Survive another day");
        user.setActivityLevel("moderate");
        user.setDailyCalories(2500);
        user.setDietType(DietType.OMNIVORE);
        user.setAllergies(List.of());
        user.setBio("Never tell me the odds!");
        return user;
    }
}