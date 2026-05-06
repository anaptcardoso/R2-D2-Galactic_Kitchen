package org.model.entity;


import jakarta.persistence.*;
import org.model.enums.DietType;
import org.model.valueObject.NutritionProfile;

import java.time.LocalDate;
import java.util.List;
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

    @Embedded
    private NutritionProfile nutritionProfile;

    // nota pessoal
    private String bio;

    // GETTERS / SETTERS

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public NutritionProfile getNutritionProfile() {
        return nutritionProfile;
    }

    public void setNutritionProfile(NutritionProfile nutritionProfile) {
        this.nutritionProfile = nutritionProfile;
    }
}
