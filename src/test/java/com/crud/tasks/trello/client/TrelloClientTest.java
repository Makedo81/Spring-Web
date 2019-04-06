package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public void init() {
        //given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getTrelloUsername()).thenReturn("dominikastankiewicz");
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id","test_board", new ArrayList<>());
        URI uri = new URI("http://test.com/members/username/boards?key=test&token=test&fields=id&fields=name&lists=all");
        when(restTemplate.getForObject(uri,TrelloBoardDto[].class)).
                thenReturn(trelloBoards);
        //when
        List<TrelloBoardDto>fetchedTrelloBoards = trelloClient.getTrelloBoards();
        //then
//        assertEquals(1,fetchedTrelloBoards.size());
//        assertEquals("test_id",fetchedTrelloBoards.get(0).getId());
//        assertEquals("test_board",fetchedTrelloBoards.get(0).getName());
//        assertEquals(new ArrayList<>(),fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void createTrelloCard() throws URISyntaxException {

        //given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Task","task description","top","test_id");

        URI uri = new URI("http://test.com/cards?key=test&token=test&" +
                "name=Test%20task&desc=task%20Description&pos=top&idList=test_id");

        CreatedTrelloCard createdTrelloCard = new CreatedTrelloCard(
                "1",
                "Test task",
                "http://test.com"
        );
        //when
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);
        when(restTemplate.postForObject(uri,null,CreatedTrelloCard.class)).thenReturn(createdTrelloCard);
        //then
        assertEquals("1",createdTrelloCard.getId());
        assertEquals("Test task",createdTrelloCard.getName());
        assertEquals("http://test.com",createdTrelloCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList()throws URISyntaxException {
         //given
        TrelloBoardDto[] trelloBoards = null;
        List<TrelloBoardDto> fetchedTrelloBoards = Arrays.asList(ofNullable(trelloBoards).orElse(new TrelloBoardDto[0]));
        URI uri = new URI("http://test.com/members/dominikastankiewicz/boards?key=test&token=test&fields=name,id&lists=all");
        when(restTemplate.getForObject(uri,TrelloBoardDto[].class)).thenReturn(trelloBoards);

        assertEquals(0,fetchedTrelloBoards.size());
        assertEquals(fetchedTrelloBoards,trelloClient.getTrelloBoards());
        assertEquals(fetchedTrelloBoards.isEmpty(),trelloClient.getTrelloBoards().isEmpty());
    }
}
