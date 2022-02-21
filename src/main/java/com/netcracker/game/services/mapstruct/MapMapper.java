package com.netcracker.game.services.mapstruct;

import com.netcracker.game.data.model.Map;
import com.netcracker.game.services.dto.MapDto;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class MapMapper extends com.netcracker.game.services.mapstruct.Mapper<Map, MapDto> {
}
