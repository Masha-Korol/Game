package com.netcracker.game.data.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Table(name = "tiledmaps")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class TiledMap {

    @Id
    @Column(name = "tiledmap_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "tiledmaps_seq", allocationSize = 1)
    private Integer tiledmapId;

    @ManyToOne(targetEntity = TileSet.class)
    @JoinColumn(name = "tileset_id")
    private TileSet tileSet;

    @Column(name = "name")
    private String name;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private String data;

    public Integer getTiledmapId() {
        return tiledmapId;
    }

    public TileSet getTileSet() {
        return tileSet;
    }

    public void setTileSet(TileSet tileSet) {
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
