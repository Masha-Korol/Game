package com.netcracker.game.services.dto.tasks;

import java.util.Objects;

public class TaskResultDto {
  private Integer taskId;
  private String terminalUUID;
  private String roundUUID;
  private boolean taskSucceeded;
  private String metadata;

  public Integer getTaskId() {
    return taskId;
  }

  public void setTaskId(Integer taskId) {
    this.taskId = taskId;
  }

  public String getMetadata() {
    return metadata;
  }

  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  public void setTaskSucceeded(boolean taskSucceeded) {
    this.taskSucceeded = taskSucceeded;
  }

  public boolean isTaskSucceeded() {
    return this.taskSucceeded;
  }

  public String getTerminalUUID() {
    return terminalUUID;
  }

  public void setTerminalUUID(String terminalUUID) {
    this.terminalUUID = terminalUUID;
  }

  public String getRoundUUID() {
    return roundUUID;
  }

  public void setRoundUUID(String roundUUID) {
    this.roundUUID = roundUUID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TaskResultDto taskResultDto = (TaskResultDto) o;
    return Objects.equals(terminalUUID, taskResultDto.terminalUUID) &&
            Objects.equals(roundUUID, taskResultDto.roundUUID);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskId, terminalUUID, roundUUID);
  }

  @Override
  public String toString() {
    return "TaskResultDto{" +
            "roundUUID='" + roundUUID + '\'' +
            ", taskId='" + taskId + '\'' +
            ", terminalUUID='" + terminalUUID + '\'' +
            ", taskSucceeded='" + taskSucceeded + '\'' +
            '}';
  }
}
