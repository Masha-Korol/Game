package com.netcracker.game.services.mapstruct;

import com.netcracker.game.data.model.GameTemplate;
import com.netcracker.game.data.repository.GameUserRepository;
import com.netcracker.game.data.repository.MapRepository;
import com.netcracker.game.services.dto.gametemplate.GameTemplateCreationDto;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class GameTemplateCreationMapper extends com.netcracker.game.services.mapstruct.Mapper<GameTemplate, GameTemplateCreationDto> {

    @Autowired
    protected GameUserRepository gameUserRepository;

    @Autowired
    protected MapRepository mapRepository;

    @Mappings({
            @Mapping(target = "owner",
                    expression = "java(gameUserRepository.findByLogin(dto.getOwnerLogin()).orElseThrow())"),
            @Mapping(target = "map",
                    expression = "java(mapRepository.findById(dto.getMapId()).orElseThrow())")
    })
    public abstract GameTemplate toEntity(GameTemplateCreationDto dto);
}
