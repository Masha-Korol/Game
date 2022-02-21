package com.netcracker.game.data.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Message implements Serializable {
    private String messageId;
    private String text;
    private String ownerLogin;
    private LocalDateTime date;
    private Integer roomId;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId() {
        this.messageId = UUID.randomUUID().toString();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(messageId, message.messageId) &&
                Objects.equals(text, message.text) &&
                Objects.equals(ownerLogin, message.ownerLogin) &&
                Objects.equals(date, message.date) &&
                Objects.equals(roomId, message.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, text, ownerLogin, date, roomId);
    }
}
