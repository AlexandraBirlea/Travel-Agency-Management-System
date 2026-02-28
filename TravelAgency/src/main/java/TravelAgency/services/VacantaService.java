package TravelAgency.services;

import TravelAgency.TravelAgency.dtos.VacantaDTO;
import TravelAgency.TravelAgency.entities.TipVacanta;


import java.util.List;

public interface VacantaService {
    VacantaDTO findById(Long id);

    List<VacantaDTO> findAll();

    void create(VacantaDTO vacantaDTO);

    void update(VacantaDTO vacantaDTO);

    void deleteVacata(Long id);

    void generateFile(String fileType);
    List<VacantaDTO> findByTipVacanta(TipVacanta tipVacanta);

    List<VacantaDTO> searchVacante(String cuvant);
    List<VacantaDTO> sortByPret(String ordine);
    List<VacantaDTO> sortByLocuri(String ordine);
}
