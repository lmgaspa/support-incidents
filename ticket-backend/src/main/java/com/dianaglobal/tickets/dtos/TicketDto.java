package com.dianaglobal.tickets.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@Schema(name = "TicketRequest", description = "Support ticket creation payload")
public class TicketDto {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(example = "Diana Gasparetto")
    private String user;

    @NotBlank
    @Schema(example = "Global Corp")
    private String company;

    @NotBlank
    @Email
    @Schema(example = "user@example.com")
    private String emailFrom;

    @NotBlank
    @Email
    @Schema(example = "support@example.com")
    private String emailTo;

    @NotBlank
    @Schema(example = "Login issue")
    private String problem;

    @NotBlank
    @Schema(example = "User cannot authenticate after password reset.")
    private String description;

    @NotBlank
    @Schema(example = "HIGH")
    private String priority;
}
