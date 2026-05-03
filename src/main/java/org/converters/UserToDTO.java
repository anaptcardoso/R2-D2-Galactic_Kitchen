package org.converters;

import org.dtos.UserProfileDTO;
import org.model.entity.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserToDTO extends AbstractConverter<UserProfile, UserProfileDTO> {

    @Override
    public UserProfileDTO convert(UserProfile user) {
        // Converte a entidade UserProfile para UserProfileDTO
        return new UserProfileDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getDateOfBirth(),
                user.getCountry(),
                user.getWeight(),
                user.getHeight(),
                user.getGoal(),
                user.getActivityLevel(),
                user.getDailyCalories(),
                user.getDietType(),
                user.getAllergies(),
                user.getBio()
        );
    }
}