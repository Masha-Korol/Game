package com.netcracker.game.data.model.tasks;

import org.json.JSONObject;

import java.util.Random;

public class WordsDefenseTask extends Task {
  private final static int TASK_TYPE_ID = 2;
  private final static String MIN_CHAR_COUNT = "min_char_count";
  private final static String MAX_CHAR_COUNT = "max_char_count";
  private String taskBody;

  public WordsDefenseTask(String jsonTemplate) {
    constructTaskFromTemplate(new JSONObject(jsonTemplate));
  }

  @Override
  public String solve() {
    return taskBody;
  }

  @Override
  public void constructTaskFromTemplate(JSONObject jsonTemplate) {
    Random random = new Random();
    StringBuilder buf = new StringBuilder();
    int minCharCount = jsonTemplate.getInt(MIN_CHAR_COUNT);
    int maxCharCount = jsonTemplate.getInt(MAX_CHAR_COUNT);
    int charCount = random.nextInt((maxCharCount - minCharCount) + 1) + minCharCount;
    for (int i = 0; i < charCount; i++) {
      char randomChar = (char) ('a' + random.nextInt(26));
      buf.append(randomChar);
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
