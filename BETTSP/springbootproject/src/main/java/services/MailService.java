package services;

import data.dto.MailDTO;

public interface MailService {
    void sendEmail(MailDTO mailDTO);
}
