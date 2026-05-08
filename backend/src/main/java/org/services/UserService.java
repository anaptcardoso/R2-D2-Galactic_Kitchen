package org.services;

import org.dtos.NutritionDTO;
import org.dtos.UserProfileDTO;
import org.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {

    // Finds all users
    List<UserProfileDTO> findAll();

    // Finds by ID
    UserProfileDTO findById(int id) throws UserNotFoundException;

    // Finds by first and last name
    List<UserProfileDTO> searchByName(String firstName, String lastName) throws UserNotFoundException;

    // Creates a new user
    UserProfileDTO save(UserProfileDTO dto) throws UserNotFoundException;

    // Updates an existing user
    UserProfileDTO update(int id, UserProfileDTO dto) throws UserNotFoundException;

    // Deletes a user
    void delete(int id) throws UserNotFoundException;

    // Returns the user's nutritional data
    NutritionDTO getNutrition(int id) throws UserNotFoundException;

    // Updates the user's nutritional data
    NutritionDTO updateNutrition(int id, NutritionDTO nutritionDTO) throws UserNotFoundException;
}