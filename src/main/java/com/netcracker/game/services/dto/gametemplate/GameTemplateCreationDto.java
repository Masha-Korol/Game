package com.netcracker.game.services.dto.gametemplate;

import java.util.Objects;

public class GameTemplateCreationDto {

    private String name;
    private Integer numberOfPlayers;
    private String ownerLogin;
    private Integer mapId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameTemplateCreationDto that = (GameTemplateCreationDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(numberOfPlayers, that.numberOfPlayers) &&
                Objects.equals(ownerLogin, that.ownerLogin) &&
                Objects.equals(mapId, that.mapId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, numberOfPlayers, ownerLogin, mapId);
    }

    @Override
    public String toString() {
        return "GameTemplateCreationDto{" +
                "name='" + name + '\'' +
                ", numberOfPlayers=" + numberOfPlayers +
                ", ownerLogin='" + ownerLogin + '\'' +
                ", mapId=" + mapId +
                '}';
    }
}
