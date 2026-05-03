
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

    @Autowired
    private UserToDTO userToDTO;

    public UserServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    // findAll — devolve todos os utilizadores
    @Override
    public List<UserProfileDTO> findAll() {
        return userToDTO.convert(userProfileRepository.findAll());
    }

    // findById — procura um utilizador por ID
    @Override
    public UserProfileDTO findById(int id) throws UserNotFoundException {
        UserProfile user = userProfileRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return userToDTO.convert(user);
    }

    // searchByName — procura por primeiro e último nome
    @Override
    public List<UserProfileDTO> searchByName(String firstName, String lastName) throws UserNotFoundException {
        List<UserProfile> users = userProfileRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName, lastName);
        return userToDTO.convert(users);
    }

    // save — cria um utilizador novo
    @Transactional
    @Override
    public UserProfileDTO save(UserProfileDTO dto) throws UserNotFoundException {
        UserProfile user = toEntity(dto);
        UserProfile saved = userProfileRepository.save(user);
        return userToDTO.convert(saved);
    }

    // update — atualiza dados pessoais de um utilizador existente
    @Transactional
    @Override
    public UserProfileDTO update(int id, UserProfileDTO dto) throws UserNotFoundException {
        UserProfile existing = userProfileRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        // atualiza dados pessoais
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

    // delete — apaga um utilizador
    @Transactional
    @Override
    public void delete(int id) throws UserNotFoundException {
        if (!userProfileRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userProfileRepository.deleteById(id);
    }

    // getNutrition — devolve os dados nutricionais do utilizador
    @Override
    public NutritionDTO getNutrition(int id) throws UserNotFoundException {
        UserProfile user = userProfileRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        NutritionProfile nutrition = user.getNutritionProfile();

        return new NutritionDTO(
                nutrition.getWeight(),
                nutrition.getHeight(),
                nutrition.getGoal(),
                nutrition.getActivityLevel(),
                nutrition.getDietPreferences(),
                nutrition.getAllergies()
        );
    }

    // updateNutrition — atualiza os dados nutricionais do utilizador
    @Transactional
    @Override
    public NutritionDTO updateNutrition(int id, NutritionDTO nutritionDTO) throws UserNotFoundException {
        UserProfile user = userProfileRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        // acede ao NutritionProfile embeddable e atualiza os campos
        NutritionProfile nutrition = user.getNutritionProfile();
        nutrition.setWeight(nutritionDTO.getWeight());
        nutrition.setHeight(nutritionDTO.getHeight());
        nutrition.setGoal(nutritionDTO.getGoal());
        nutrition.setActivityLevel(nutritionDTO.getActivityLevel());
        nutrition.setDietPreferences(nutritionDTO.getDietPreferences());
        nutrition.setAllergies(nutritionDTO.getAllergies());

        userProfileRepository.save(user);
        return nutritionDTO;
    }

    // ── Método privado de conversão DTO → entidade ────────────────────────────

    private UserProfile toEntity(UserProfileDTO dto) {
        UserProfile user = new UserProfile();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setCountry(dto.getCountry());
        user.setBio(dto.getBio());

        // converte NutritionDTO → NutritionProfile embeddable
        if (dto.getNutritionDTO() != null) {
            NutritionProfile nutrition = new NutritionProfile(
                    dto.getNutritionDTO().getWeight(),
                    dto.getNutritionDTO().getHeight(),
                    dto.getNutritionDTO().getGoal(),
                    dto.getNutritionDTO().getActivityLevel(),
                    dto.getNutritionDTO().getDietPreferences(),
                    dto.getNutritionDTO().getAllergies()
            );
            user.setNutritionProfile(nutrition);
        }

        return user;
    }
}
