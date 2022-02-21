package com.netcracker.game.services.service;

import com.netcracker.game.services.dto.gameuser.GameUserDto;
import com.netcracker.game.services.dto.gameuser.GameUserInfoDto;
import java.util.*;

public interface IGameUserService {

    void addUser(GameUserDto dto);

    List<GameUserDto> getAll();
    List<GameUserInfoDto> getAllByRoomId(Integer roomId);
}
