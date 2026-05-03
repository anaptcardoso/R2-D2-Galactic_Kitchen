package org.converters;

import org.dtos.NutritionDTO;
import org.dtos.UserProfileDTO;
import org.model.entity.UserProfile;
import org.model.valueObject.NutritionProfile;
import org.springframework.stereotype.Component;

@Component
public class UserToDTO extends AbstractConverter<UserProfile, UserProfileDTO> {

    @Override
    public UserProfileDTO convert(UserProfile userProfile) {
        NutritionProfile nutritionProfile = userProfile.getNutritionProfile();
        NutritionDTO nutritionDTO = new NutritionDTO(
                nutritionProfile.getWeight(),
                nutritionProfile.getHeight(),
                nutritionProfile.getGoal(),
                nutritionProfile.getActivityLevel(),
                nutritionProfile.getDietPreferences(),
                nutritionProfile.getAllergies()
        );

        return new UserProfileDTO(
                userProfile.getId(),
                userProfile.getFirstName(),
                userProfile.getLastName(),
                userProfile.getEmail(),
                userProfile.getPhone(),
                userProfile.getDateOfBirth(),
                userProfile.getCountry(),
                userProfile.getBio(),
                nutritionDTO
        );
    }
}
