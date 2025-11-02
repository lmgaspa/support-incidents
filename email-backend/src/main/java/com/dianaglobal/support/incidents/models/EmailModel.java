package com.dianaglobal.support.incidents.models;

import com.dianaglobal.support.incidents.enums.StatusEmail;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Document(collection = "tickets")
public class EmailModel {
    @Id
    private String id;

    private String ownerRef;

    private String emailFrom;
    private String emailTo;

    private String subject;
    private String text;

    private String company;
    private String problem;
    private String priority;

    private LocalDateTime sendDataEmail; // setado pelo serviço
    private StatusEmail statusEmail;     // SENT / ERROR
}
