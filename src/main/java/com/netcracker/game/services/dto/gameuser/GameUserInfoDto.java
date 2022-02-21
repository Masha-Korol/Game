package com.netcracker.game.services.dto.gameuser;

import java.util.Objects;

public class GameUserInfoDto {
    private Integer gameUserId;
    private String login;
    private String email;

    public Integer getGameUserId() {
        return gameUserId;
    }

    public void setGameUserId(Integer gameUserId) {
        this.gameUserId = gameUserId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameUserInfoDto that = (GameUserInfoDto) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, email);
    }

    @Override
    public String toString() {
        return "GameUserInfoDto{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
