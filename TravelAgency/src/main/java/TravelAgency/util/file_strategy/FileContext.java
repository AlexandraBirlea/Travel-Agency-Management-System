package TravelAgency.util.file_strategy;

import TravelAgency.TravelAgency.dtos.VacantaDTO;

import java.util.List;

public class FileContext {
    private FileStrategy fileStrategy;

    public FileContext(FileStrategy fileStrategy) {
        this.fileStrategy = fileStrategy;
    }

    public void setFileStrategy(FileStrategy fileStrategy) {
        this.fileStrategy = fileStrategy;
    }

    public void generateFile(List<VacantaDTO> vacante){
        fileStrategy.generateVacanteFile(vacante);
    }
}
