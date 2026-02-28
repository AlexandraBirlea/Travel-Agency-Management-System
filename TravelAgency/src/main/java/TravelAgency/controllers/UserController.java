package TravelAgency.controllers;

import TravelAgency.TravelAgency.dtos.UserDTO;
import TravelAgency.TravelAgency.entities.Rol;
import TravelAgency.TravelAgency.services.RolService;
import TravelAgency.TravelAgency.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final RolService rolService;

    public UserController(UserService userService, RolService rolService) {
        this.userService = userService;
        this.rolService = rolService;
    }

    @GetMapping("view")
    public String renderUserViewPage(Model model) {
        model.addAttribute("title", "Utilizatori");
        model.addAttribute("headerName", "admin");
        model.addAttribute("users", userService.findAll());

        return "user/users";
    }

    @GetMapping("details")
    public String showUserDetails(@RequestParam(required = false) Long userId, Model model) {
        if (userId == null) {
            return "redirect:/user/view";
        } else {
            UserDTO userDTO = userService.findById(userId);

            model.addAttribute("title", "User: " + userDTO.getPrenume());
            model.addAttribute("headerName", "admin");
            userDTO.setId(userId);
            model.addAttribute(userDTO);
            List<Rol> roles = rolService.findAll();
            model.addAttribute("roles", roles);

            return "user/user_details";
        }
    }

    @PostMapping("update")
    public String updateUser(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "User: " + userDTO.getPrenume());
            model.addAttribute("headerName", "admin");
            model.addAttribute(userDTO);
            List<Rol> roles = rolService.findAll();
            model.addAttribute("roles", roles);

            return "user/user_details";
        }

        userService.updateUser(userDTO);

        return "redirect:/user/view";
    }

    @GetMapping("delete")
    public String renderDeleteForm(Model model) {
        model.addAttribute("title", "Sterge Utilizatori");
        model.addAttribute("headerName", "admin");
        model.addAttribute("users", userService.findAll());

        return "user/user_delete";
    }

    @PostMapping("delete/submit")
    public String deleteUsers(@RequestParam(required = false) long[] userIds) {
        if (userIds != null) {
            for (long id : userIds) {
                userService.delete(id);
            }
        }

        return "redirect:/user/delete";
    }

    @GetMapping("create")
    public String renderCreateUserForm(Model model) {
        model.addAttribute("title", "Adaugare Utilizator");
        model.addAttribute(new UserDTO());
        model.addAttribute("headerName", "admin");
        model.addAttribute("roles", rolService.findAll());

        return "user/user_create";
    }

    @PostMapping("create/save")
    public String adduser(@ModelAttribute @Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Adaugare Utilizator");
            model.addAttribute(userDTO);
            model.addAttribute("headerName", "admin");
            model.addAttribute("roles", rolService.findAll());

            return "user/user_create";
        }

        userService.create(userDTO);

        return "redirect:/user/view";
    }
}
