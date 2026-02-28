package TravelAgency.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;

import static TravelAgency.TravelAgency.constante.RezervareConstante.EROARE_PERSOANE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class Rezervare {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User utilizator;

    @ManyToOne
    private Vacanta vacanta;

    @Column(name = "data_rezervare")
    private LocalDate dataRezervare;

    @Column(name = "numar_persoane")
    @Min(value = 1, message = EROARE_PERSOANE)
    private int numarPersoane;
}
