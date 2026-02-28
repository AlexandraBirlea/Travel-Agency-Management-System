package TravelAgency.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static TravelAgency.TravelAgency.constante.UserConstante.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Table(name = "`user`")
public class User implements UserDetails {

    public User(){
        vacanta = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = EROARE_NUME)
    private String nume;

    @NotBlank(message = EROARE_PRENUME)
    private String prenume;

    @Email(message = EROARE_EMAIL)
    @NotBlank(message = EROARE_EMAIL_BLANK)
    private String email;

    @NotBlank(message = EROARE_PAROLA_BLANK)
    private String parola;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Rol rol;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_vacanta",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "vacanta_id")
    )
    private List<Vacanta> vacanta;

    @OneToMany(mappedBy = "utilizator", cascade = CascadeType.ALL)
    private List<Rezervare> rezervari;

    public void addVacanta(Vacanta vacanta){
        this.vacanta.add(vacanta);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.getNume()));
    }

    @Override
    public String getPassword() {
        return parola;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void removeAssociation(Vacanta vacanta){
        vacanta.getUser().remove(this);
    }
}
