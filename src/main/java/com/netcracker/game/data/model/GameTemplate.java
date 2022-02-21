package com.netcracker.game.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "game_templates", schema = "public")
public class GameTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "game_template_seq", allocationSize = 1)
    private Integer templateId;

    @Column(length = 100)
    private String name;
    private Integer numberOfPlayers;

    @ManyToOne
    @JoinColumn(name = "map_id")
    private Map map;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private GameUser owner;

    public GameUser getOwner() {
        return owner;
    }

    public void setOwner(GameUser owner) {
        this.owner = owner;
    }

    public Integer getTemplateId() {
        return templateId;
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

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameTemplate that = (GameTemplate) o;

        return Objects.equals(name, that.getName()) &&
                Objects.equals(numberOfPlayers, that.getNumberOfPlayers()) &&
                Objects.equals(owner, that.getOwner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, numberOfPlayers);
    }

    @Override
    public String toString() {
        return "GameTemplate{" +
                "templateId=" + templateId +
                ", name='" + name + '\'' +
                ", numberOfPlayers=" + numberOfPlayers +
                ", owner=" + owner +
                '}';
    }
}

