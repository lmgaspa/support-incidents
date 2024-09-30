package com.dianaglobal.tickets.dtos;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;
@Data
public class TicketDto {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String user;

    @NotBlank
    private String company;

    @NotBlank
    @Email
    private String emailTo;

    @NotBlank
    private String problem;

    @NotBlank
    private String description;

    @NotBlank
    private String priority;
}
