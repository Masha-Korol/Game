package com.netcracker.game.services.dto.room;

import java.util.Objects;

public class RoomCreationDto {

    private String roomType;
    private String name;
    private String gameTemplateName;
    private String gameLeaderLogin;

    public String getGameLeaderLogin() {
        return gameLeaderLogin;
    }

    public void setGameLeaderLogin(String gameLeaderLogin) {
        this.gameLeaderLogin = gameLeaderLogin;
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

    public String getGameTemplateName() {
        return gameTemplateName;
    }

    public void setGameTemplateName(String gameTemplateName) {
        this.gameTemplateName = gameTemplateName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCreationDto that = (RoomCreationDto) o;
        return Objects.equals(roomType, that.roomType) &&
                Objects.equals(name, that.name) &&
                Objects.equals(gameTemplateName, that.gameTemplateName) &&
                Objects.equals(gameLeaderLogin, that.gameLeaderLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomType, name, gameTemplateName, gameLeaderLogin);
    }

    @Override
    public String toString() {
        return "RoomCreationDto{" +
                "roomType='" + roomType + '\'' +
                ", name='" + name + '\'' +
                ", gameTemplateName='" + gameTemplateName + '\'' +
                ", gameLeaderLogin='" + gameLeaderLogin + '\'' +
                '}';
    }
}
