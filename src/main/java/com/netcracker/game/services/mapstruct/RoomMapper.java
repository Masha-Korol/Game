package com.netcracker.game.services.mapstruct;

import com.netcracker.game.data.model.Room;
import com.netcracker.game.services.dto.room.RoomDto;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class RoomMapper extends com.netcracker.game.services.mapstruct.Mapper<Room, RoomDto> {
}
