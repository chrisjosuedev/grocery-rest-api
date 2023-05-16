package dev.chrisjosue.groceryrestapi.dto.requests.mail;

import lombok.Data;

@Data
public class MailDto {
    private String to;
    private String subject;
    private String message;

    public MailDto(String to, String message) {
        this.to = to;
        this.subject = "PASSWORD RECOVERY";
        this.message = message;
    }
}
