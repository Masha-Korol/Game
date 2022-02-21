package com.netcracker.game.services.dto.tasks;

import java.util.Objects;

public class IngameTaskDto {
  private Integer typeId;
  private String taskBody;
  private String taskConstraints;

  public Integer getTypeId() {
    return typeId;
  }

  public void setTypeId(Integer typeId) {
    this.typeId = typeId;
  }

  public String getTaskBody() {
    return taskBody;
  }

  public void setTaskBody(String taskBody) {
    this.taskBody = taskBody;
  }

  public String getTaskConstraints() {
    return taskConstraints;
  }

  public void setTaskConstraints(String taskConstraints) {
    this.taskConstraints = taskConstraints;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    IngameTaskDto ingameTaskDto = (IngameTaskDto) o;
    return Objects.equals(typeId, ingameTaskDto.typeId) &&
            Objects.equals(taskBody, ingameTaskDto.taskBody) &&
            Objects.equals(taskConstraints, ingameTaskDto.taskConstraints);
  }

  @Override
  public int hashCode() {
    return Objects.hash(typeId, taskBody, taskConstraints);
  }

  @Override
  public String toString() {
    return "IngameTaskDto{" +
            "typeId='" + typeId + '\'' +
            ", taskBody='" + taskBody + '\'' +
            ", taskConstraints='" + taskConstraints + '\'' +
            '}';
  }
}
