package services;

import data.dto.MailDTO;

public interface MailService {
    void sendEmail(MailDTO mailDTO);

    void sendEmailVerification(String email, String token);
}
