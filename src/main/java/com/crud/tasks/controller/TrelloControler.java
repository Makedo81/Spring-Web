package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trello")
@CrossOrigin("*")
public class TrelloControler {

    @Autowired
    private TrelloClient trelloClient;

    @Autowired
    private TrelloService trelloService;

    @RequestMapping(method = RequestMethod.GET,value = "getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {
        return trelloService.fetchTrelloBoards();
    }

//    @RequestMapping(method = RequestMethod.GET,value = "getTrelloBoards")
//    public List<TrelloBoardDto> getTrelloBoards() {
//
//        trelloClient.getTrelloBoards().stream()
//                .filter(b->b.getName().contains("Kodilla"))
//                .forEach(b->System.out.println(b.getId()+ " " +b.getName()));
//
//        return trelloClient.getTrelloBoards();
//    }

    @RequestMapping(method = RequestMethod.POST,value = "createTrelloCard" )
    public CreatedTrelloCard createTrelloCard (@RequestBody TrelloCardDto trelloCardDto) {
        //return trelloClient.createNewCard(trelloCardDto);
        return trelloService.createTrelloCard(trelloCardDto);
    }
}






//    List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();
//        trelloBoards.forEach(trelloBoardDto -> System.out.println(trelloBoardDto.getId()
//                    + " " + trelloBoardDto.getName()));
//
//        trelloClient.getTrelloBoards().stream()
//                .filter(b->b.getName().contains("Kodilla"))
//                .forEach(b->System.out.println(b.getId()+ " " +b.getName()));

// GET request
// List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();
//  trelloBoards = trelloClient.getTrelloBoards();

//        trelloBoards.forEach(trelloBoardDto -> {
//            System.out.println(trelloBoardDto.getName() + " - " + trelloBoardDto.getId());
//            System.out.println("This board contains lists: ");
//            trelloBoardDto.getLists().forEach(trelloList ->
//                    System.out.println(trelloList.getName() + " - " + trelloList.getId() + " - " + trelloList.isClosed()));
//
//        });