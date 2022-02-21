package com.netcracker.game.data.model;

import java.util.Objects;

public class TerminalInfo {
  private Integer roomId;
  private String terminalUUID;

  public Integer getRoomId() {
    return roomId;
  }

  public void setRoomId(Integer roomId) {
    this.roomId = roomId;
  }

  public String getTerminalUUID() {
    return terminalUUID;
  }

  public void setTerminalUUID(String terminalUUID) {
    this.terminalUUID = terminalUUID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TerminalInfo ti = (TerminalInfo) o;
    return Objects.equals(roomId, ti.getRoomId()) &&
            Objects.equals(terminalUUID, ti.getTerminalUUID());
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomId, terminalUUID);
  }
}
