package org.repository;

import org.model.entity.UserProfile;
import org.model.enums.DietType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

    // Buscar utilizador por nome
    Optional<UserProfile> findByFirstNameOrLastName(String firstName, String lastName);

    // Buscar utilizadores por preferência de dieta
    List<UserProfile> findByDietType(DietType dietType);
}
