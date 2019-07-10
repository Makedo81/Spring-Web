package com.crud.tasks.scheduler;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.MailCreatorService;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.trello.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    @Autowired
    private SimpleEmailService simpleEmailService;
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    MailCreatorService mailCreatorService;

    private static final String SUBJECT = "Task: Once a day email";

    @Scheduled(fixedDelay = 100000)
//   @Scheduled(cron = " 00 10 * * * * ")
    public void sendInformationEmail() throws MailParseException {
        long size = taskRepository.count();
        Mail mail = new Mail(adminConfig.getAdminMail(),
                SUBJECT,
                "Currently in database you got " + size + " tasks ");
        simpleEmailService.sendMail(mail);

    }
}
