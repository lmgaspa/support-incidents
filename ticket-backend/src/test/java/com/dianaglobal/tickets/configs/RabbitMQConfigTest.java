package com.dianaglobal.tickets.configs;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import static org.assertj.core.api.Assertions.assertThat;

class RabbitMQConfigTest {

    private final RabbitMQConfig rabbitMQConfig = new RabbitMQConfig();

    @Test
    void queueCreatesDurableIncidentQueue() {
        Queue queue = rabbitMQConfig.queue();

        assertThat(queue.getName()).isEqualTo(RabbitMQConfig.QUEUE_NAME);
        assertThat(queue.isDurable()).isTrue();
    }

    @Test
    void messageConverterCreatesJacksonConverter() {
        Jackson2JsonMessageConverter converter = rabbitMQConfig.messageConverter();

        assertThat(converter).isNotNull();
    }
}
