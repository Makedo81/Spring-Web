package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private MailCreatorService mailCreatorService;

    public void sendMail(final Mail mail) {
        LOGGER.info("Starting email preparation");
        try {
            javaMailSender.send(createMimeMessage(mail));
            LOGGER.info("Email has been sent");
        }
        catch (MailException e) {
            LOGGER.error("Fail to process email sending",e.getMessage(),e);
        }
    }

    public MimeMessagePreparator createMimeMessage(final Mail mail) {
        long size = taskRepository.count();
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            if (size==1) {
                messageHelper.setText(mailCreatorService.buildTaskCreatedEmail(
                        "Currently in database you got " + size + " task "),true);
            }else {
            messageHelper.setText(mailCreatorService.buildTaskCreatedEmail(mail.getMessage()),true);}
        };
    }

    /**
     * SimpleMailMessage - sending
     */

    public void send(final Mail mail) {
        LOGGER.info("Starting email preparation");
        try {
            SimpleMailMessage mailMessage = createMessage(mail);
            javaMailSender.send(mailMessage);
            LOGGER.info("Email has been sent");
        }
        catch (MailException e) {
            LOGGER.error("Fail to process email sending",e.getMessage(),e);
        }
    }

    public SimpleMailMessage createMessage(final Mail mail) {
        long size = taskRepository.count();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        if (size==1) {
            mailMessage.setText("Currently in database you got " + size + " task ");
        }else if (size==0){
            mailMessage.setText(mail.getMessage());
        }
        return mailMessage;
    }
}