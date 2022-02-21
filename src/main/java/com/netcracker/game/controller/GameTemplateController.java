package com.netcracker.game.controller;

import com.netcracker.game.data.model.GameTemplate;
import com.netcracker.game.data.repository.GameTemplateRepository;
import com.netcracker.game.exception.BadInputDataException;
import com.netcracker.game.services.dto.gametemplate.GameTemplateCreationDto;
import com.netcracker.game.services.dto.gametemplate.GameTemplateDto;
import com.netcracker.game.services.dto.pagination.PageDto;
import com.netcracker.game.services.dto.pagination.PageRequestDto;
import com.netcracker.game.services.service.IGameTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/templates")
public class GameTemplateController {

    @Autowired
    GameTemplateRepository gameTemplateRepository;

    private final IGameTemplateService gameTemplateService;

    public GameTemplateController(IGameTemplateService gameTemplateService) {
        this.gameTemplateService = gameTemplateService;
    }

    @PostMapping("/")
    public void addGameTemplate(@RequestBody GameTemplateCreationDto dto) throws BadInputDataException {
        gameTemplateService.addGameTemplate(dto);
    }

    @GetMapping("/")
    public PageDto<GameTemplateDto> getAll(@RequestParam String searchText,
                                           PageRequestDto pageRequest){
        return gameTemplateService.getAll(searchText, pageRequest);
    }

    @GetMapping("/all/{gameUserId}")
    public List<GameTemplateDto> getAllByGameUser(@PathVariable("gameUserId") Integer gameUserId) {
        return gameTemplateService.getAllByGameUser(gameUserId);
    }

    @PutMapping("/edit")
    public void editGameTemplate(@RequestBody GameTemplateDto gameTemplateDto){
        gameTemplateService.editGameTemplate(gameTemplateDto);
    }

    @DeleteMapping("/{gameId}")
    public void deleteGameTemplate(@PathVariable("gameId") Integer gameTemplateId){
        gameTemplateService.deleteGameTemplate(gameTemplateId);
    }

    @GetMapping("/name/{gameName}")
    public GameTemplateDto getByName(@PathVariable("gameName") String gameTemplateName){
        return gameTemplateService.getByName(gameTemplateName);
    }

    @GetMapping("/{roomId}")
    public void getTiledMapIdByRoomId(@PathVariable("roomId") Integer roomId) {
        //GameTemplate template = gameTemplateRepository.findByMapId(roomId);
    }
}
