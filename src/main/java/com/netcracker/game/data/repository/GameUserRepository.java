package com.netcracker.game.data.repository;

import com.netcracker.game.data.model.GameUser;
import com.netcracker.game.data.model.Room;
import com.netcracker.game.services.dto.gameuser.GameUserDto;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.*;

public interface GameUserRepository extends PagingAndSortingRepository<GameUser, Integer> {

    Optional<GameUser> findByLogin(String login);

    Boolean existsByLogin(String login);

    Boolean existsByEmail(String email);

    List<GameUser> findAllByRoomsContains(Room room);
}
