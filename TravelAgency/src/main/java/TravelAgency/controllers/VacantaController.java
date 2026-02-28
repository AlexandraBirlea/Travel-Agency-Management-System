package TravelAgency.controllers;

import TravelAgency.TravelAgency.dtos.RezervareDTO;
import TravelAgency.TravelAgency.dtos.VacantaDTO;
import TravelAgency.TravelAgency.dtos.converter.VacantaConverter;
import TravelAgency.TravelAgency.entities.TipVacanta;
import TravelAgency.TravelAgency.entities.Vacanta;
import TravelAgency.TravelAgency.services.RezervareService;
import TravelAgency.TravelAgency.services.UserService;
import TravelAgency.TravelAgency.services.VacantaService;
import TravelAgency.TravelAgency.repositories.VacantaRepository;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("vacanta")
public class VacantaController {
    private final VacantaService vacantaService;

    private final UserService userService;
    private final RezervareService rezervareService;

    public VacantaController(VacantaService vacantaService, UserService userService, RezervareService rezervareService) {
        this.vacantaService = vacantaService;
        this.userService = userService;
        this.rezervareService = rezervareService;
    }

    @GetMapping("agent")
    public String showVacanteAgent(Model model) {
        model.addAttribute("title", "Vacante");
        model.addAttribute("headerName", "agent");
        model.addAttribute("fileType", "TEXT");
        model.addAttribute("vacante", vacantaService.findAll());

        return "vacanta/vacante";
    }

    @GetMapping("client")
    public String showVacanteClient(Model model) {
        model.addAttribute("title", "Oferte");
        model.addAttribute("headerName", "client");
        model.addAttribute("vacante", vacantaService.findAll());

        return "vacanta/vacantec";
    }

    @GetMapping("create")
    public String renderCreateVacantaForm(Model model) {
        model.addAttribute("title", "Adaugare Vacanta");
        model.addAttribute(new VacantaDTO());
        model.addAttribute("headerName", "agent");

        return "vacanta/vacanta_create";
    }

    @PostMapping("create/save")
    public String addVacanta(@ModelAttribute @Valid VacantaDTO vacantaDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Adaugare Vacanta");
            model.addAttribute(vacantaDTO);
            model.addAttribute("headerName", "agent");

            return "vacanta/vacanta_create";
        }

        vacantaService.create(vacantaDTO);

        return "redirect:/vacanta/agent";
    }

    @GetMapping("agent/show")
    public String showVacantaAgent(@RequestParam(required = false) Long vacantaId, Model model) {
        if (vacantaId == null) {
            return "redirect:/vacanta/agent";
        } else {
            VacantaDTO vacantaDTO = vacantaService.findById(vacantaId);

            model.addAttribute("title", "Vacanta: " + vacantaDTO.getDestinatie());
            model.addAttribute("headerName", "agent");
            model.addAttribute(vacantaDTO);

            return "vacanta/vacanta_agent";
        }
    }

    @GetMapping("client/show")
    public String showVacantaClient(@RequestParam(required = false) Long vacantaId, Model model) {
        if (vacantaId == null) {
            return "redirect:/vacanta/client";
        } else {
            VacantaDTO vacantaDTO = vacantaService.findById(vacantaId);

            model.addAttribute("title", "Vacanta: " + vacantaDTO.getDestinatie());
            model.addAttribute("headerName", "client");
            model.addAttribute(vacantaDTO);
            model.addAttribute("rezervareDTO", new RezervareDTO());
            model.addAttribute("utilizatori", userService.findAll());

            return "vacanta/vacanta_client";
        }
    }

    @PostMapping("update")
    public String updateVacanta(@Valid @ModelAttribute VacantaDTO vacantaDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Vacanta: " + vacantaDTO.getDestinatie());
            model.addAttribute("headerName", "agent");
            model.addAttribute(vacantaDTO);

            return "vacanta/vacanta_agent";
        }

        vacantaService.update(vacantaDTO);

        return "redirect:/vacanta/agent";
    }

    @GetMapping("delete")
    public String renderDeleteForm(Model model) {
        model.addAttribute("title", "Sterge Vacanta");
        model.addAttribute("headerName", "agent");
        model.addAttribute("vacante", vacantaService.findAll());

        return "vacanta/vacanta_delete";
    }

    @PostMapping("delete/submit")
    public String deleteVacante(@RequestParam(required = false) long[] vacanteIds) {
        if (vacanteIds != null) {
            for (long id : vacanteIds) {
                vacantaService.deleteVacata(id);
            }
        }

        return "redirect:/vacanta/delete";
    }

    @PostMapping("/generate_file")
    public String  generateVacantaFile(@RequestParam String fileType) {
        vacantaService.generateFile(fileType);

        return "redirect:/vacanta/agent";
    }
    @GetMapping("filtrare")
    public String filtrareVacante(@RequestParam("tip_vacanta") TipVacanta tipVacanta, Model model) {
        System.out.println("Tip vacanta primit: " + tipVacanta);
        List<VacantaDTO> vacanteFiltrate = vacantaService.findByTipVacanta(tipVacanta);
        System.out.println("Numar de vacante gasite: " + vacanteFiltrate.size());
        model.addAttribute("title", "Vacante Filtrate");
        model.addAttribute("headerName", "client");
        model.addAttribute("vacante", vacanteFiltrate);
        return "vacanta/vacantec";
    }

        @GetMapping("/cautare")
        public String cautareVacante(@RequestParam(name = "search", required = false) String search, Model model) {
            if (search != null && !search.isEmpty()) {
                List<VacantaDTO> vacanteCautate = vacantaService.searchVacante(search);
                model.addAttribute("title", "Rezultate căutare pentru: " + search);
                model.addAttribute("headerName", "client");
                model.addAttribute("vacante", vacanteCautate);
            } else {
                return "redirect:/vacanta/client";
            }
            return "vacanta/vacantec";
        }
    @GetMapping("/sortarePret")
    public String sortareVacantePret(@RequestParam(name = "ordine", required = false, defaultValue = "asc") String ordine, Model model) {
        List<VacantaDTO> vacanteSortate = vacantaService.sortByPret(ordine);
        model.addAttribute("title", "Vacanțe sortate după preț");
        model.addAttribute("headerName", "client");
        model.addAttribute("vacante", vacanteSortate);
        return "vacanta/vacantec";
    }

    @GetMapping("/sortareLocuri")
    public String sortareVacanteLocuri(@RequestParam(name = "ordine", required = false, defaultValue = "asc") String ordine, Model model) {
        List<VacantaDTO> vacanteSortate = vacantaService.sortByLocuri(ordine);
        model.addAttribute("title", "Vacanțe sortate după numărul de locuri");
        model.addAttribute("headerName", "client");
        model.addAttribute("vacante", vacanteSortate);
        return "vacanta/vacantec";
    }
    }
