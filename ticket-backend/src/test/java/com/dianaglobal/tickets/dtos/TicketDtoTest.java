package com.dianaglobal.tickets.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TicketDtoTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidator() {
        validatorFactory.close();
    }

    @Test
    void dataAnnotationExposesGettersAndSetters() {
        TicketDto ticketDto = new TicketDto();

        ticketDto.setUser("Diana");
        ticketDto.setCompany("Global Corp");
        ticketDto.setEmailFrom("from@example.com");
        ticketDto.setEmailTo("to@example.com");
        ticketDto.setProblem("Login");
        ticketDto.setDescription("Users cannot log in.");
        ticketDto.setPriority("HIGH");

        assertThat(ticketDto.getUser()).isEqualTo("Diana");
        assertThat(ticketDto.getCompany()).isEqualTo("Global Corp");
        assertThat(ticketDto.getEmailFrom()).isEqualTo("from@example.com");
        assertThat(ticketDto.getEmailTo()).isEqualTo("to@example.com");
        assertThat(ticketDto.getProblem()).isEqualTo("Login");
        assertThat(ticketDto.getDescription()).isEqualTo("Users cannot log in.");
        assertThat(ticketDto.getPriority()).isEqualTo("HIGH");
    }

    @Test
    void validationAcceptsValidTicket() {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setUser("Diana");
        ticketDto.setCompany("Global Corp");
        ticketDto.setEmailFrom("from@example.com");
        ticketDto.setEmailTo("to@example.com");
        ticketDto.setProblem("Login");
        ticketDto.setDescription("Users cannot log in.");
        ticketDto.setPriority("HIGH");

        Set<ConstraintViolation<TicketDto>> violations = validator.validate(ticketDto);

        assertThat(violations).isEmpty();
    }

    @Test
    void validationRejectsBlankFieldsAndInvalidEmails() {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setUser("");
        ticketDto.setCompany("");
        ticketDto.setEmailFrom("invalid");
        ticketDto.setEmailTo("invalid");
        ticketDto.setProblem("");
        ticketDto.setDescription("");
        ticketDto.setPriority("");

        Set<ConstraintViolation<TicketDto>> violations = validator.validate(ticketDto);

        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString())
                .containsExactlyInAnyOrder(
                        "user",
                        "company",
                        "emailFrom",
                        "emailTo",
                        "problem",
                        "description",
                        "priority"
                );
    }
}
