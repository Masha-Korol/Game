package com.netcracker.game.services.service;

import com.netcracker.game.data.model.IngameTaskEntity;
import com.netcracker.game.services.dto.tasks.IngameTaskDto;
import com.netcracker.game.services.dto.tasks.TaskResultDto;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IIngameTaskService {
  Set<String> getTerminalsUUIDs(String roundUUID, List<Integer> taskTypesIds);
  IngameTaskDto getTaskByTerminal(String roundUUID, String terminalUUID);
  TaskResultDto getTaskResult(String roundUUID, String terminalUUID, String taskResult);
  List<IngameTaskEntity> generateTasksFromTemplate(List<Integer> taskTypeIds);
//  void generateTerminals(String roundUUID, List<Integer> taskTypesIds);
}
