package com.netcracker.game.data.model.tasks;

import org.json.JSONObject;

import java.util.Random;

public class SimonTask extends Task {
  private final static int TASK_TYPE_ID = 3;
  private final static int NUMBER_OF_SEGMENTS = 4;
  private final static String MIN_SEQ_LENGTH = "min_seq_length";
  private final static String MAX_SEQ_LENGTH = "max_seq_length";
  private String taskBody;

  public SimonTask(String jsonTemplate) {
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
    int minSequenceLength = jsonTemplate.getInt(MIN_SEQ_LENGTH);
    int maxSequenceLength = jsonTemplate.getInt(MAX_SEQ_LENGTH);
    int sequenceLength = random.nextInt((maxSequenceLength - minSequenceLength) + 1) + minSequenceLength;
    for (int i = 0; i < sequenceLength; i++) {
      int randomSegment = random.nextInt(NUMBER_OF_SEGMENTS);
      buf.append(randomSegment);
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
