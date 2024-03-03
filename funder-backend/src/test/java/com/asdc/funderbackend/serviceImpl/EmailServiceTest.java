package com.asdc.funderbackend.serviceImpl;

import com.asdc.funderbackend.entity.ContactUsDao;
import com.asdc.funderbackend.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;


    @Test
    void testSendEmailResetPassword() throws MessagingException {
            User user = new User();
            user.setFirstName("Shreya");
            user.setUserName("shreyakapoor@gmail.com");

            MimeMessage mimeMessage = mock(MimeMessage.class);
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
            doNothing().when(javaMailSender).send(any(MimeMessage.class));

            emailService.setUserName("shreyakapoor@gmail.com");
            emailService.sendEmail(user, "token123", "RESETPASSWORD");

            verify(javaMailSender, times(1)).send(any(MimeMessage.class));
        }

    @Test
    void testSendEmailSignup() throws MessagingException {
        User user = new User();
        user.setFirstName("Shreya");
        user.setUserName("shreyakapoor@gmail.com");

        MimeMessage mimeMessage = mock(MimeMessage.class);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        doNothing().when(javaMailSender).send(any(MimeMessage.class));

        emailService.setUserName("shreyakapoor@gmail.com");
        emailService.sendEmail(user, "token456", "SIGNUP");

        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    void testSendEmailContactUs() throws MessagingException {
        ContactUsDao contactUsDao = new ContactUsDao();
        contactUsDao.setName("Shreya Kapoor");
        contactUsDao.setEmail("shreyakapoor@gmail.cm");
        contactUsDao.setSubject("Test Subject");
        contactUsDao.setMessage("This is a test message.");

        MimeMessage mimeMessage = mock(MimeMessage.class);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        doNothing().when(javaMailSender).send(any(MimeMessage.class));

        emailService.setUserName("your_test_from_address@example.com");
        emailService.sendEmailForContactUs(contactUsDao);

        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }
}
