package TravelAgency.controllers;

import TravelAgency.TravelAgency.dtos.UserDTO;
import TravelAgency.TravelAgency.dtos.converter.UserConverter;
import TravelAgency.TravelAgency.entities.EmailPayload;
import TravelAgency.TravelAgency.entities.Rol;
import TravelAgency.TravelAgency.entities.User;
import TravelAgency.TravelAgency.services.EmailService;
import TravelAgency.TravelAgency.services.RolService;
import TravelAgency.TravelAgency.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@Controller
public class AuthenticationController {

    private final UserService userService;
    private final RolService rolService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthenticationController(UserService userService, RolService rolService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userService = userService;
        this.rolService = rolService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("register")
    public String showRegisterForm(Model model){
        model.addAttribute("title", "Register");
        model.addAttribute(new UserDTO());
        model.addAttribute("headerName", "home");

        return "register";
    }

    @PostMapping("register/save")
    public String registration(@Valid @ModelAttribute UserDTO userDTO, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("title", "Register");
            model.addAttribute("headerName", "home");

            return "register";
        }

        UserDTO existingUser = userService.findByEmail(userDTO.getEmail());

        if(existingUser != null){
            model.addAttribute("errors", "This email address is already in use");
            model.addAttribute("title", "Register");
            model.addAttribute("headerName", "home");

            return "register";
        }

        Rol rol = rolService.findByName("CLIENT");
        userDTO.setRol(rol);

        userService.create(userDTO);

        EmailPayload emailPayload = new EmailPayload();
        emailPayload.setRecipientEmail(userDTO.getEmail());
        emailPayload.setSubject("Registered!");
        emailPayload.setBody("Salut "+ userDTO.getPrenume() + " " + userDTO.getNume() +"!\n" +
                "\n" +
                "Te-ai inregistrat in aplicatia agentiei noastre de turism.\n" +
                "Multumim si bine ai venit!");
        emailPayload.setFirstName(userDTO.getPrenume());
        emailPayload.setLastName(userDTO.getNume());

        emailService.sendEmail(emailPayload);

        return "redirect:/login";
    }

    @GetMapping("login")
    public String login(Model model){
        model.addAttribute("title", "Login");
        model.addAttribute("headerName", "home");

        return "login";
    }
}
