package com.netcracker.game.data.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "maps", schema = "public")
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "map_seq", allocationSize = 1)
    private Integer mapId;

    @ManyToOne
    @JoinColumn(name = "tiledmap_id")
    private TiledMap tiledMap;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String description;

    @OneToOne
    @JoinColumn(name = "preview_id")
    private File preview;

    public Integer getMapId() {
        return mapId;
    }

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

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public void setTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    public File getPreview() {
        return preview;
    }

    public void setPreview(File preview) {
        this.preview = preview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Map map = (Map) o;
        return Objects.equals(name, map.name) &&
                Objects.equals(description, map.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "Map{" +
                "mapId=" + mapId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
