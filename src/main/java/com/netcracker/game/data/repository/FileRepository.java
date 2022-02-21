package com.netcracker.game.data.repository;

import com.netcracker.game.data.model.File;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FileRepository extends PagingAndSortingRepository<File, Integer> {

    List<File> findByType(String type);
}
