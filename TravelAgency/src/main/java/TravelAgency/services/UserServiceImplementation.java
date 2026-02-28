package TravelAgency.services;

import TravelAgency.TravelAgency.dtos.UserDTO;
import TravelAgency.TravelAgency.dtos.converter.UserConverter;
import TravelAgency.TravelAgency.dtos.converter.VacantaConverter;
import TravelAgency.TravelAgency.entities.User;
import TravelAgency.TravelAgency.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static TravelAgency.TravelAgency.constante.UserConstante.*;

@Service
public class UserServiceImplementation implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImplementation.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            LOGGER.error(EROARE_ID, id);

            return null;
        }

        return UserConverter.userToUserDTO(optionalUser.get());
    }

    @Override
    public List<UserDTO> findAll() {
        return UserConverter.entityListToDtoList(userRepository.findAll());
    }

    @Override
    public void create(UserDTO userDTO) {
        userDTO.setParola(passwordEncoder.encode(userDTO.getParola()));

        User user = userRepository.save(UserConverter.userDTOToUser(userDTO));

        LOGGER.info(INSERT, user.getId());
    }

    @Override
    public void update(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(userDTO.getId());
        if (userOptional.isEmpty()) {
            LOGGER.error(EROARE_ID, userDTO.getId());

            return;
        }
        User existingUser = userOptional.get();

        existingUser.setNume(userDTO.getNume());
        existingUser.setPrenume(userDTO.getPrenume());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setParola(userDTO.getParola());
        existingUser.setRol(userDTO.getRol());
        existingUser.setVacanta(VacantaConverter.dtoListToEntityList(userDTO.getVacantaDTO()));

        userRepository.save(existingUser);
        LOGGER.info(UPDATE, userDTO.getId());
    }


    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            LOGGER.error(EROARE_ID, id);

            return;
        }

        userRepository.deleteById(id);
        LOGGER.info(DELETE, id);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(userDTO.getId());
        if (userOptional.isEmpty()) {
            LOGGER.error(EROARE_ID, userDTO.getId());

            return;
        }
        userDTO.setParola(passwordEncoder.encode(userDTO.getParola()));

        userRepository.save(UserConverter.userDTOToUser(userDTO));
        LOGGER.info(UPDATE, userDTO.getId());
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return null;
        }

        return UserConverter.userToUserDTO(user);
    }
}
