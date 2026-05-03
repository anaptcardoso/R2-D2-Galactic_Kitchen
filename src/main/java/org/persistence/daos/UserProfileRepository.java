package org.persistence.daos;

import org.model.entity.UserProfile;
import org.model.enums.DietType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

    // Buscar utilizador por email
    Optional<UserProfile> findByEmail(String email);

    // Verificar se email já existe (para registo)
    boolean existsByEmail(String email);

    // Buscar utilizador por nome
    Optional<UserProfile> findByFirstNameAndLastName(String firstName, String lastName);
    List<UserProfile> findByFirstNameOrLastName(String firstName, String lastName);

    // Buscar utilizadores por preferência de dieta
    List<UserProfile> findByNutritionProfileDietPreferencesContaining(DietType dietType);

    List<UserProfile> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName);
}
