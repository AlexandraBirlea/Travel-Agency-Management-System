package TravelAgency.repositories;

import TravelAgency.TravelAgency.entities.EmailPayload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailPayloadRepository extends JpaRepository<EmailPayload, Long> {
}
