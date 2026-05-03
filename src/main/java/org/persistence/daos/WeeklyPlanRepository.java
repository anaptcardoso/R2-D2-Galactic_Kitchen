package org.persistence.daos;

import org.model.entity.UserProfile;
import org.model.entity.WeeklyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklyPlanRepository extends JpaRepository<WeeklyPlan, Integer> {

    // Buscar planos de um utilizador específico
    List<WeeklyPlan> findByUser(UserProfile userProfile);

    // Buscar plano atual de um utilizador por número de semanas
    Optional<WeeklyPlan> findByUserAndWeekStart(UserProfile userProfile, LocalDate weekStart);
}
