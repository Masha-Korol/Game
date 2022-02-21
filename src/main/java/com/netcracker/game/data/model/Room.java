package com.netcracker.game.data.model;

import com.netcracker.game.data.model.enums.RoomType;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "rooms", schema = "public")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "room_seq", allocationSize = 1)
    private Integer roomId;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private RoomType roomType;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String code;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "room_players",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "game_user_id")
    )
    private Set<GameUser> roomPlayers;

    @ManyToOne
    @JoinColumn(name = "game_leader")
    private GameUser gameLeader;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private GameTemplate gameTemplate;

    public Set<GameUser> getRoomPlayers() {
        return roomPlayers;
    }

    public void addRoomPlayer(GameUser roomPlayer) {
        if (this.roomPlayers == null) {
            this.roomPlayers = new HashSet<GameUser>();
        }
        this.roomPlayers.add(roomPlayer);
    }

    public void removeGameUser(GameUser gameUser){
        this.roomPlayers.remove(gameUser);
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public GameTemplate getGameTemplate() {
        return gameTemplate;
    }

    public void setGameTemplate(GameTemplate gameTemplate) {
        this.gameTemplate = gameTemplate;
    }

    public GameUser getGameLeader() {
        return gameLeader;
    }

    public void setGameLeader(GameUser gameLeader) {
        this.gameLeader = gameLeader;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomType == room.roomType &&
                Objects.equals(name, room.name) &&
                Objects.equals(code, room.code) &&
                Objects.equals(roomPlayers, room.roomPlayers) &&
                Objects.equals(gameLeader, room.gameLeader) &&
                Objects.equals(gameTemplate, room.gameTemplate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomType, name, code, roomPlayers, gameLeader, gameTemplate);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomType=" + roomType +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", gameLeader=" + gameLeader +
                ", gameTemplate=" + gameTemplate +
                '}';
    }
}
