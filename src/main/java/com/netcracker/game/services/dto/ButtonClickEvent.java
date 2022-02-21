package com.netcracker.game.services.dto;

import java.util.Objects;

public class ButtonClickEvent {
    private Integer roomId;
    private String clickedGameUserLogin;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getClickedGameUserLogin() {
        return clickedGameUserLogin;
    }

    public void setClickedGameUserLogin(String clickedGameUserLogin) {
        this.clickedGameUserLogin = clickedGameUserLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ButtonClickEvent that = (ButtonClickEvent) o;
        return Objects.equals(roomId, that.roomId) &&
                Objects.equals(clickedGameUserLogin, that.clickedGameUserLogin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, clickedGameUserLogin);
    }

    @Override
    public String toString() {
        return "ButtonClickEvent{" +
                "roomId=" + roomId +
                ", clickedGameUserLogin='" + clickedGameUserLogin + '\'' +
                '}';
    }
}
