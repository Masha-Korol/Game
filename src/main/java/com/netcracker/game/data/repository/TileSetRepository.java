package com.netcracker.game.data.repository;

import com.netcracker.game.data.model.TileSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TileSetRepository extends JpaRepository<TileSet, Integer> {
}
