package com.dianaglobal.support.incidents.models;

import com.dianaglobal.support.incidents.enums.StatusEmail;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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

    public EmailModel() {
    }

    public EmailModel(String id, String ownerRef, String emailFrom, String emailTo, String subject, String text,
                      String company, String problem, String priority, LocalDateTime sendDataEmail,
                      StatusEmail statusEmail) {
        this.id = id;
        this.ownerRef = ownerRef;
        this.emailFrom = emailFrom;
        this.emailTo = emailTo;
        this.subject = subject;
        this.text = text;
        this.company = company;
        this.problem = problem;
        this.priority = priority;
        this.sendDataEmail = sendDataEmail;
        this.statusEmail = statusEmail;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerRef() {
        return ownerRef;
    }

    public void setOwnerRef(String ownerRef) {
        this.ownerRef = ownerRef;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDateTime getSendDataEmail() {
        return sendDataEmail;
    }

    public void setSendDataEmail(LocalDateTime sendDataEmail) {
        this.sendDataEmail = sendDataEmail;
    }

    public StatusEmail getStatusEmail() {
        return statusEmail;
    }

    public void setStatusEmail(StatusEmail statusEmail) {
        this.statusEmail = statusEmail;
    }

    public static final class Builder {
        private String id;
        private String ownerRef;
        private String emailFrom;
        private String emailTo;
        private String subject;
        private String text;
        private String company;
        private String problem;
        private String priority;
        private LocalDateTime sendDataEmail;
        private StatusEmail statusEmail;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder ownerRef(String ownerRef) {
            this.ownerRef = ownerRef;
            return this;
        }

        public Builder emailFrom(String emailFrom) {
            this.emailFrom = emailFrom;
            return this;
        }

        public Builder emailTo(String emailTo) {
            this.emailTo = emailTo;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder company(String company) {
            this.company = company;
            return this;
        }

        public Builder problem(String problem) {
            this.problem = problem;
            return this;
        }

        public Builder priority(String priority) {
            this.priority = priority;
            return this;
        }

        public Builder sendDataEmail(LocalDateTime sendDataEmail) {
            this.sendDataEmail = sendDataEmail;
            return this;
        }

        public Builder statusEmail(StatusEmail statusEmail) {
            this.statusEmail = statusEmail;
            return this;
        }

        public EmailModel build() {
            return new EmailModel(id, ownerRef, emailFrom, emailTo, subject, text, company, problem, priority,
                    sendDataEmail, statusEmail);
        }
    }
}
