package com.dianaglobal.tickets.dtos;

import lombok.Data;

@Data
public class EmailDto {
    private String ownerRef;
    private String emailFrom;
    private String emailTo;
    private String subject;
    private String text;
    private String company;
    private String problem;
    private String priority;
}
