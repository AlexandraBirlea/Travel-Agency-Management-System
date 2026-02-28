package TravelAgency.repositories;

import TravelAgency.TravelAgency.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByNume(String name);
}
