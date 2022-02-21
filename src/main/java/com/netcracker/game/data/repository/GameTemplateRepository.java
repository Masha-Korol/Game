package com.netcracker.game.data.repository;

import com.netcracker.game.data.model.GameTemplate;
import com.netcracker.game.data.model.GameUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface GameTemplateRepository extends PagingAndSortingRepository<GameTemplate, Integer> {

    GameTemplate findByName(String name);

    //GameTemplate findByMapId(Integer id);
    List<GameTemplate> findAllByOwner(GameUser gameUser);
    boolean existsByName(String name);
    Page<GameTemplate> findAllByNameContainsIgnoreCase(String searchText, Pageable pageable);
}
