package com.netcracker.game.data.model.tasks.factory;

import com.netcracker.game.data.model.tasks.CalculatorTask;
import com.netcracker.game.data.model.tasks.SimonTask;
import com.netcracker.game.data.model.tasks.Task;
import com.netcracker.game.data.model.tasks.WordsDefenseTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskFactory {

  private static final Logger logger = LoggerFactory.getLogger(TaskFactory.class);

  public Task createTask(Integer taskTypeId, String jsonTemplate) throws IllegalArgumentException {
    switch (taskTypeId) {
      case 1:
        return new CalculatorTask(jsonTemplate);
      case 2:
        return new WordsDefenseTask(jsonTemplate);
      case 3:
        return new SimonTask(jsonTemplate);
      default:
        throw new IllegalArgumentException("taskTypeId not supported: ".concat(taskTypeId.toString()));
    }
  }
}
