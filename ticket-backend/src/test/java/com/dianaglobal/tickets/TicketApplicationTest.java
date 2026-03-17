package com.dianaglobal.tickets;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class TicketApplicationTest {

    @Test
    void mainStartsApplicationWithNonWebMode() {
        assertThatCode(() -> TicketApplication.main(new String[]{"--spring.main.web-application-type=none"}))
                .doesNotThrowAnyException();
    }
}
