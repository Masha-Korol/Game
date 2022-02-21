package com.netcracker.game.services.mapstruct;

import com.netcracker.game.data.model.TiledMap;
import com.netcracker.game.services.dto.TiledMapDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TiledMapMapper extends com.netcracker.game.services.mapstruct.Mapper<TiledMap, TiledMapDto> {
}
