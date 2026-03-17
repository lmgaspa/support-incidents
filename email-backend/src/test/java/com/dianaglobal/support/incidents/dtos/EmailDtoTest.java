package com.dianaglobal.support.incidents.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class EmailDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void shouldAcceptValidPayload() {
        EmailDto dto = new EmailDto();
        dto.setOwnerRef("owner-1");
        dto.setEmailFrom("from@test.com");
        dto.setEmailTo("to@test.com");
        dto.setSubject("subject");
        dto.setText("body");
        dto.setCompany("ACME");
        dto.setProblem("outage");
        dto.setPriority("high");

        Set<ConstraintViolation<EmailDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldRejectBlankAndInvalidFields() {
        EmailDto dto = new EmailDto();
        dto.setOwnerRef(" ");
        dto.setEmailFrom("invalid");
        dto.setEmailTo("");

        Set<ConstraintViolation<EmailDto>> violations = validator.validate(dto);

        assertThat(violations).hasSizeGreaterThanOrEqualTo(8);
        assertThat(violations).extracting(v -> v.getPropertyPath().toString())
                .contains("ownerRef", "emailFrom", "emailTo", "subject", "text", "company", "problem", "priority");
    }
}
