package com.netcracker.game.services.mapstruct;

import com.netcracker.game.data.model.GameTemplate;
import com.netcracker.game.services.dto.gametemplate.GameTemplateDto;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class GameTemplateMapper extends com.netcracker.game.services.mapstruct.Mapper<GameTemplate, GameTemplateDto> {
}
