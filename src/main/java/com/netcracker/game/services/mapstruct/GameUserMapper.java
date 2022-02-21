package com.netcracker.game.services.mapstruct;

import com.netcracker.game.data.model.GameUser;
import com.netcracker.game.services.dto.gameuser.GameUserDto;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class GameUserMapper extends com.netcracker.game.services.mapstruct.Mapper<GameUser, GameUserDto> {
}
