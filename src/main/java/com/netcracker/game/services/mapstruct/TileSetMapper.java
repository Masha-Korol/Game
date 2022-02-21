package com.netcracker.game.services.mapstruct;

import com.netcracker.game.data.model.TileSet;
import com.netcracker.game.services.dto.TileSetDto;
import org.mapstruct.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class TileSetMapper extends com.netcracker.game.services.mapstruct.Mapper<TileSet, TileSetDto> {
}
