package com.dianaglobal.tickets.configs;

import com.dianaglobal.tickets.controllers.TicketController;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
@Import(WebConfig.class)
class WebConfigMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    void corsAllowsConfiguredFrontendOrigin() throws Exception {
        mockMvc.perform(options("/send-ticket")
                        .header("Origin", "https://incidentticketform.vercel.app")
                        .header("Access-Control-Request-Method", "POST"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "https://incidentticketform.vercel.app"))
                .andExpect(header().string("Access-Control-Allow-Credentials", "true"));
    }

    @Test
    void sendTicketRejectsInvalidPayload() throws Exception {
        mockMvc.perform(post("/send-ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "user": "",
                                  "company": "",
                                  "emailFrom": "invalid",
                                  "emailTo": "still-invalid",
                                  "problem": "",
                                  "description": "",
                                  "priority": ""
                                }
                                """))
                .andExpect(status().isBadRequest());
    }
}
