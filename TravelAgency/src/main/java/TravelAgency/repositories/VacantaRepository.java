package TravelAgency.repositories;

import TravelAgency.TravelAgency.entities.TipVacanta;
import TravelAgency.TravelAgency.entities.Vacanta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacantaRepository extends JpaRepository<Vacanta, Long> {
    List<Vacanta> findByTipVacanta(TipVacanta tipVacanta);
    @Query("SELECT v FROM Vacanta v WHERE CONCAT(v.tipVacanta, ' ', v.destinatie) LIKE %:search%")
    List<Vacanta> searchVacante(@Param("search") String search);

}
