package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTestSuite {

    @InjectMocks
    private TrelloMapper trelloMapper;
    @InjectMocks
    private TrelloValidator trelloValidator;

    @Test
    public void mappingCardTest() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Task",
                "task description",
                "top",
                "test_id");

        TrelloCard trelloCardDomain = new TrelloCard(
                trelloCardDto.getName(),
                trelloCardDto.getDescription(),
                trelloCardDto.getPos(),
                trelloCardDto.getListId());

        //When
        TrelloCardDto cardDTO = trelloMapper.mapToCardDto(trelloCardDomain);
        TrelloCard cardDomain = trelloMapper.mapToCard(cardDTO);
        //Then
        assertEquals("Task", cardDTO.getName());
        assertEquals(cardDTO.getName(), cardDomain.getName());
    }

    @Test
    public void mappingBoardTest() {
        //Given
        List<TrelloList> lists = new ArrayList<>();
        lists.add(new TrelloList("1","test_name",true));

        List<TrelloListDto> listsDto = new ArrayList<>();
        listsDto.add(new TrelloListDto("1","test_name",true));

        lists = trelloMapper.mapToList(listsDto);

        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("test_id", "test_board",lists));
        trelloBoards.stream()
                .map(trelloBoard -> new TrelloBoard(trelloBoard.getId(),trelloBoard.getName(),trelloBoard.getLists()))
                .collect(toList());
        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoards);
        List<TrelloBoard> trelloBoardsDomain = trelloMapper.mapToBoards(trelloBoardsDto);
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(trelloBoardsDomain);
        List<TrelloBoardDto> test =  trelloMapper.mapToBoardsDto(filteredBoards);

        //Then
        assertEquals(1,trelloBoardsDto.size());
        assertEquals("test_board",trelloBoardsDto.get(0).getName());
        assertEquals("test_name",trelloBoardsDto.get(0).getLists().get(0).getName());
        assertEquals("test_board",trelloBoardsDomain.get(0).getName());
        assertEquals("test_board",test.get(0).getName());
    }
}
