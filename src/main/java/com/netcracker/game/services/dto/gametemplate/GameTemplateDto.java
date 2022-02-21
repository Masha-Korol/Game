package com.netcracker.game.services.dto.gametemplate;

import com.netcracker.game.services.dto.MapDto;
import com.netcracker.game.services.dto.gameuser.GameUserDto;

import java.util.Objects;

public class GameTemplateDto {

    private String name;
    private Integer numberOfPlayers;
    private MapDto map;
    private GameUserDto owner;
    private Integer templateId;

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public GameUserDto getOwner() {
        return owner;
    }

    public void setOwner(GameUserDto owner) {
        // otherwise we will leak template owner password on rooms endpoints
        // we need to think about which data we are allowing user to see
        // about other ppl in rooms - maybe we need to hide emails as well?
        owner.setPassword("");
        this.owner = owner;
    }

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

    public MapDto getMap() {
        return map;
    }

    public void setMap(MapDto map) {
        this.map = map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameTemplateDto that = (GameTemplateDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(numberOfPlayers, that.numberOfPlayers) &&
                Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, numberOfPlayers, owner);
    }

    @Override
    public String toString() {
        return "GameTemplateDto{" +
                "name='" + name + '\'' +
                ", numberOfPlayers=" + numberOfPlayers +
                ", owner=" + owner +
                '}';
    }
}
