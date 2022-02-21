package com.netcracker.game.services.dto.room;

import com.netcracker.game.services.dto.gametemplate.GameTemplateDto;
import com.netcracker.game.services.dto.gameuser.GameUserInfoDto;

import java.util.*;
import java.util.Objects;

public class RoomDto {
    private Integer roomId;
    private String roomType;
    private String name;
    private String code;
    private GameTemplateDto gameTemplate;
    private List<GameUserInfoDto> roomPlayers;
    private GameUserInfoDto gameLeader;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public GameTemplateDto getGameTemplate() {
        return gameTemplate;
    }

    public void setGameTemplate(GameTemplateDto gameTemplate) {
        this.gameTemplate = gameTemplate;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GameUserInfoDto> getRoomPlayers() {
        return roomPlayers;
    }

    public void setRoomPlayers(List<GameUserInfoDto> roomPlayers) {
        this.roomPlayers = roomPlayers;
    }

    public GameUserInfoDto getGameLeader() {
        return gameLeader;
    }

    public void setGameLeader(GameUserInfoDto gameLeader) {
        this.gameLeader = gameLeader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDto roomDto = (RoomDto) o;
        return Objects.equals(roomType, roomDto.roomType) &&
                Objects.equals(name, roomDto.name) &&
                Objects.equals(code, roomDto.code) &&
                Objects.equals(gameTemplate, roomDto.gameTemplate) &&
                Objects.equals(roomPlayers, roomDto.roomPlayers) &&
                Objects.equals(gameLeader, roomDto.gameLeader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomType, name, code, gameTemplate, roomPlayers, gameLeader);
    }

    @Override
    public String toString() {
        return "RoomDto{" +
                "roomId=" + roomId +
                ", roomType='" + roomType + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", gameTemplate=" + gameTemplate +
                ", roomPlayers=" + roomPlayers +
                ", gameLeader=" + gameLeader +
                '}';
    }
}
