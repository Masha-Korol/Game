package com.netcracker.game.services.service.impl;

import com.netcracker.game.data.model.IngameTaskEntity;
import com.netcracker.game.data.model.TaskTemplate;
import com.netcracker.game.data.model.tasks.Task;
import com.netcracker.game.data.model.tasks.factory.TaskFactory;
import com.netcracker.game.data.repository.IngameTaskRedisRepository;
import com.netcracker.game.data.repository.TaskTemplateRepository;
import com.netcracker.game.services.dto.tasks.IngameTaskDto;
import com.netcracker.game.services.dto.tasks.TaskResultDto;
import com.netcracker.game.services.service.IIngameTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class IngameTaskServiceImpl implements IIngameTaskService {

  private static final Logger logger = LoggerFactory.getLogger(IngameTaskServiceImpl.class);
  private final ConcurrentMap<String, ReentrantLock> locks;

  @Autowired
  TaskTemplateRepository taskTemplateRepository;

  @Autowired
  IngameTaskRedisRepository ingameTaskRedisRepository;

  public IngameTaskServiceImpl() {
    this.locks = new ConcurrentHashMap<>();
  }

//  @Override
//  public void generateTerminals(String roundUUID, List<Integer> taskTypesIds) {
//    if (!ingameTaskRedisRepository.isRoundAlreadyHaveTasks(roundUUID)) {
//      List<IngameTaskEntity> entities = generateTasksFromTemplate(taskTypesIds);
//      for (IngameTaskEntity entity : entities) {
//        ingameTaskRedisRepository.save(roundUUID, UUID.randomUUID().toString(), entity);
//      }
//    }
//  }

  @Override
  public Set<String> getTerminalsUUIDs(String roundUUID, List<Integer> taskTypesIds) {

    Set<String> result = new HashSet<>();

    if (!ingameTaskRedisRepository.isRoundAlreadyHaveTasks(roundUUID)) {
      ReentrantLock lock = locks.computeIfAbsent(roundUUID, (k) -> new ReentrantLock());
      lock.lock();
      try {
        if (!ingameTaskRedisRepository.isRoundAlreadyHaveTasks(roundUUID) && !taskTypesIds.isEmpty()) {
          List<IngameTaskEntity> entities = generateTasksFromTemplate(taskTypesIds);
          for (IngameTaskEntity entity : entities) {
            ingameTaskRedisRepository.save(roundUUID, UUID.randomUUID().toString(), entity);
          }
        }
      } finally {
        if (ingameTaskRedisRepository.isRoundAlreadyHaveTasks(roundUUID)) {
          result = ingameTaskRedisRepository.getAllTerminalsUUIDs(roundUUID);
        }
        lock.unlock();
      }
    } else {
      result = ingameTaskRedisRepository.getAllTerminalsUUIDs(roundUUID);
    }
    return result;
  }

  @Override
  public IngameTaskDto getTaskByTerminal(String roundUUID, String terminalUUID) {
    IngameTaskEntity entity = ingameTaskRedisRepository.get(roundUUID, terminalUUID);
    IngameTaskDto dto = new IngameTaskDto();
    dto.setTaskConstraints("");
    dto.setTypeId(entity.getTaskTypeId());
    dto.setTaskBody(entity.getBody());
    return dto;
  }

  @Override
  public TaskResultDto getTaskResult(String roundUUID, String terminalUUID, String taskResult) {
    TaskResultDto dto = new TaskResultDto();
    IngameTaskEntity entity = ingameTaskRedisRepository.get(roundUUID, terminalUUID);
    dto.setRoundUUID(roundUUID);
    dto.setTaskId(entity.getTaskTypeId());
    dto.setTerminalUUID(terminalUUID);
    dto.setTaskSucceeded(taskResult.equals(entity.getResult()));
    dto.setMetadata("");
    return dto;
  }

  @Override
  public List<IngameTaskEntity> generateTasksFromTemplate(List<Integer> taskTypeIds) {
    List<IngameTaskEntity> ingameTaskList = new ArrayList<>();
    TaskFactory taskFactory = new TaskFactory();
    for (Integer taskTypeId : taskTypeIds) {
      Optional<TaskTemplate> taskTemplate = taskTemplateRepository.findByTaskTypeId(taskTypeId);
      if (taskTemplate.isPresent()) {
        IngameTaskEntity entity = new IngameTaskEntity();

        Task task = taskFactory.createTask(taskTypeId, taskTemplate.get().getGeneralTemplate());
        entity.setTaskTypeId(taskTypeId);
        entity.setBody(task.getTaskBody());
        entity.setResult(task.solve());
        ingameTaskList.add(entity);
      } else {
        logger.error("TaskTemplate not found for task type " + taskTypeId.toString());
      }
    }
    return ingameTaskList;
  }

}
