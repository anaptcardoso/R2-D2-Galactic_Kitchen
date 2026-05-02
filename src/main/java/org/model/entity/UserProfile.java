package org.model.entity;


import org.model.enums.DietType;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Set;

@Entity
public class UserProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DietType> dietPreferences;

      // GETTERS / SETTERS

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }

    public Set<DietType> getDietPreferences() { return dietPreferences; }
    public void setDietPreferences(Set<DietType> dietPreferences) { this.dietPreferences = dietPreferences; }
}
