package dev.chrisjosue.groceryrestapi.service.imp;

import dev.chrisjosue.groceryrestapi.dto.requests.mail.MailDto;
import dev.chrisjosue.groceryrestapi.service.IEmailSenderService;
import dev.chrisjosue.groceryrestapi.utils.exceptions.MyBusinessException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSender implements IEmailSenderService {
    @Value("${env.MAIL}")
    private String mailFrom;
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(MailDto mailDto) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, "utf-8");

            mailMessage.setFrom(mailFrom, "CJ Dev Support");
            mailMessage.setTo(mailDto.getTo());
            mailMessage.setSubject(mailDto.getSubject());
            mailMessage.setText(mailDto.getMessage(), true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new MyBusinessException("Something failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
