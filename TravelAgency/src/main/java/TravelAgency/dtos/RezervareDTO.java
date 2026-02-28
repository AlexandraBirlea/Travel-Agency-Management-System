package TravelAgency.dtos;

import TravelAgency.TravelAgency.entities.User;
import TravelAgency.TravelAgency.entities.Vacanta;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;

import static TravelAgency.TravelAgency.constante.RezervareConstante.EROARE_PERSOANE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class RezervareDTO {
    private Long id;
    private User utilizator;
    private VacantaDTO vacantaDTO;
    private LocalDate dataRezervare;
    @Min(value = 1, message = EROARE_PERSOANE)
    private int numarPersoane;

}
