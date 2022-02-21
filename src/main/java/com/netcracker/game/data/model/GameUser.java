package com.netcracker.game.data.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "game_users", schema = "public")
public class GameUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "game_user_seq", allocationSize = 1)
    private Integer gameUserId;

    @Column(length = 100)
    private String login;

    @Column(length = 100)
    private String password;

    @Column(length = 100)
    private String email;

    @OneToMany(mappedBy = "owner")
    private List<GameTemplate> gameTemplates;

    @ManyToMany(mappedBy = "roomPlayers")
    private List<Room> rooms;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id")
    private File file;

    @ManyToMany()
    @JoinTable(
            name = "game_user_roles",
            joinColumns = @JoinColumn(name = "game_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<AuthorizationRole> roles;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void removeRoom(Room room){
        this.rooms.remove(room);
    }

    public List<GameTemplate> getGameTemplates() {
        return gameTemplates;
    }

    public void setGameTemplates(List<GameTemplate> gameTemplates) {
        this.gameTemplates = gameTemplates;
    }

    public Integer getGameUserId() {
        return gameUserId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<AuthorizationRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<AuthorizationRole> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameUser gameUser = (GameUser) o;
        return Objects.equals(login, gameUser.login) &&
                Objects.equals(password, gameUser.password) &&
                Objects.equals(email, gameUser.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, email);
    }

    @Override
    public String toString() {
        return "GameUser{" +
                "gameUserId=" + gameUserId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
