package TravelAgency.services;

import TravelAgency.TravelAgency.dtos.RezervareDTO;
import TravelAgency.TravelAgency.dtos.VacantaDTO;

import java.util.List;

public interface RezervareService {
    RezervareDTO findById(Long id);

    List<RezervareDTO> findAll();

    void create(RezervareDTO rezervareDTO);

    void update(RezervareDTO rezervareDTO);

    void delete(Long id);

    List<RezervareDTO> findByUtilizatorId(Long id);

    List<RezervareDTO> findByVacantaId(long id);

    boolean rezerva(RezervareDTO rezervareDTO, Long vacantaId);
}
