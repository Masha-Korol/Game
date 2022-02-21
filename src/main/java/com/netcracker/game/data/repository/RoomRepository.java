package com.netcracker.game.data.repository;

import com.netcracker.game.data.model.GameUser;
import com.netcracker.game.data.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface RoomRepository extends PagingAndSortingRepository<Room, Integer> {

    List<Room> findAllByRoomPlayersContains(GameUser gameUser);
    Room findByRoomId(Integer id);
    Room findByName(String name);
    Room findByCode(String code);
    boolean existsByName(String name);
    boolean existsByCode(String code);
    @Query(value = "from Room r where " +
            "upper(r.name) like concat('%', upper(:searchText), '%') and " +
            "r.roomType = 'PUBLIC' and " +
            "r.gameTemplate.numberOfPlayers - r.roomPlayers.size - (:open) >= 0")
    Page<Room> findAll(@Param("searchText") String searchText,
                       @Param("open") Integer open,
                       Pageable pageable);
}
