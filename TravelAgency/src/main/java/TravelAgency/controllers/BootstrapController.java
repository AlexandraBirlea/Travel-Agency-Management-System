package TravelAgency.controllers;

import TravelAgency.TravelAgency.dtos.UserDTO;
import TravelAgency.TravelAgency.dtos.VacantaDTO;
import TravelAgency.TravelAgency.entities.EmailPayload;
import TravelAgency.TravelAgency.entities.Rol;
import TravelAgency.TravelAgency.entities.TipVacanta;
import TravelAgency.TravelAgency.services.EmailService;
import TravelAgency.TravelAgency.services.RolService;
import TravelAgency.TravelAgency.services.UserService;
import TravelAgency.TravelAgency.services.VacantaService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/bootstrap")
public class BootstrapController {

    private final RolService rolService;
    private final UserService userService;
    private final VacantaService vacantaService;
    private final PasswordEncoder passwordEncoder;

    public BootstrapController(RolService rolService, UserService userService, VacantaService vacantaService, PasswordEncoder passwordEncoder) {
        this.rolService = rolService;
        this.userService = userService;
        this.vacantaService = vacantaService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String bootstrap() {
        rolService.create(new Rol(1L, "ADMIN"));
        rolService.create(new Rol(2L, "CLIENT"));
        rolService.create(new Rol(3L, "AGENT"));

        userService.create(UserDTO.builder()
                .withId(1L)
                .withNume("POPESCU")
                .withPrenume("Ion")
                .withEmail("ion@gmail.com")
                .withParola("parola123")
                .withRol(rolService.findByName("ADMIN"))
                .withVacantaDTO(new ArrayList<>())
                .build());

        userService.create(UserDTO.builder()
                .withId(2L)
                .withNume("BUD")
                .withPrenume("Cristian")
                .withEmail("cristian@gmail.com")
                .withParola("parola123")
                .withRol(rolService.findByName("AGENT"))
                .withVacantaDTO(new ArrayList<>())
                .build());

        vacantaService.create(VacantaDTO.builder()
                .withId(1L)
                .withLocuriDisponibile(10)
                .withTipVacanta(TipVacanta.CROAZIERA)
                .withDestinatie("Maldive")
                .withDurataZile(14)
                .withPret(1000)
                .withDescriere("Croaziera in Maldive!")
                .build());

        vacantaService.create(VacantaDTO.builder()
                .withId(2L)
                .withTipVacanta(TipVacanta.SEJUR)
                .withLocuriDisponibile(6)
                .withDestinatie("Franta")
                .withDurataZile(7)
                .withPret(400)
                .withDescriere("Sejur in Franta!")
                .build());

        vacantaService.create(VacantaDTO.builder()
                .withId(3L)
                .withLocuriDisponibile(4)
                .withTipVacanta(TipVacanta.EXCURSIE)
                .withDestinatie("Croatia")
                .withDurataZile(14)
                .withPret(1000)
                .withDescriere("Excursie in Croatia!")
                .build());

        return "redirect:/";
    }
}
