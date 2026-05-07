package org.factories;

import org.model.entity.UserProfile;
import org.model.enums.DietType;
import org.model.valueObject.NutritionProfile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class UserFactory {

    // Cria uma lista de utilizadores de exemplo
    // Estes utilizadores são usados para popular a base de dados quando a app arranca
    public static List<UserProfile> createSampleUsers() {
        return List.of(
                createLuke(),
                createLeia(),
                createHan(),
                createAna(),
                createInes(),
                createPedro(),
                createYasmin()

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
        user.setNutritionProfile(new NutritionProfile(
                75.0, 172.0, "Become a Jedi Master", "very active",
                Set.of(DietType.OMNIVORE), null));
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
        user.setNutritionProfile(new NutritionProfile(
                58.0, 150.0, "Maintain fitness for the Rebellion", "active",
                Set.of(DietType.VEGETARIAN), "gluten"));
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
        user.setNutritionProfile(new NutritionProfile(
                85.0, 180.0, "Survive another day", "moderate",
                Set.of(DietType.OMNIVORE), null));
        user.setBio("Never tell me the odds!");
        return user;
    }

    // Utilizador 4 — Ana
    //
    private static UserProfile createAna() {
        UserProfile user = new UserProfile();
        user.setFirstName("Ana");
        user.setLastName("Cardoso");
        user.setEmail("anaptcardoso11@gmail.com");
        user.setPhone("911164088");
        user.setDateOfBirth(LocalDate.of(95,10 , 11));
        user.setCountry("Portugal");
        user.setNutritionProfile(new NutritionProfile(
                60.0, 165.0, "Be a programmer", "moderate",
                Set.of(DietType.OMNIVORE), null));
        user.setBio("https://github.com/anaptcardoso");
        return user;
    }

    // Utilizador 5 — Ines
    //
    private static UserProfile createInes() {
        UserProfile user = new UserProfile();
        user.setFirstName("Ines");
        user.setLastName("Azevedo");
        user.setEmail("ines@mail.com");//altera
        user.setPhone("912097615");
        user.setDateOfBirth(LocalDate.of(29, 7, 13));//altera
        user.setCountry("Portugal");
        user.setNutritionProfile(new NutritionProfile(
                85.0, 180.0, "Survive another day", "moderate",//altera
                Set.of(DietType.OMNIVORE), null));
        user.setBio("https://github.com/inesazevedo23");
        return user;
    }

    // Utilizador 6 — Pedro
    //
    private static UserProfile createPedro() {
        UserProfile user = new UserProfile();
        user.setFirstName("Pedro");
        user.setLastName("Saldanha");
        user.setEmail("pedro@mail.com");//altera
        user.setPhone("920176304");
        user.setDateOfBirth(LocalDate.of(29, 7, 13));//altera
        user.setCountry("Netherlands");
        user.setNutritionProfile(new NutritionProfile(
                85.0, 180.0, "Survive another day", "moderate",//altera
                Set.of(DietType.OMNIVORE), null));
        user.setBio("https://github.com/SaldanhaIgor");
        return user;
    }

    // Utilizador 7 — Yasmin
    // Contrabandista — come o que aparecer!
    private static UserProfile createYasmin() {
        UserProfile user = new UserProfile();
        user.setFirstName("Yasmin");
        user.setLastName("Natasha");
        user.setEmail("yasmin@email.com");//altera
        user.setPhone("912566280");
        user.setDateOfBirth(LocalDate.of(29, 7, 13));//altera
        user.setCountry("Portugal");
        user.setNutritionProfile(new NutritionProfile(
                85.0, 180.0, "Survive another day", "moderate",//altera
                Set.of(DietType.OMNIVORE), null));
        user.setBio("https://github.com/yasminnatasha17-blip");
        return user;
    }
}