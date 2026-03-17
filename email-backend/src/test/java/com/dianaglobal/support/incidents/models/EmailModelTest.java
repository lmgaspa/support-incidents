package com.dianaglobal.support.incidents.models;

import com.dianaglobal.support.incidents.enums.StatusEmail;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EmailModelTest {

    @Test
    void builderAndAccessorsShouldRoundTripFields() {
        LocalDateTime now = LocalDateTime.now();

        EmailModel model = EmailModel.builder()
                .id("id-1")
                .ownerRef("owner-1")
                .emailFrom("from@test.com")
                .emailTo("to@test.com")
                .subject("subject")
                .text("body")
                .company("ACME")
                .problem("outage")
                .priority("high")
                .sendDataEmail(now)
                .statusEmail(StatusEmail.SENT)
                .build();

        assertThat(model.getId()).isEqualTo("id-1");
        assertThat(model.getOwnerRef()).isEqualTo("owner-1");
        assertThat(model.getEmailFrom()).isEqualTo("from@test.com");
        assertThat(model.getEmailTo()).isEqualTo("to@test.com");
        assertThat(model.getSubject()).isEqualTo("subject");
        assertThat(model.getText()).isEqualTo("body");
        assertThat(model.getCompany()).isEqualTo("ACME");
        assertThat(model.getProblem()).isEqualTo("outage");
        assertThat(model.getPriority()).isEqualTo("high");
        assertThat(model.getSendDataEmail()).isEqualTo(now);
        assertThat(model.getStatusEmail()).isEqualTo(StatusEmail.SENT);
    }
}
