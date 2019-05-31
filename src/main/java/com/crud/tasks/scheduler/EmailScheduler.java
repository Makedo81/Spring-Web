package com.crud.tasks.scheduler;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.trello.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String SUBJECT = "Task: Once a day email";

//  @Scheduled(fixedDelay = 50000)
    @Scheduled(cron = " 00 10 * * * * ")
    public void sendInformationEmail() {
        long size = taskRepository.count();
        simpleEmailService.send
                (new Mail(adminConfig.getAdminMail(),
                                SUBJECT,
                                "Currently in database you got "
                                + size + " tasks ",
                                ""));
    }
}
