package TravelAgency.dtos;

import TravelAgency.TravelAgency.entities.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static TravelAgency.TravelAgency.constante.UserConstante.*;

@Getter
@Setter
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class UserDTO {
    public UserDTO() {
        vacantaDTO = new ArrayList<>();
    }

    private Long id;
    @NotBlank(message = EROARE_NUME)
    private String nume;
    @NotBlank(message = EROARE_PRENUME)
    private String prenume;
    @Email(message = EROARE_EMAIL)
    @NotBlank(message = EROARE_EMAIL_BLANK)
    private String email;
    @Size(min = 8, message = EROARE_PAROLA)
    @NotBlank(message = EROARE_PAROLA_BLANK)
    private String parola;
    private Rol rol;
    private List<VacantaDTO> vacantaDTO;

    public void addVacanta(VacantaDTO vacanta) {
        this.vacantaDTO.add(vacanta);
    }
}
