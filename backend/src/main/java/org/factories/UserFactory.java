package org.factories;

import org.model.entity.UserProfile;
import org.model.enums.DietType;
import org.model.valueObject.NutritionProfile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class UserFactory {

  // Creates a list of sample users
  // These users are used to populate the database when the app starts
  public static List<UserProfile> createSampleUsers() {
    return List.of(
        createLuke(),
        createLeia(),
        createHan(),
        createAna(),
        createInes(),
        createPedro(),
        createYasmin());
  }

  // User 1 — Luke Skywalker
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

  // User 2 — Leia Organa
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

  // User 3 — Han Solo
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

  // User 4 — Ana
  private static UserProfile createAna() {
    UserProfile user = new UserProfile();
    user.setFirstName("Ana");
    user.setLastName("Cardoso");
    user.setEmail("anaptcardoso11@gmail.com");
    user.setPhone("911164088");
    user.setDateOfBirth(LocalDate.of(95, 10, 11));
    user.setCountry("Portugal");
    user.setNutritionProfile(new NutritionProfile(
        60.0, 165.0, "Be a programmer", "moderate",
        Set.of(DietType.OMNIVORE), null));
    user.setBio("https://github.com/anaptcardoso" + " *** " + "https://www.linkedin.com/in/ana-cardoso-063595151");
    return user;
  }

  // User 5 — Ines
  private static UserProfile createInes() {
    UserProfile user = new UserProfile();
    user.setFirstName("Inês");
    user.setLastName("Azevedo");
    user.setEmail("ines_azevedo_98@hotmail.com");
    user.setPhone("912097615");
    user.setDateOfBirth(LocalDate.of(98, 8, 23));
    user.setCountry("Portugal");
    user.setNutritionProfile(new NutritionProfile(
        75.0, 168.0, "Become the next Tony Stark", "moderate",
        Set.of(DietType.OMNIVORE), null));
    user.setBio("https://github.com/inesazevedo23" + " *** " + "https://www.linkedin.com/in/inês-azevedo-19b45131b?");
    return user;
  }

  // User 6 — Pedro
  private static UserProfile createPedro() {
    UserProfile user = new UserProfile();
    user.setFirstName("Pedro");
    user.setLastName("Saldanha");
    user.setEmail("saldanhaigor250213@gmail.com");
    user.setPhone("920176304");
    user.setDateOfBirth(LocalDate.of(94, 2, 25));
    user.setCountry("Netherlands");
    user.setNutritionProfile(new NutritionProfile(
        100.0, 183.0, "Survive another day", "moderate",
        Set.of(DietType.OMNIVORE), null));
    user.setBio("https://github.com/SaldanhaIgor" + " *** " + "https://www.linkedin.com/in/saldanha-pedro-2641511b3/");
    return user;
  }

  // User 7 — Yasmin
  private static UserProfile createYasmin() {
    UserProfile user = new UserProfile();
    user.setFirstName("Yasmin");
    user.setLastName("Pires");
    user.setEmail("yasmin17@email.com");
    user.setPhone("912566280");
    user.setDateOfBirth(LocalDate.of(2001, 6, 7));
    user.setCountry("Portugal");
    user.setNutritionProfile(new NutritionProfile(
        67.0, 170.0, "Be a programmer", "moderate",
        Set.of(DietType.OMNIVORE), null));
    user.setBio(
        "https://github.com/yasminnatasha17-blip" + " *** " + "https://www.linkedin.com/in/yasmin-pires-a9b62a182");
    return user;
  }
}
