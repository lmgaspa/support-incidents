package com.dianaglobal.support.incidents.enums;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusEmailTest {

    @Test
    void shouldExposeExpectedValues() {
        assertThat(StatusEmail.values()).containsExactly(StatusEmail.SENT, StatusEmail.ERROR);
        assertThat(StatusEmail.valueOf("SENT")).isEqualTo(StatusEmail.SENT);
        assertThat(StatusEmail.valueOf("ERROR")).isEqualTo(StatusEmail.ERROR);
    }
}
