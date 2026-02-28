package TravelAgency.dtos;

import TravelAgency.TravelAgency.entities.TipVacanta;
import TravelAgency.TravelAgency.entities.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

import static TravelAgency.TravelAgency.constante.RezervareConstante.EROARE_PERSOANE;
import static TravelAgency.TravelAgency.constante.VacantaConstante.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class VacantaDTO {
    private Long id;
    @NotNull
    private TipVacanta tipVacanta;
    @NotBlank(message = EROARE_PERSOANE)
    private String destinatie;
    @Min(value = 1, message = EROARE_DURATA)
    private int durataZile;
    @Min(value = 100, message = EROARE_PRET)
    private double pret;
    private int locuriDisponibile;
    @NotBlank(message = EROARE_DESCRIERE)
    private String descriere;
    private List<User> user;

    @Override
    public String toString() {
        return "Vacanta: " + descriere + " in " + destinatie + ", durata: " + durataZile + ", pret: " + pret + " si " +
                "locuri: " + locuriDisponibile;
    }

    public String toCSV() {
        String destinatieCSV = "\"" + destinatie.replace("\"", "\"\"") + "\"";
        String descriereCSV = "\"" + descriere.replace("\"", "\"\"") + "\"";

        return id + "," + tipVacanta + "," + destinatieCSV + "," + durataZile + "," +
                pret + "," + locuriDisponibile + "," + descriereCSV;
    }
}
