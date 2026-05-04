package org.services;

import org.dtos.NutritionDTO;
import org.dtos.UserProfileDTO;
import org.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {

    // Procura todos os utilizadores
    List<UserProfileDTO> findAll();

    // Procura por ID
    UserProfileDTO findById(int id) throws UserNotFoundException;

    // Procura por primeiro e último nome
    List<UserProfileDTO> searchByName(String firstName, String lastName) throws UserNotFoundException;

    // Cria um utilizador novo
    UserProfileDTO save(UserProfileDTO dto) throws UserNotFoundException;

    // Atualiza um utilizador existente
    UserProfileDTO update(int id, UserProfileDTO dto) throws UserNotFoundException;

    // Apaga um utilizador
    void delete(int id) throws UserNotFoundException;

    // Devolve os dados nutricionais do utilizador
    NutritionDTO getNutrition(int id) throws UserNotFoundException;

    // Atualiza os dados nutricionais do utilizador
    NutritionDTO updateNutrition(int id, NutritionDTO nutritionDTO) throws UserNotFoundException;
}