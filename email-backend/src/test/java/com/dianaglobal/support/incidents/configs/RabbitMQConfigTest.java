package com.dianaglobal.support.incidents.configs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class RabbitMQConfigTest {

    private RabbitMQConfig config;

    @BeforeEach
    void setUp() {
        config = new RabbitMQConfig();
        ReflectionTestUtils.setField(config, "queueName", "incident_queue");
    }

    @Test
    void queueShouldBeDurableWithConfiguredName() {
        Queue queue = config.queue();

        assertThat(queue.getName()).isEqualTo("incident_queue");
        assertThat(queue.isDurable()).isTrue();
    }

    @Test
    void jsonMessageConverterShouldBeJacksonBased() {
        assertThat(config.jsonMessageConverter()).isInstanceOf(Jackson2JsonMessageConverter.class);
    }

    @Test
    void rabbitTemplateShouldUseJsonConverter() {
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);

        RabbitTemplate rabbitTemplate = config.rabbitTemplate(connectionFactory);

        assertThat(rabbitTemplate.getConnectionFactory()).isSameAs(connectionFactory);
        assertThat(rabbitTemplate.getMessageConverter()).isInstanceOf(Jackson2JsonMessageConverter.class);
    }

    @Test
    void listenerFactoryShouldUseExpectedSettings() {
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);

        SimpleRabbitListenerContainerFactory factory = config.rabbitListenerContainerFactory(connectionFactory);

        assertThat(ReflectionTestUtils.getField(factory, "connectionFactory")).isSameAs(connectionFactory);
        assertThat(ReflectionTestUtils.getField(factory, "acknowledgeMode")).isEqualTo(AcknowledgeMode.AUTO);
        assertThat(ReflectionTestUtils.getField(factory, "concurrentConsumers")).isEqualTo(1);
        assertThat(ReflectionTestUtils.getField(factory, "maxConcurrentConsumers")).isEqualTo(3);
        assertThat(ReflectionTestUtils.getField(factory, "prefetchCount")).isEqualTo(10);
        assertThat(ReflectionTestUtils.getField(factory, "missingQueuesFatal")).isEqualTo(false);
        assertThat(ReflectionTestUtils.getField(factory, "messageConverter")).isInstanceOf(Jackson2JsonMessageConverter.class);
    }
}
