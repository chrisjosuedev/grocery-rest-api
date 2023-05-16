package dev.chrisjosue.groceryrestapi.service;

import dev.chrisjosue.groceryrestapi.dto.requests.mail.MailDto;

public interface IEmailSenderService {
    void sendEmail(MailDto mailDto);
}
