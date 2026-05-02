package org.services;

import org.dtos.UserProfileDTO;
import org.model.entity.UserProfile;
import org.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {

    private final UserProfileRepository userProfileRepository;

    public UserServiceImpl(UserProfileRepository userProfileRepository){
        this.userProfileRepository = userProfileRepository;
    }

    // findAll — devolve todos os utilizadores
    @Override
    public List<UserProfileDTO> findAll(){
        // Busca todos os utilizadores na BD
        // e converte cada UserProfile para UserProfileDTO
        return userProfileRepository.findAll()
                .stream()
                .mao(this::toDTO)
                .toList();
    }

    // findById — busca um utilizador por ID
    @Override
    public UserProfileDTO findById(int id){
        // Se não existir, lança excepção com mensagem clara
        UserProfile user = userProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"+ id));
        return toDTO(user);
    }

    // findByName — busca por primeiro ou último nome
    @Override
    public UserProfileDTO findByName(String name){
        UserProfile user = userProfileRepository.findByName(name)
                .orElseThrow(()-> new RuntimeException("User not found" +name));
        return toDTO(user);
    }

    // save — cria um utilizador novo
    @Transactional
    @Override
    public UserProfileDTO save(UserProfileDTO dto) {
        // Converte o DTO para entidade para guardar na BD
        UserProfile user = toEntity(dto);

        // Guarda na BD e devolve a entidade com ID gerado
        UserProfile saved = userProfileRepository.save(user);

        // Converte de volta para DTO para devolver ao frontend
        return toDTO(saved);
    }

    // update — atualiza um utilizador existente
    @Transactional
    @Override
    public UserProfileDTO update(int id, UserProfileDTO dto) {
        // Verificamos se o utilizador existe antes de atualizar
        UserProfile existing = userProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        // Atualizamos todos os campos com os valores do DTO
        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setDateOfBirth(dto.getDateOfBirth());
        existing.setCountry(dto.getCountry());
        existing.setWeight(dto.getWeight());
        existing.setHeight(dto.getHeight());
        existing.setGoal(dto.getGoal());
        existing.setActivityLevel(dto.getActivityLevel());
        existing.setDietType(dto.getDietType());
        existing.setAllergies(dto.getAllergies());
        existing.setBio(dto.getBio());

        // Guardamos as alterações na BD
        UserProfile updated = userProfileRepository.save(existing);
        return toDTO(updated);
    }

    // delete — apaga um utilizador
    @Transactional
    @Override
    public void delete(int id) {
        // Verificamos se existe antes de apagar
        if (!userProfileRepository.existsById(id)) {
            throw new RuntimeException("User not found: " + id);
        }
        userProfileRepository.deleteById(id);
    }

    // MÉTODOS PRIVADOS DE CONVERSÃO

    // Converte entidade UserProfile → UserProfileDTO (para enviar ao frontend)
    private UserProfileDTO toDTO(UserProfile user) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setCountry(user.getCountry());
        dto.setWeight(user.getWeight());
        dto.setHeight(user.getHeight());
        dto.setGoal(user.getGoal());
        dto.setActivityLevel(user.getActivityLevel());
        dto.setDietType(user.getDietType());
        dto.setAllergies(user.getAllergies());
        dto.setBio(user.getBio());
        return dto;
    }

    // Converte UserProfileDTO → entidade UserProfile
    private UserProfile toEntity(UserProfileDTO dto) {
        UserProfile user = new UserProfile();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setCountry(dto.getCountry());
        user.setWeight(dto.getWeight());
        user.setHeight(dto.getHeight());
        user.setGoal(dto.getGoal());
        user.setActivityLevel(dto.getActivityLevel());
        user.setDietType(dto.getDietType());
        user.setAllergies(dto.getAllergies());
        user.setBio(dto.getBio());
        return user;
    }
}
