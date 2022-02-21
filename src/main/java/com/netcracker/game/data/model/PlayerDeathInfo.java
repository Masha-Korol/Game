package com.netcracker.game.data.model;

import java.util.Objects;

public class PlayerDeathInfo {
  private CoordinatesStructure killerInfo;
  private CoordinatesStructure victimInfo;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PlayerDeathInfo pdi = (PlayerDeathInfo) o;
    return Objects.equals(killerInfo, pdi.getKillerInfo()) &&
            Objects.equals(victimInfo, pdi.getVictimInfo());
  }

  @Override
  public int hashCode() {
    return Objects.hash(killerInfo, victimInfo);
  }

  public CoordinatesStructure getKillerInfo() {
    return killerInfo;
  }

  public void setKillerInfo(CoordinatesStructure killerInfo) {
    this.killerInfo = killerInfo;
  }

  public CoordinatesStructure getVictimInfo() {
    return victimInfo;
  }

  public void setVictimInfo(CoordinatesStructure victimInfo) {
    this.victimInfo = victimInfo;
  }
}
