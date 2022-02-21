package com.netcracker.game.data.repository;

import com.netcracker.game.data.model.Map;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MapRepository extends PagingAndSortingRepository<Map, Integer> {
}
