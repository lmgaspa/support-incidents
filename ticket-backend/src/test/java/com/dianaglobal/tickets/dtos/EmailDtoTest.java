package com.dianaglobal.tickets.dtos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailDtoTest {

    @Test
    void dataAnnotationExposesGettersAndSetters() {
        EmailDto emailDto = new EmailDto();

        emailDto.setOwnerRef("owner-1");
        emailDto.setEmailFrom("from@example.com");
        emailDto.setEmailTo("to@example.com");
        emailDto.setSubject("Subject");
        emailDto.setText("Body");
        emailDto.setCompany("Company");
        emailDto.setProblem("Problem");
        emailDto.setPriority("HIGH");

        assertThat(emailDto.getOwnerRef()).isEqualTo("owner-1");
        assertThat(emailDto.getEmailFrom()).isEqualTo("from@example.com");
        assertThat(emailDto.getEmailTo()).isEqualTo("to@example.com");
        assertThat(emailDto.getSubject()).isEqualTo("Subject");
        assertThat(emailDto.getText()).isEqualTo("Body");
        assertThat(emailDto.getCompany()).isEqualTo("Company");
        assertThat(emailDto.getProblem()).isEqualTo("Problem");
        assertThat(emailDto.getPriority()).isEqualTo("HIGH");
    }
}
