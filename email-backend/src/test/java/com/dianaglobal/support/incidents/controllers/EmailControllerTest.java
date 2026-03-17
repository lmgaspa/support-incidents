package com.dianaglobal.support.incidents.controllers;

import com.dianaglobal.support.incidents.models.EmailModel;
import com.dianaglobal.support.incidents.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmailController.class)
class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Test
    void sendEmailShouldReturnCreated() throws Exception {
        EmailModel saved = EmailModel.builder()
                .ownerRef("owner-1")
                .emailFrom("from@test.com")
                .emailTo("to@test.com")
                .subject("subject")
                .text("body")
                .company("ACME")
                .problem("outage")
                .priority("high")
                .build();
        when(emailService.sendEmail(any(EmailModel.class))).thenReturn(saved);

        mockMvc.perform(post("/api/emails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "ownerRef":"owner-1",
                                  "emailFrom":"from@test.com",
                                  "emailTo":"to@test.com",
                                  "subject":"subject",
                                  "text":"body",
                                  "company":"ACME",
                                  "problem":"outage",
                                  "priority":"high"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.emailTo").value("to@test.com"))
                .andExpect(jsonPath("$.ownerRef").value("owner-1"));

        verify(emailService).sendEmail(any(EmailModel.class));
    }

    @Test
    void sendEmailShouldValidatePayload() throws Exception {
        mockMvc.perform(post("/api/emails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "ownerRef":"",
                                  "emailFrom":"invalid",
                                  "emailTo":"",
                                  "subject":"",
                                  "text":"",
                                  "company":"",
                                  "problem":"",
                                  "priority":""
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllEmailsShouldReturnPayload() throws Exception {
        when(emailService.getAllEmails()).thenReturn(List.of(
                EmailModel.builder().emailTo("a@test.com").subject("A").build(),
                EmailModel.builder().emailTo("b@test.com").subject("B").build()
        ));

        mockMvc.perform(get("/api/emails"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].emailTo").value("a@test.com"))
                .andExpect(jsonPath("$[1].subject").value("B"));
    }

    @Test
    void getEmailsByEmailToShouldReturnFilteredPayload() throws Exception {
        when(emailService.findByEmailTo("to@test.com")).thenReturn(List.of(
                EmailModel.builder().emailTo("to@test.com").subject("subject").build()
        ));

        mockMvc.perform(get("/api/emails/by-email").param("emailTo", "to@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].emailTo").value("to@test.com"));
    }
}
