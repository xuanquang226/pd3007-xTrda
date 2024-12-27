package services.impl;

import java.util.Base64;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import data.dao.MailDao;
import data.dto.MailDTO;
import services.MailService;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailDao mailDao;
    @Autowired
    private JavaMailSender mailSender;

    @Value("${HOST}")
    private String host;

    @Override
    public void sendEmail(MailDTO mailDTO) {

        // Send to admin
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("buixuanquang2206@gmail.com");
        mailMessage.setSubject(mailDTO.getSubject());
        mailMessage.setText(mailDTO.getBody());
        mailSender.send(mailMessage);

        Random random = new Random();
        int randomNumber = random.nextInt(100);
        String input = mailDTO.getSubject() + randomNumber;
        String encoded = Base64.getEncoder().encodeToString(input.getBytes());
        String ticket = encoded.substring(0, 4);

        mailDTO.setTicket(ticket);
        mailDao.saveMail(mailDTO);

        // Send to user
        SimpleMailMessage mailMessage2 = new SimpleMailMessage();
        mailMessage2.setTo(mailDTO.getEmail());
        mailMessage2.setSubject(mailDTO.getSubject());
        String contentMailRep = """
                Ticket: %s\n\n\n
                Cảm ơn bạn đã liên hệ với chúng tôi yêu cầu trên sẽ được xử lý trong thời gian sớm nhất.
                """.formatted(ticket);
        mailMessage2.setText(contentMailRep);
        mailSender.send(mailMessage2);
    }

    @Override
    public void sendEmailVerification(String email, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Xác thực tài khoản Xiaotrada của bạn");

        String contentMail = """
                Nhấn vào link sau để xác thực tài khoản của bạn:
                http://%s/api/account/verify?token=%s
                """.formatted(host, token);
        mailMessage.setText(contentMail);
        mailSender.send(mailMessage);
    }
}
