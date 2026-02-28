package TravelAgency.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "email_payload")
public class EmailPayload {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "First Name must not be blank!")
    @Size(min=1, max=50, message = "First name should be between 1 and 50 characters!")
    private String firstName;

    @NotBlank(message = "Last Name must not be blank!")
    @Size(min=1, max=50, message = "Last Name should be between 1 and 50 characters!")
    private String lastName;

    @NotNull(message = "Email should not be null!")
    @Email(message = "Invalid email format!")
    private String recipientEmail;

    @NotNull(message = "Subject must not be null!")
    @NotBlank(message = "Subject must not be blank!")
    private String subject;

    @NotNull(message = "Body must not be null!")
    @NotBlank(message = "Body must not be blank!")
    private String body;
}
