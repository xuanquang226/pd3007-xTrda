package services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import data.dao.MailDao;
import data.dto.MailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import services.MailService;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailDao mailDao;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(MailDTO mailDTO) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailDTO.getEmail());
        mailMessage.setTo("buixuanquang2206@gmail.com");
        mailMessage.setSubject(mailDTO.getSubject());
        mailMessage.setText(mailDTO.getBody());

        mailSender.send(mailMessage);

        // MimeMessage mimeMessage = mailSender.createMimeMessage();
        // try {
        // MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
        // true);

        // mimeMessageHelper.setSubject(subject);
        // mimeMessage.setRecipient(null, null);
        // } catch (MessagingException messagingException) {
        // messagingException.getMessage();
        // }
        mailDao.saveMail(mailDTO);
    }
}
