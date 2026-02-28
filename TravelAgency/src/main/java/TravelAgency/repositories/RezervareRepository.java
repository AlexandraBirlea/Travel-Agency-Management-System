package TravelAgency.repositories;

import TravelAgency.TravelAgency.entities.Rezervare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RezervareRepository extends JpaRepository<Rezervare, Long> {
    List<Rezervare> findByUtilizatorId(Long id);
    List<Rezervare> findByVacantaId(long id);
}