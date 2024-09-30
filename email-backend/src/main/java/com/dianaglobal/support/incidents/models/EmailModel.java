package com.dianaglobal.support.incidents.models;

import com.dianaglobal.support.incidents.enums.StatusEmail;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Document(collection = "incidents")
public class EmailModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String emailId;
    private String ownerRef;
    private String emailFrom;
    private String emailTo;
    private String subject;
    private String text;
    private LocalDateTime sendDataEmail;
    private StatusEmail statusEmail;

    private String company;
    private String problem;
    private String priority;
}
