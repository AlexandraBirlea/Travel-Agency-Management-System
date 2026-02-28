package TravelAgency.services;

import TravelAgency.TravelAgency.entities.EmailPayload;

public interface EmailService {
    void sendEmail (EmailPayload emailPayload);
}
