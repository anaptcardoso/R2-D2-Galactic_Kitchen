package org.model.entity;


import jakarta.persistence.*;
import org.model.enums.DietType;
import org.model.valueObject.NutritionProfile;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class UserProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // dados pessoais
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String country;

    // dados nutricionais
    private Double weight;
    private Double height;
    private String goal;
    private String activityLevel;

    @Embedded
    private NutritionProfile nutritionProfile;

    // preferências alimentares
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DietType> dietPreferences;

    // nota pessoal
    private String bio;

    // GETTERS / SETTERS

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getId() { return id; }

    public void setId( int id) { this.id = id; }

    public String getGoal() { return goal; }

    public void setGoal(String goal) { this.goal = goal; }

    public Set<DietType> getDietPreferences() { return dietPreferences; }

    public void setDietPreferences(Set<DietType> dietPreferences) { this.dietPreferences = dietPreferences; }

    public NutritionProfile getNutritionProfile() {
        return nutritionProfile;
    }

    public void setNutritionProfile(NutritionProfile nutritionProfile) {
        this.nutritionProfile = nutritionProfile;
    }
}
