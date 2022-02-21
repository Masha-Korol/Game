package com.netcracker.game.data.model.tasks;

import org.json.JSONObject;

public abstract class Task {

  public abstract String solve();
  public abstract void constructTaskFromTemplate(JSONObject jsonTemplate);
  public abstract String getTaskBody();
  public abstract Integer getTaskTypeId();
}
