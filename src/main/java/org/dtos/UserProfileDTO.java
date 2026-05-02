package org.dtos;

import org.model.enums.DietType;

import java.time.LocalDate;
import java.util.List;

public class UserProfileDTO {

    //personal data
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String country;

    //personal note
    private String bio;

    //nutritional data
    private NutritionDTO nutritionDTO;


    public UserProfileDTO(){}

    public UserProfileDTO(int id, String firstName, String lastName,
                          String email, String phone, LocalDate dateOfBirth,
                          String country, String bio, NutritionDTO nutritionDTO){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.bio = bio;
        this.nutritionDTO = nutritionDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public NutritionDTO getNutritionDTO() {
        return nutritionDTO;
    }

    public void setNutritionDTO(NutritionDTO nutritionDTO) {
        this.nutritionDTO = nutritionDTO;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (!(obj instanceof  UserProfileDTO other)) return false;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "UserProfileDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
