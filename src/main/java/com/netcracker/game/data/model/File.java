package com.netcracker.game.data.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "files", schema = "public")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "files_seq", allocationSize = 1)
    private Integer fileId;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String type;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] data;

    @OneToOne(mappedBy = "file")
    private GameUser gameUser;

    public File() {
    }

    public File(String name,
                String type,
                byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public File(Integer fileId, String name, String type, byte[] data, GameUser gameUser) {
        this.fileId = fileId;
        this.name = name;
        this.type = type;
        this.data = data;
        this.gameUser = gameUser;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public GameUser getGameUser() {
        return gameUser;
    }

    public void setGameUser(GameUser gameUser) {
        this.gameUser = gameUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(name, file.name) &&
                Objects.equals(type, file.type) &&
                Arrays.equals(data, file.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, type);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileId=" + fileId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
