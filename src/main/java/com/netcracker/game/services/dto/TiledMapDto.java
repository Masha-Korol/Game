package com.netcracker.game.services.dto;

public class TiledMapDto {

    private Integer tiledmapId;
    private TileSetDto tileSet;
    private String name;
    private String data;

    public Integer getTiledmapId() {
        return tiledmapId;
    }

    public void setTiledmapId(Integer tiledmapId) {
        this.tiledmapId = tiledmapId;
    }

    public TileSetDto getTileSet() {
        return tileSet;
    }

    public void setTileSet(TileSetDto tileSet) {
        this.tileSet = tileSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
