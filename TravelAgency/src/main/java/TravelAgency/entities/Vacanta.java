package TravelAgency.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

import static TravelAgency.TravelAgency.constante.VacantaConstante.*;

@Builder(setterPrefix = "with")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vacanta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tip_vacanta")
    @NotNull
    private TipVacanta tipVacanta;

    @NotBlank(message = EROARE_DESTINATIE)
    private String destinatie;

    @Min(value = 1, message = EROARE_DURATA)
    private int durataZile;

    @Min(value = 100, message = EROARE_PRET)
    private double pret;

    @NotBlank(message = EROARE_DESCRIERE)
    private String descriere;

    private int locuriDisponibile;

    @ManyToMany(mappedBy = "vacanta", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> user;

    public void removeAssocition(User user) {
        user.getVacanta().remove(this);
    }

}
