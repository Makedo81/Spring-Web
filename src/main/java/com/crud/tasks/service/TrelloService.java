package com.crud.tasks.service;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static java.util.Optional.ofNullable;

@Service
public class TrelloService {

    @Autowired
    private TrelloClient trelloClient;

    @Autowired
    private SimpleEmailService emailService;

    @Autowired
    private AdminConfig adminConfig;

    private static final String SUBJECT = "Task: New trello card";

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCard createTrelloCard(final TrelloCardDto trelloCardDto) {
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);
        ofNullable(newCard).ifPresent(card->emailService.send
                (
                new Mail(adminConfig.getAdminMail(),
                SUBJECT,"New card: "
                + trelloCardDto.getName()+ "has been created on your Trello account","")));

        return  newCard;
    }
}
//        emailService.send(new Mail(adminConfig.getAdminMail(),SUBJECT,"New card: "
//                + trelloCardDto.getName()+ "has been created on your Trello account"));