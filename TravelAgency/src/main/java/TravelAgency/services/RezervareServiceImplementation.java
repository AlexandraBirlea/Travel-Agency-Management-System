package TravelAgency.services;

import TravelAgency.TravelAgency.dtos.RezervareDTO;
import TravelAgency.TravelAgency.dtos.converter.RezervareConverter;
import TravelAgency.TravelAgency.dtos.converter.VacantaConverter;
import TravelAgency.TravelAgency.entities.Rezervare;
import TravelAgency.TravelAgency.entities.User;
import TravelAgency.TravelAgency.entities.Vacanta;
import TravelAgency.TravelAgency.repositories.RezervareRepository;
import TravelAgency.TravelAgency.repositories.UserRepository;
import TravelAgency.TravelAgency.repositories.VacantaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static TravelAgency.TravelAgency.constante.RezervareConstante.*;

@Service
public class RezervareServiceImplementation implements RezervareService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RezervareServiceImplementation.class);

    private final RezervareRepository rezervareRepository;
    private final VacantaRepository vacantaRepository;
    private final UserRepository userRepository;

    public RezervareServiceImplementation(RezervareRepository rezervareRepository, VacantaRepository vacantaRepository, UserRepository userRepository) {
        this.rezervareRepository = rezervareRepository;
        this.vacantaRepository = vacantaRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RezervareDTO findById(Long id) {
        Optional<Rezervare> optionalRezervare = rezervareRepository.findById(id);

        if (optionalRezervare.isEmpty()) {
            LOGGER.error(EROARE_ID, id);

            return null;
        }

        return RezervareConverter.rezervareToRezervareDTO(optionalRezervare.get());
    }

    @Override
    public List<RezervareDTO> findAll() {
        return RezervareConverter.entityToDtoList(rezervareRepository.findAll());
    }

    @Override
    public void create(RezervareDTO rezervareDTO) {
        Rezervare rezervare = rezervareRepository.save(RezervareConverter.rezervareDTOToRezervare(rezervareDTO));

        LOGGER.info(INSERT, rezervare.getId());
    }

    @Override
    public void update(RezervareDTO rezervareDTO) {
        Optional<Rezervare> rezervareOptional = rezervareRepository.findById(rezervareDTO.getId());
        if (rezervareOptional.isEmpty()) {
            LOGGER.error(EROARE_ID, rezervareDTO.getId());

            return;
        }
        Rezervare existingRezervare = rezervareOptional.get();

        existingRezervare.setVacanta(VacantaConverter.vacantaDTOTOVacanta(rezervareDTO.getVacantaDTO()));
        existingRezervare.setDataRezervare(rezervareDTO.getDataRezervare());
        existingRezervare.setNumarPersoane(rezervareDTO.getNumarPersoane());
        existingRezervare.setUtilizator(rezervareDTO.getUtilizator());

        rezervareRepository.save(existingRezervare);
        LOGGER.info(UPDATE, rezervareDTO.getId());
    }

    @Override
    public void delete(Long id) {
        if (!rezervareRepository.existsById(id)) {
            LOGGER.error(EROARE_ID, id);

            return;
        }

        rezervareRepository.deleteById(id);
        LOGGER.info(DELETE, id);
    }

    @Override
    public List<RezervareDTO> findByUtilizatorId(Long id) {
        return RezervareConverter.entityToDtoList(rezervareRepository.findByUtilizatorId(id));
    }

    @Override
    public List<RezervareDTO> findByVacantaId(long id) {
        return RezervareConverter.entityToDtoList(rezervareRepository.findByVacantaId(id));
    }

    @Override
    public boolean rezerva(RezervareDTO rezervareDTO, Long vacantaId) {

        Vacanta vacanta = vacantaRepository.findById(vacantaId).orElse(null);

        if(vacanta == null)
            return false;

        int numarLocuri = vacanta.getLocuriDisponibile();
        int numarPersoane = rezervareDTO.getNumarPersoane();

        if(numarLocuri >= numarPersoane){
            vacanta.setLocuriDisponibile(numarLocuri - numarPersoane);
            rezervareDTO.setVacantaDTO(VacantaConverter.vacantaToVacantaDTO(vacanta));
            create(rezervareDTO);
            vacantaRepository.save(vacanta);

            User user = userRepository.findById(rezervareDTO.getUtilizator().getId()).orElse(null);

            if(user == null)
                return false;

            user.addVacanta(vacanta);

            userRepository.save(user);

            return true;
        }

        return false;
    }
}
