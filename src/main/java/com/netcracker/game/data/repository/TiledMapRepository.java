package com.netcracker.game.data.repository;

import com.netcracker.game.data.model.TiledMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiledMapRepository extends JpaRepository<TiledMap, Integer> {
}
