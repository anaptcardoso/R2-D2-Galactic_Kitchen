package org.services;

import org.converters.UserToDTO;
import org.dtos.NutritionDTO;
import org.dtos.UserProfileDTO;
import org.exceptions.UserNotFoundException;
import org.model.entity.UserProfile;
import org.model.valueObject.NutritionProfile;
import org.persistence.daos.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserProfileRepository userProfileRepository;

    // We use the converter to transform UserProfile → UserProfileDTO
    @Autowired
    private UserToDTO userToDTO;

    public UserServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }


    // findAll — returns all users
    @Override
    public List<UserProfileDTO> findAll() {
        return userToDTO.convert(userProfileRepository.findAll());
    }


    // findById — searches for a user by ID
    @Override
    public UserProfileDTO findById(int id) throws UserNotFoundException {
        UserProfile user = userProfileRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return userToDTO.convert(user);
    }


    // searchByName — searches by first and last name
    @Override
    public List<UserProfileDTO> searchByName(String firstName, String lastName) throws UserNotFoundException {
        List<UserProfile> users = userProfileRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName, lastName);
        return userToDTO.convert(users);
    }


    // save — creates a new user
    @Transactional
    @Override
    public UserProfileDTO save(UserProfileDTO dto) throws UserNotFoundException {
        // Converts the DTO to an entity to save it in the database
        UserProfile user = toEntity(dto);
        // Saves it in the database and returns the entity with the generated ID
        UserProfile saved = userProfileRepository.save(user);
        // Converts it back to a DTO to return it to the frontend
        return userToDTO.convert(saved);
    }

    // update — updates a user's personal data
    @Transactional
    @Override
    public UserProfileDTO update(int id, UserProfileDTO dto) throws UserNotFoundException {
        // We check if the user exists before updating
        UserProfile existing = userProfileRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        // Updates only the personal data
        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setDateOfBirth(dto.getDateOfBirth());
        existing.setCountry(dto.getCountry());
        existing.setBio(dto.getBio());

        UserProfile updated = userProfileRepository.save(existing);
        return userToDTO.convert(updated);
    }


    // delete — deletes a user
    @Transactional
    @Override
    public void delete(int id) throws UserNotFoundException {
        if (!userProfileRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userProfileRepository.deleteById(id);
    }


    // getNutrition — returns the user's nutritional data
    @Override
    public NutritionDTO getNutrition(int id) throws UserNotFoundException {
        UserProfile user = userProfileRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        // If NutritionProfile is null, we return an empty DTO
        // instead of getting a NullPointerException!
        NutritionProfile nutrition = user.getNutritionProfile();
        if (nutrition == null) {
            return new NutritionDTO(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
        }

        return new NutritionDTO(
                nutrition.getWeight(),
                nutrition.getHeight(),
                nutrition.getGoal(),
                nutrition.getActivityLevel(),
                nutrition.getDietPreferences(),
                nutrition.getAllergies()
        );
    }


    // updateNutrition — updates nutritional data
    @Transactional
    @Override
    public NutritionDTO updateNutrition(int id, NutritionDTO nutritionDTO) throws UserNotFoundException {
        UserProfile user = userProfileRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        // If there is no NutritionProfile, we create a new one
        NutritionProfile nutrition = user.getNutritionProfile();
        if (nutrition == null) {
            nutrition = new NutritionProfile();
            user.setNutritionProfile(nutrition);
        }

        // Updates the nutritional fields
        nutrition.setWeight(nutritionDTO.getWeight());
        nutrition.setHeight(nutritionDTO.getHeight());
        nutrition.setGoal(nutritionDTO.getGoal());
        nutrition.setActivityLevel(nutritionDTO.getActivityLevel());
        nutrition.setDietPreferences(nutritionDTO.getDietPreferences());
        nutrition.setAllergies(nutritionDTO.getAllergies());

        userProfileRepository.save(user);
        return nutritionDTO;
    }


    // PRIVATE DTO → ENTITY CONVERSION METHOD

    // Converts UserProfileDTO → UserProfile entity (to save in the database)
    private UserProfile toEntity(UserProfileDTO dto) {
        UserProfile user = new UserProfile();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setCountry(dto.getCountry());
        user.setBio(dto.getBio());

        // Creates the NutritionProfile with the nutritional data
        if(dto.getNutritionDTO() != null){
            NutritionProfile nutrition = new NutritionProfile(
                    dto.getNutritionDTO().getWeight(),
                    dto.getNutritionDTO().getHeight(),
                    dto.getNutritionDTO().getGoal(),
                    dto.getNutritionDTO().getActivityLevel(),
                    dto.getNutritionDTO().getDietPreferences(), // dietPreferences — not available in the current DTO
                    dto.getNutritionDTO().getAllergies()  // allergies — saved separately
            );
            user.setNutritionProfile(nutrition);
        }
        return user;
    }
}