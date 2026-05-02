package org.repository;

import org.model.entity.WeeklyPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeeklyPlanRepository extends JpaRepository<WeeklyPlan, String> {
}
