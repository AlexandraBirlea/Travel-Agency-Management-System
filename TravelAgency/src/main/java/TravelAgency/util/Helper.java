package TravelAgency.util;

import TravelAgency.TravelAgency.dtos.UserDTO;
import TravelAgency.TravelAgency.entities.Rol;
import TravelAgency.TravelAgency.services.UserService;
import org.springframework.security.core.Authentication;

public class Helper {

    public static String getHeaderName(Authentication authentication, UserService userService) {
        if (authentication == null) {
            return "home";
        } else {
            String email = authentication.getName();
            UserDTO user = userService.findByEmail(email);
            Rol rol = user.getRol();

            if (rol.getNume().equals("ADMIN")) {
                return "admin";
            } else if (rol.getNume().equals("AGENT")) {
                return "agent";
            } else {
                return "client";
            }
        }
    }
}