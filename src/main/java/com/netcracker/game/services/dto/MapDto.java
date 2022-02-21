package com.netcracker.game.services.dto;

import java.util.Objects;

public class MapDto {

    private Integer mapId;
    private String name;
    private TiledMapDto tiledMap;
    private String description;
    private FileDto preview;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TiledMapDto getTiledMap() {
        return tiledMap;
    }

    public void setTiledMap(TiledMapDto tiledMap) {
        this.tiledMap = tiledMap;
    }

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public FileDto getPreview() {
        return preview;
    }

    public void setPreview(FileDto preview) {
        this.preview = preview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapDto mapDto = (MapDto) o;
        return Objects.equals(name, mapDto.name) &&
                Objects.equals(description, mapDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "MapDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
