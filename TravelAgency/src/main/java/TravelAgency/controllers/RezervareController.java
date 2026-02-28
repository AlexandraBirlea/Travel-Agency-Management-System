package TravelAgency.controllers;

import TravelAgency.TravelAgency.dtos.RezervareDTO;
import TravelAgency.TravelAgency.dtos.UserDTO;
import TravelAgency.TravelAgency.dtos.VacantaDTO;
import TravelAgency.TravelAgency.dtos.converter.UserConverter;
import TravelAgency.TravelAgency.dtos.converter.VacantaConverter;
import TravelAgency.TravelAgency.entities.EmailPayload;
import TravelAgency.TravelAgency.entities.Vacanta;
import TravelAgency.TravelAgency.repositories.VacantaRepository;
import TravelAgency.TravelAgency.services.EmailService;
import TravelAgency.TravelAgency.services.RezervareService;
import TravelAgency.TravelAgency.services.UserService;
import TravelAgency.TravelAgency.services.VacantaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("rezervare")
public class RezervareController {

    private final RezervareService rezervareService;

    private final VacantaService vacantaService;
    private final UserService userService;

    private final VacantaRepository vacantaRepository;

    private final EmailService emailService;

    public RezervareController(RezervareService rezervareService, VacantaService vacantaService, UserService userService, VacantaRepository vacantaRepository, EmailService emailService) {
        this.rezervareService = rezervareService;
        this.vacantaService = vacantaService;
        this.userService = userService;
        this.vacantaRepository = vacantaRepository;
        this.emailService = emailService;
    }

    @PostMapping("rezerva/{vacantaId}")
    public String rezervaVacanta(@ModelAttribute @Valid RezervareDTO rezervareDTO, BindingResult bindingResult,
                                 Authentication authentication,
                                 @PathVariable("vacantaId") Long vacantaId, @RequestParam("year") int year,
                                 @RequestParam("month") int month, @RequestParam("day") int day, Model model) {
        if (bindingResult.hasErrors()) {
            VacantaDTO vacantaDTO = vacantaService.findById(vacantaId);

            model.addAttribute("title", "Vacanta: " + vacantaDTO.getDestinatie());
            model.addAttribute("headerName", "client");
            model.addAttribute(vacantaDTO);
            model.addAttribute("rezervareDTO", new RezervareDTO());
            model.addAttribute("utilizatori", userService.findAll());

            return "vacanta/vacanta_client";
        }

        LocalDate localDate;

        try {
            localDate = LocalDate.of(year, month, day);
            if(localDate.isBefore(LocalDate.now())){
                throw new DateTimeException("");
            }
        }catch (DateTimeException e){
            VacantaDTO vacantaDTO = vacantaService.findById(vacantaId);

            model.addAttribute("title", "Vacanta: " + vacantaDTO.getDestinatie());
            model.addAttribute("headerName", "client");
            model.addAttribute(vacantaDTO);
            model.addAttribute("rezervareDTO", new RezervareDTO());
            model.addAttribute("utilizatori", userService.findAll());
            model.addAttribute("errors", "Data selectata este invalida!");

            return "vacanta/vacanta_client";
        }

        String email = authentication.getName();
        UserDTO userDTO = userService.findByEmail(email);

        rezervareDTO.setDataRezervare(LocalDate.of(year, month, day));
        rezervareDTO.setUtilizator(UserConverter.userDTOToUser(userDTO));

        if(!rezervareService.rezerva(rezervareDTO, vacantaId)){
            VacantaDTO vacantaDTO = vacantaService.findById(vacantaId);

            model.addAttribute("title", "Vacanta: " + vacantaDTO.getDestinatie());
            model.addAttribute("headerName", "client");
            model.addAttribute(vacantaDTO);
            model.addAttribute("rezervareDTO", new RezervareDTO());
            model.addAttribute("utilizatori", userService.findAll());
            model.addAttribute("errors", "Nu mai sunt destule locuri disponibile!");

            return "vacanta/vacanta_client";
        }

        EmailPayload emailPayload = new EmailPayload();
        emailPayload.setRecipientEmail(userDTO.getEmail());
        emailPayload.setSubject("Rezervare Vacanta!");
        emailPayload.setBody(
                "Ai rezervat o vacanta in " + rezervareDTO.getVacantaDTO().getDestinatie() +" pe data de" +
                " "+ rezervareDTO.getDataRezervare().toString() + ". Rezervarea este facuta pentru " +
                rezervareDTO.getNumarPersoane() + " persoane.\n" +
                "Iti uram vacanta placuta!");
        emailPayload.setFirstName(userDTO.getPrenume());
        emailPayload.setLastName(userDTO.getNume());

        emailService.sendEmail(emailPayload);

        return "redirect:/vacanta/client";
    }

    @GetMapping("rezervari")
    public String showRezervariForm(Model model, Authentication auth) {
        String userEmail = auth.getName();
        UserDTO userDTO = userService.findByEmail(userEmail);

        model.addAttribute("title", "Rezervari");
        model.addAttribute("headerName", "client");
        model.addAttribute("rezervari", rezervareService.findByUtilizatorId(userDTO.getId()));
        model.addAttribute("clienti", userService.findAll());
        model.addAttribute("selectedClientId", 1L);

        return "vacanta/rezervare_client";
    }

    @PostMapping("delete")
    public String deleteRezervare(@RequestParam Long id, Model model){
        RezervareDTO rezervareDTO = rezervareService.findById(id);
        Vacanta vacanta = vacantaRepository.findById(rezervareDTO.getVacantaDTO().getId()).orElse(null);

        if(vacanta == null)
            return "redirect:/rezervare/rezervari";

        vacanta.setLocuriDisponibile(vacanta.getLocuriDisponibile() + rezervareDTO.getNumarPersoane());
        vacantaRepository.save(vacanta);
        rezervareService.update(rezervareDTO);

        rezervareService.delete(id);

        model.addAttribute("title", "Rezervari");
        model.addAttribute("headerName", "client");
        model.addAttribute("rezervari", new ArrayList<RezervareDTO>());
        model.addAttribute("clienti", userService.findAll());
        model.addAttribute("selectedClientId", 1L);

        return "vacanta/rezervare_client";
    }

}
