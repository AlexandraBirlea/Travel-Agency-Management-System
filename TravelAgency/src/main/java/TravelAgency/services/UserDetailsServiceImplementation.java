package TravelAgency.services;

import TravelAgency.TravelAgency.dtos.UserDTO;
import TravelAgency.TravelAgency.dtos.converter.UserConverter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImplementation(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userService.findByEmail(username);

        if(userDTO == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return UserConverter.userDTOToUser(userDTO);
    }
}
