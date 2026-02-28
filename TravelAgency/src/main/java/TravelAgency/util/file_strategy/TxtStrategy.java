package TravelAgency.util.file_strategy;

import TravelAgency.TravelAgency.dtos.VacantaDTO;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TxtStrategy implements FileStrategy{
    @Override
    public void generateVacanteFile(List<VacantaDTO> vacante) {
        try (FileWriter writer = new FileWriter("vacante.txt")) {
            for (VacantaDTO vacanta : vacante) {
                writer.write(vacanta.toString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error generating text file: " + e.getMessage());
        }
    }
}
