package com.crud.tasks.service;

import com.crud.tasks.trello.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    @Autowired
    AdminConfig adminConfig;

    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provide connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");
        context.setVariable("message",message);
        context.setVariable("tasks_url","http://localhost:8888/tasks_frontend/");
        context.setVariable("button","Visit website");
        context.setVariable("admin_config",adminConfig);
        context.setVariable("admin_name",adminConfig.getAdminName());
        context.setVariable("company_name",adminConfig.getCompanyName());
        context.setVariable("company_email",adminConfig.getCompanyEmail());
        context.setVariable("show_button",true);
        context.setVariable("is_friend",true);
        context.setVariable("show_message",false);
        context.setVariable("application_functionality",functionality);
        return templateEngine.process("mail/created-trello-card-mail",context);
    }

    public String buildTaskCreatedEmail(String message) {
        Context context = new Context();
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Daily control tasks number in database by email");
        List<String> information = new ArrayList<>();
        information.add(adminConfig.getAppName());
        information.add(adminConfig.getAppDescription());
        information.add("version: " + adminConfig.getAppVersion());
        context.setVariable("message",message);
        context.setVariable("tasks_url","http://localhost:8888/tasks_frontend/");
        context.setVariable("button","Visit website");
        context.setVariable("admin_config",adminConfig);
        context.setVariable("admin_name",adminConfig.getAdminName());
        context.setVariable("company_name",adminConfig.getCompanyName());
        context.setVariable("company_email",adminConfig.getCompanyEmail());
        context.setVariable("show_button",true);
        context.setVariable("is_friend",true);
        context.setVariable("show_preview_message",false);
        context.setVariable("application_functionality",functionality);
        context.setVariable("application_information",information);
        return templateEngine.process("mail/new-task-added-mail",context);
    }
}
