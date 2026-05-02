package org.services;

import org.dtos.UserProfileDTO;

import java.util.List;

public interface UserService {

    // Busca todos os utilizadores
    List<UserProfileDTO> findAll();

    // Busca por ID
    UserProfileDTO findById(int id);

    // Busca por nome
    UserProfileDTO findByName(String name);

    // Cria um utilizador novo
    UserProfileDTO save(UserProfileDTO dto);

    // Atualiza um utilizador existente
    UserProfileDTO update(int id, UserProfileDTO dto);

    // Apaga um utilizador
    void delete(int id);
}
