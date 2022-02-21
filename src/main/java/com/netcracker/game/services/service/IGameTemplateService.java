package com.netcracker.game.services.service;

import com.netcracker.game.exception.BadInputDataException;
import com.netcracker.game.services.dto.gametemplate.GameTemplateCreationDto;
import com.netcracker.game.services.dto.gametemplate.GameTemplateDto;
import com.netcracker.game.services.dto.pagination.PageDto;
import com.netcracker.game.services.dto.pagination.PageRequestDto;

import java.util.*;

public interface IGameTemplateService {
    void addGameTemplate(GameTemplateCreationDto dto) throws BadInputDataException;
    PageDto<GameTemplateDto> getAll(String searchText, PageRequestDto pageRequest);
    List<GameTemplateDto> getAllByGameUser(Integer gameUserId);
    void editGameTemplate(GameTemplateDto gameTemplateDto);
    void deleteGameTemplate(Integer gameTemplateId);
    GameTemplateDto getByName(String gameTemplateName);
}
