package com.netcracker.game.data.model;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Table(name = "task_template")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class TaskTemplate {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
  @SequenceGenerator(name = "generator", sequenceName = "task_template_seq", allocationSize = 1)
  private Integer taskTemplateId;

  @Type(type = "json")
  @Column(columnDefinition = "json")
  private String generalTemplate;

  private Integer taskTypeId;

  public Integer getTaskTemplateId() {
    return taskTemplateId;
  }

  public void setTaskTemplateId(Integer taskTemplateId) {
    this.taskTemplateId = taskTemplateId;
  }

  public String getGeneralTemplate() {
    return generalTemplate;
  }

  public void setGeneralTemplate(String generalTemplate) {
    this.generalTemplate = generalTemplate;
  }

  public Integer getTaskTypeId() {
    return taskTypeId;
  }

  public void setTaskTypeId(Integer taskTypeId) {
    this.taskTypeId = taskTypeId;
  }
}
