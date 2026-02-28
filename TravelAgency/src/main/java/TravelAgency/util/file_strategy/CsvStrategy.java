package TravelAgency.util.file_strategy;

import TravelAgency.TravelAgency.dtos.VacantaDTO;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvStrategy implements FileStrategy{
    @Override
    public void generateVacanteFile(List<VacantaDTO> vacante) {
        try (FileWriter writer = new FileWriter("vacante.csv")) {
            writer.append("ID,Tip,Destinatie,Durata,Pret,Locuri,Descriere");
            writer.append("\n");

            for (VacantaDTO vacanta : vacante) {
                writer.append(vacanta.toCSV());
                writer.append("\n");
            }

        } catch (IOException e) {
            System.err.println("Error generating CSV: " + e.getMessage());
        }
    }
}
