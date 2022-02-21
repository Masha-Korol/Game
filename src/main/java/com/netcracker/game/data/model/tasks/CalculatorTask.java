package com.netcracker.game.data.model.tasks;

import com.udojava.evalex.Expression;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

public class CalculatorTask extends Task {
  private final static int TASK_TYPE_ID = 1;
  private final static String MAX_NUMBER_FIELD = "max_number";
  private final static String ARGS_FIELD = "arg";
  private String taskBody;

  public CalculatorTask(String jsonTemplate) {
    constructTaskFromTemplate(new JSONObject(jsonTemplate));
  }

  @Override
  public String solve() {
    return String.valueOf(new Expression(taskBody).eval());
  }

  @Override
  public void constructTaskFromTemplate(JSONObject jsonTemplate) {
    Random random = new Random();
    StringBuilder buf = new StringBuilder();
    JSONArray maxValues = jsonTemplate.getJSONArray(MAX_NUMBER_FIELD);
    JSONArray args = jsonTemplate.getJSONArray(ARGS_FIELD);
    for (int i = 0; i < maxValues.length(); i++) {
      int randomIndex = random.nextInt(args.getJSONArray(i).length());
      int randomValue = random.nextInt(maxValues.getInt(i) + 1) + 1;
      String op = args.getJSONArray(i).getString(randomIndex);

      buf.append(randomValue);
      if (i != maxValues.length() - 1) {
        buf.append(" ");
        buf.append(op);
        buf.append(" ");
      }
    }
    taskBody = buf.toString();
  }

  @Override
  public String getTaskBody() {
    return taskBody;
  }

  @Override
  public Integer getTaskTypeId() {
    return TASK_TYPE_ID;
  }

}
