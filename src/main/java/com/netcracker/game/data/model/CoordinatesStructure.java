package com.netcracker.game.data.model;

import com.netcracker.game.data.model.enums.Color;
import com.netcracker.game.data.model.enums.MovementDirection;

import java.io.Serializable;
import java.util.Objects;

public class CoordinatesStructure implements Serializable {
    private double x;
    private double y;
    private String playerLogin;
    private Integer roomId;
    private MovementDirection movementDirection;

    public MovementDirection getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(MovementDirection movementDirection) {
        this.movementDirection = movementDirection;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getPlayerLogin() {
        return playerLogin;
    }

    public void setPlayerLogin(String playerLogin) {
        this.playerLogin = playerLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinatesStructure that = (CoordinatesStructure) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0 &&
                Objects.equals(playerLogin, that.playerLogin) &&
                Objects.equals(roomId, that.roomId) &&
                movementDirection == that.movementDirection;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, playerLogin, roomId, movementDirection);
    }

    @Override
    public String toString() {
        return "CoordinatesStructure{" +
                "x=" + x +
                ", y=" + y +
                ", playerLogin='" + playerLogin + '\'' +
                ", roomId=" + roomId +
                ", movementDirection=" + movementDirection +
                '}';
    }
}
