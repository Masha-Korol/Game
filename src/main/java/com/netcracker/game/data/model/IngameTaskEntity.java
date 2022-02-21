package com.netcracker.game.data.model;

import java.io.Serializable;

public class IngameTaskEntity implements Serializable {
  private Integer taskTypeId;
  private String body;
  private String result;

  public Integer getTaskTypeId() {
    return taskTypeId;
  }

  public void setTaskTypeId(Integer taskTypeId) {
    this.taskTypeId = taskTypeId;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}
