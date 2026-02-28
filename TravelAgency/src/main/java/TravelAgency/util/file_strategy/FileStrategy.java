package TravelAgency.util.file_strategy;

import TravelAgency.TravelAgency.dtos.VacantaDTO;

import java.util.List;

public interface FileStrategy {
    void generateVacanteFile(List<VacantaDTO> vacante);
}
