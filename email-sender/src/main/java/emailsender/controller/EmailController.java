package emailsender.controller;

import emailsender.model.EmailRequestDto;
import emailsender.model.ResponseDto;
import emailsender.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import static emailsender.constant.UUIDToken.FIRST_TOKEN;
import static emailsender.constant.UUIDToken.SECOND_TOKEN;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<ResponseDto> sendEmail(@RequestHeader("Authorization") String token, @Valid @RequestBody EmailRequestDto emailRequestDto, BindingResult bindingResult){
        ResponseDto responseDto = new ResponseDto();

        if(!isTokenValid(token)){
            responseDto.setMessage("Invalid authorization token");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
        }

        if(bindingResult.hasErrors()){
            responseDto.setMessage(bindingResult.getFieldError().getDefaultMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }

        emailService.sendEmail(emailRequestDto);

        responseDto.setMessage("Email successfully sent!");

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public boolean isTokenValid(String token){
        String validToken = FIRST_TOKEN + SECOND_TOKEN;

        return validToken.equals(token);
    }
}
