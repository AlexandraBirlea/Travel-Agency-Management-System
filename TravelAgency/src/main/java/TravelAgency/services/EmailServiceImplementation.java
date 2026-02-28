package TravelAgency.services;

import TravelAgency.TravelAgency.dtos.EmailRequestResponseDto;
import TravelAgency.TravelAgency.entities.EmailPayload;
import TravelAgency.TravelAgency.repositories.EmailPayloadRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailServiceImplementation implements EmailService{

    private final EmailPayloadRepository emailPayloadRepository;

    private static final String EMAIL_SERVICE_URL = "http://localhost:8081/email/send";
    private static final String AUTH_TOKEN = "5bb93748-b815-4948-af25-a7404c852dc-1ae0e8a1-ae06-440b-a862-847e45f31a15";

    public EmailServiceImplementation(EmailPayloadRepository emailPayloadRepository) {
        this.emailPayloadRepository = emailPayloadRepository;
    }

    @Override
    public void sendEmail(EmailPayload emailPayload) {
        EmailPayload payload = emailPayloadRepository.save(emailPayload);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", AUTH_TOKEN);

        HttpEntity<EmailPayload> request = new HttpEntity<>(payload, headers);

        ResponseEntity<EmailRequestResponseDto> responseEntity = restTemplate.exchange(
                EMAIL_SERVICE_URL,
                HttpMethod.POST,
                request,
                EmailRequestResponseDto.class
        );


        String responseMessage = responseEntity.getBody().getMessage();

        System.out.println(responseMessage);
    }
}
