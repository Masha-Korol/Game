package com.netcracker.game.services.mapstruct;

import com.netcracker.game.data.model.Room;
import com.netcracker.game.data.repository.GameTemplateRepository;
import com.netcracker.game.data.repository.GameUserRepository;
import com.netcracker.game.services.dto.room.RoomCreationDto;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.mapstruct.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class RoomCreationMapper extends com.netcracker.game.services.mapstruct.Mapper<Room, RoomCreationDto>{

    @Autowired
    protected GameTemplateRepository gameTemplateRepository;
    @Autowired
    protected GameUserRepository gameUserRepository;

    @Mappings({@Mapping(target = "gameTemplate",
            expression = "java(gameTemplateRepository.findByName(dto.getGameTemplateName()))"),
            @Mapping(target = "gameLeader",
                    expression = "java(gameUserRepository.findByLogin(dto.getGameLeaderLogin()).orElseThrow())")})
    public abstract Room toEntity(RoomCreationDto dto);
}
