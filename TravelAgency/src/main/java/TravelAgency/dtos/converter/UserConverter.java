package TravelAgency.dtos.converter;

import TravelAgency.TravelAgency.dtos.UserDTO;
import TravelAgency.TravelAgency.entities.User;

import java.util.List;

public class UserConverter {

    private UserConverter() {
    }

    public static UserDTO userToUserDTO(User user) {
        return UserDTO.builder()
                .withId(user.getId())
                .withNume(user.getNume())
                .withPrenume(user.getPrenume())
                .withEmail(user.getEmail())
                .withParola(user.getParola())
                .withRol(user.getRol())
                .withVacantaDTO(VacantaConverter.entityListToDtoList(user.getVacanta()))
                .build();
    }

    public static User userDTOToUser(UserDTO usersDTO) {
        return User.builder()
                .withId(usersDTO.getId())
                .withNume(usersDTO.getNume())
                .withPrenume(usersDTO.getPrenume())
                .withEmail(usersDTO.getEmail())
                .withParola(usersDTO.getParola())
                .withRol(usersDTO.getRol())
                .withVacanta(VacantaConverter.dtoListToEntityList(usersDTO.getVacantaDTO()))
                .build();
    }

    public static List<UserDTO> entityListToDtoList(List<User> users) {
        return users.stream()
                .map(UserConverter::userToUserDTO)
                .toList();
    }
}
