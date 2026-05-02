package org.repository;

import org.model.entity.WeeklyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklyPlanRepository extends JpaRepository<WeeklyPlan, String> {

    // Buscar planos de um utilizador específico
    List<WeeklyPlan> findByUserId(String userId);

    // Buscar plano atual de um utilizador por número de semanas
    Optional<WeeklyPlan> findByUserIdAndWeekNumber(String userId, int weekNumber);
}
