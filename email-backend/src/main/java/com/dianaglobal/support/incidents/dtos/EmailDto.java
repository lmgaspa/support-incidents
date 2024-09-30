package com.dianaglobal.support.incidents.dtos;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class EmailDto {

    @NotBlank
    private String ownerRef;

    @NotBlank
    @Email
    private String emailFrom;

    @NotBlank
    @Email
    private String emailTo;

    @NotBlank
    private String subject;

    @NotBlank
    private String text;

    @NotBlank
    private String company;

    @NotBlank
    private String problem;

    @NotBlank
    private String priority;
}
