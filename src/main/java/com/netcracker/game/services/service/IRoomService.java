package com.netcracker.game.services.service;

import com.netcracker.game.exception.BadInputDataException;
import com.netcracker.game.services.dto.pagination.PageDto;
import com.netcracker.game.services.dto.pagination.PageRequestDto;
import com.netcracker.game.services.dto.room.RoomCreationDto;
import com.netcracker.game.services.dto.room.RoomDto;

import java.util.List;

public interface IRoomService {
    RoomDto addRoom(RoomCreationDto dto) throws BadInputDataException;
    PageDto<RoomDto> getAllPublic(String typePrivate,
                            String typePublic,
                            String openS,
                            String searchText,
                            PageRequestDto pageRequest);
    List<RoomDto> getAllByGameUser(Integer userId);
    RoomDto addRoomPlayer(Integer roomId, String gameUserLogin);
    RoomDto addPrivateRoomPlayer(String code, String gameUserLogin);
    RoomDto getById(Integer roomId);
    void exitRoom(Integer roomId, String gameUserLogin);
    void editRoom(RoomDto roomDto);
    void passGameLeaderRight(Integer roomId, String passToUsername, String hostUsername);
    boolean isTheLastGamePlayer(Integer roomId, String gameUserLogin);
    List<String> getAlivePlayersLogins(Integer roomId);
}
