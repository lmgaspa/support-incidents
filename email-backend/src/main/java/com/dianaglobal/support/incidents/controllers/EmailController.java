package com.dianaglobal.support.incidents.controllers;

import com.dianaglobal.support.incidents.dtos.EmailDto;
import com.dianaglobal.support.incidents.models.EmailModel;
import com.dianaglobal.support.incidents.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/emails", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmailModel> sendEmail(@RequestBody @Valid EmailDto emailDto) {
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        EmailModel savedEmail = emailService.sendEmail(emailModel);
        return ResponseEntity.status(201).body(savedEmail);
    }

    @GetMapping
    public ResponseEntity<List<EmailModel>> getAllEmails() {
        return ResponseEntity.ok(emailService.getAllEmails());
    }

    @GetMapping("/by-email")
    public ResponseEntity<List<EmailModel>> getEmailsByEmailTo(@RequestParam String emailTo) {
        return ResponseEntity.ok(emailService.findByEmailTo(emailTo));
    }
}
