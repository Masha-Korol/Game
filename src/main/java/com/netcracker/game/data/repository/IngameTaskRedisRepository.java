package com.netcracker.game.data.repository;

import com.netcracker.game.data.model.IngameTaskEntity;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

// K - roundUUID, HK - terminalUUID, V - IngameTaskEntity
@Repository
public class IngameTaskRedisRepository {
  private final RedisTemplate<Integer, IngameTaskEntity> redisTemplate;
  private final HashOperations hashOperations;

  IngameTaskRedisRepository(RedisTemplate<Integer, IngameTaskEntity> redisTemplate) {
    this.redisTemplate = redisTemplate;
    this.hashOperations = redisTemplate.opsForHash();
  }

  public void save(String roundUUID, String terminalUUID, IngameTaskEntity ingameTaskEntity) {
    hashOperations.put(roundUUID, terminalUUID, ingameTaskEntity);
  }

  public IngameTaskEntity get(String roundUUID, String terminalUUID) {
    return (IngameTaskEntity) hashOperations.get(roundUUID, terminalUUID);
  }

  public List<IngameTaskEntity> getAllTasks(String roundUUID) {
    return hashOperations.values(roundUUID);
  }

  public Set<String> getAllTerminalsUUIDs(String roundUUID) {
    return hashOperations.keys(roundUUID);
  }

  public boolean isRoundAlreadyHaveTasks(String roundUUID) {
    return !hashOperations.values(roundUUID).isEmpty();
  }
}
