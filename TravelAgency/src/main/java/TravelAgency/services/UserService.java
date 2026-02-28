package TravelAgency.services;

import TravelAgency.TravelAgency.dtos.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO findById(Long id);

    List<UserDTO> findAll();

    void create(UserDTO userDTO);

    void update(UserDTO userDTO);

    void delete(Long id);

    void updateUser(UserDTO userDTO);

    UserDTO findByEmail(String email);
}
