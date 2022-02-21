package com.netcracker.game.services.mapstruct;

import com.netcracker.game.data.model.GameUser;
import com.netcracker.game.services.dto.gameuser.GameUserInfoDto;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class GameUserInfoMapper extends com.netcracker.game.services.mapstruct.Mapper<GameUser, GameUserInfoDto> {

}
