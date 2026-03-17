package com.dianaglobal.support.incidents;

import com.dianaglobal.support.incidents.repositories.EmailRepository;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.data.mongodb.uri=mongodb://localhost:27017/testdb",
        "spring.rabbitmq.addresses=amqp://guest:guest@localhost:5672",
        "spring.rabbitmq.queue=test-queue",
        "spring.mail.host=localhost",
        "spring.mail.port=2525",
        "spring.mail.username=test",
        "spring.mail.password=test",
        "spring.rabbitmq.listener.simple.auto-startup=false"
})
class SupportIncidentsApplicationTest {

    @MockBean
    private EmailRepository emailRepository;

    @MockBean
    private ConnectionFactory connectionFactory;

    @MockBean
    private JavaMailSender javaMailSender;

    @Test
    void contextLoads(ApplicationContext applicationContext) {
        assertThat(applicationContext).isNotNull();
    }
}
