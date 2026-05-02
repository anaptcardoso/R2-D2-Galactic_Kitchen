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

    //nutritional data
    private Double weight;
    private Double height;
    private String goal;
    private String activityLevel;

    //food preferences
    private DietType dietType;
    private List<String> allergies;

    //personal note
    private String bio;

    public UserProfileDTO(){}

    public UserProfileDTO(int id, String firstName, String lastName,
                          String email, String phone, LocalDate dateOfBirth,
                          String country, Double weight, Double height,
                          String goal, String activityLevel,
                          DietType dietType, List<String> allergies, String bio){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.weight = weight;
        this.height = height;
        this.goal = goal;
        this.activityLevel = activityLevel;
        this.dietType = dietType;
        this.allergies = allergies;
        this.bio = bio;

    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getHeight() {
        return height;
    }

    public String getGoal() {
        return goal;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public DietType getDietType() {
        return dietType;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public String getBio() {
        return bio;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public void setDietType(DietType dietType) {
        this.dietType = dietType;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public void setBio(String bio) {
        this.bio = bio;
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
                ", dietType=" + dietType +
                '}';
    }
}
