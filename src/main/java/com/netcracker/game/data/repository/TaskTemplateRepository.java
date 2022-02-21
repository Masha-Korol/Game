package com.netcracker.game.data.repository;

import com.netcracker.game.data.model.TaskTemplate;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TaskTemplateRepository extends PagingAndSortingRepository<TaskTemplate, Integer> {

  Optional<TaskTemplate> findByTaskTypeId(Integer taskTypeId);
  Optional<TaskTemplate> findByTaskTemplateId(Integer taskTemplateId);

}
