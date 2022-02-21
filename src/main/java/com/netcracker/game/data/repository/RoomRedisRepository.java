package com.netcracker.game.data.repository;

import com.netcracker.game.data.model.Message;
import com.netcracker.game.data.model.RoomEntity;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class RoomRedisRepository {
    private final RedisTemplate<Integer, RoomEntity> redisTemplate;
    private final HashOperations hashOperations;

    public RoomRedisRepository(RedisTemplate<Integer, RoomEntity> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(RoomEntity roomEntity, Integer roomId) {
        hashOperations.put(roomId, roomEntity.getRoomId().hashCode(), roomEntity);
    }

    public RoomEntity findByRoomId(Integer roomId){
        return (RoomEntity) hashOperations.get(roomId, roomId);
    }
}
