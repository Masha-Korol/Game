package com.netcracker.game.services.dto.voting;

import java.util.Objects;

public class VotingRequest {
    private Integer roomId;
    private String gameUserLogin;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getGameUserLogin() {
        return gameUserLogin;
    }

    public void setGameUserLogin(String gameUserLogin) {
        this.gameUserLogin = gameUserLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VotingRequest that = (VotingRequest) o;
        return Objects.equals(roomId, that.roomId) &&
                Objects.equals(gameUserLogin, that.gameUserLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, gameUserLogin);
    }

    @Override
    public String toString() {
        return "VotingRequest{" +
                "roomId=" + roomId +
                ", gameUserLogin='" + gameUserLogin + '\'' +
                '}';
    }
}
