package org.converters;

import org.dtos.NutritionDTO;
import org.dtos.UserProfileDTO;
import org.model.entity.UserProfile;
import org.model.valueObject.NutritionProfile;
import org.springframework.stereotype.Component;

@Component
public class UserToDTO extends AbstractConverter<UserProfile, UserProfileDTO> {

    @Override
    public UserProfileDTO convert(UserProfile user) {

        // converte NutritionProfile embeddable → NutritionDTO
        NutritionProfile nutrition = user.getNutritionProfile();
        NutritionDTO nutritionDTO = null;

        if (nutrition != null) {
            nutritionDTO = new NutritionDTO(
                    nutrition.getWeight(),
                    nutrition.getHeight(),
                    nutrition.getGoal(),
                    nutrition.getActivityLevel(),
                    nutrition.getDietPreferences(),
                    nutrition.getAllergies()
            );
        }

        // converte UserProfile → UserProfileDTO com dados pessoais + NutritionDTO
        return new UserProfileDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getDateOfBirth(),
                user.getCountry(),
                user.getBio(),
                nutritionDTO
        );

    }
}