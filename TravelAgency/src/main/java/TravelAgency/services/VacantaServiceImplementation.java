package TravelAgency.services;

import TravelAgency.TravelAgency.dtos.RezervareDTO;
import TravelAgency.TravelAgency.dtos.VacantaDTO;
import TravelAgency.TravelAgency.dtos.converter.VacantaConverter;
import TravelAgency.TravelAgency.entities.User;
import TravelAgency.TravelAgency.entities.Vacanta;
import TravelAgency.TravelAgency.entities.TipVacanta;
import TravelAgency.TravelAgency.repositories.UserRepository;
import TravelAgency.TravelAgency.repositories.VacantaRepository;
import TravelAgency.TravelAgency.util.file_strategy.CsvStrategy;
import TravelAgency.TravelAgency.util.file_strategy.FileContext;
import TravelAgency.TravelAgency.util.file_strategy.PdfStrategy;
import TravelAgency.TravelAgency.util.file_strategy.TxtStrategy;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static TravelAgency.TravelAgency.constante.VacantaConstante.*;

@Service
public class VacantaServiceImplementation implements VacantaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VacantaServiceImplementation.class);

    private final VacantaRepository vacanteRepository;
    private final RezervareService rezervareService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final FileContext fileContext;
    private final VacantaRepository vacantaRepository;

    public VacantaServiceImplementation(VacantaRepository vacanteRepository, RezervareService rezervareService, UserService userService, UserRepository userRepository, VacantaRepository vacantaRepository) {
        this.vacanteRepository = vacanteRepository;
        this.rezervareService = rezervareService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.fileContext = new FileContext(new TxtStrategy());
        this.vacantaRepository = vacantaRepository;
    }

    @Override
    public VacantaDTO findById(Long id) {
        Optional<Vacanta> optionalVacanta = vacanteRepository.findById(id);

        if (optionalVacanta.isEmpty()) {
            LOGGER.error(EROARE_ID, id);

            return null;
        }

        return VacantaConverter.vacantaToVacantaDTO(optionalVacanta.get());
    }

    @Override
    public List<VacantaDTO> findAll() {
        return VacantaConverter.entityListToDtoList(vacanteRepository.findAll());
    }

    @Override
    public void create(VacantaDTO vacantaDTO) {
        Vacanta vacanta = vacanteRepository.save(VacantaConverter.vacantaDTOTOVacanta(vacantaDTO));

        LOGGER.info(INSERT, vacanta.getId());
    }

    @Override
    @Transactional
    public void update(VacantaDTO vacantaDTO) {
        Optional<Vacanta> vacantaOptional = vacanteRepository.findById(vacantaDTO.getId());
        if (vacantaOptional.isEmpty()) {
            LOGGER.error(EROARE_ID, vacantaDTO.getId());

            return;
        }
        Vacanta existingVacanta = vacantaOptional.get();

        existingVacanta.setTipVacanta(vacantaDTO.getTipVacanta());
        existingVacanta.setDestinatie(vacantaDTO.getDestinatie());
        existingVacanta.setDurataZile(vacantaDTO.getDurataZile());
        existingVacanta.setLocuriDisponibile(vacantaDTO.getLocuriDisponibile());
        existingVacanta.setPret(vacantaDTO.getPret());
        existingVacanta.setDescriere(vacantaDTO.getDescriere());

        vacanteRepository.save(existingVacanta);
        LOGGER.info(UPDATE, vacantaDTO.getId());
    }

    @Override
    public void deleteVacata(Long id) {
        deleteRezervari(id);

        Vacanta vacanta = vacanteRepository.findById(id).orElse(null);

        if(vacanta != null) {
            List<User> users = vacanta.getUser();

            users.forEach(vacanta::removeAssocition);

            vacanteRepository.save(vacanta);
        }

        try {
            vacantaRepository.deleteById(id);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteRezervari(Long id){
        List<RezervareDTO> rezervari = rezervareService.findByVacantaId(id);

        for(RezervareDTO rezervareDTO : rezervari){
            rezervareService.delete(rezervareDTO.getId());
        }
    }

    @Override
    public void generateFile(String fileType) {
        switch (fileType) {
            case "TEXT" -> fileContext.setFileStrategy(new TxtStrategy());
            case "PDF" -> fileContext.setFileStrategy(new PdfStrategy());
            case "CSV" -> fileContext.setFileStrategy(new CsvStrategy());
        }

        List<VacantaDTO> vacante = findAll();

        fileContext.generateFile(vacante);
    }
    @Override
    public List<VacantaDTO> findByTipVacanta(TipVacanta tipVacanta) {
        List<Vacanta> vacante = vacantaRepository.findByTipVacanta(tipVacanta);
        return VacantaConverter.entityListToDtoList(vacante);
    }
    public List<VacantaDTO> searchVacante(String search) {
        List<Vacanta> vacante = vacantaRepository.searchVacante(search);
        return VacantaConverter.entityListToDtoList(vacante);
    }
    @Override
    public List<VacantaDTO> sortByPret(String ordine) {
        List<Vacanta> vacante = vacantaRepository.findAll();
        if ("asc".equalsIgnoreCase(ordine)) {
            vacante.sort(Comparator.comparing(Vacanta::getPret));
        } else if ("desc".equalsIgnoreCase(ordine)) {
            vacante.sort(Comparator.comparing(Vacanta::getPret).reversed());
        }
        return VacantaConverter.entityListToDtoList(vacante);
    }

    @Override
    public List<VacantaDTO> sortByLocuri(String ordine) {
        List<Vacanta> vacante = vacantaRepository.findAll();
        if ("asc".equalsIgnoreCase(ordine)) {
            vacante.sort(Comparator.comparing(Vacanta::getLocuriDisponibile));
        } else if ("desc".equalsIgnoreCase(ordine)) {
            vacante.sort(Comparator.comparing(Vacanta::getLocuriDisponibile).reversed());
        }
        return VacantaConverter.entityListToDtoList(vacante);
    }
}
