package com.netcracker.game.services.service.impl;

import com.netcracker.game.data.model.GameUser;
import com.netcracker.game.data.model.Room;
import com.netcracker.game.data.model.RoomEntity;
import com.netcracker.game.data.model.enums.GameStage;
import com.netcracker.game.data.model.enums.RoomType;
import com.netcracker.game.data.repository.GameUserRepository;
import com.netcracker.game.data.repository.RoomRedisRepository;
import com.netcracker.game.data.repository.RoomRepository;
import com.netcracker.game.exception.BadInputDataException;
import com.netcracker.game.services.dto.pagination.PageDto;
import com.netcracker.game.services.dto.pagination.PageRequestDto;
import com.netcracker.game.services.dto.room.RoomCreationDto;
import com.netcracker.game.services.dto.room.RoomDto;
import com.netcracker.game.services.mapstruct.RoomCreationMapper;
import com.netcracker.game.services.mapstruct.RoomMapper;
import com.netcracker.game.services.service.IRoomService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;
    private final GameUserRepository gameUserRepository;
    private final RoomMapper roomMapper;
    private final RoomCreationMapper roomCreationMapper;
    private final RoomRedisRepository roomRedisRepository;

    public RoomServiceImpl(RoomRepository roomRepository,
                           GameUserRepository gameUserRepository,
                           RoomMapper roomMapper,
                           RoomCreationMapper roomCreationMapper, RoomRedisRepository roomRedisRepository) {
        this.roomRepository = roomRepository;
        this.gameUserRepository = gameUserRepository;
        this.roomMapper = roomMapper;
        this.roomCreationMapper = roomCreationMapper;
        this.roomRedisRepository = roomRedisRepository;
    }

    @Override
    @Transactional
    public RoomDto addRoom(RoomCreationDto dto) throws BadInputDataException {
        if(roomRepository.existsByName(dto.getName())){
           throw new BadInputDataException("Room name '" + dto.getName() + "' has already been taken");
        }

        Room room = roomCreationMapper.toEntity(dto);
        room.addRoomPlayer(room.getGameLeader());
        if(RoomType.valueOf(dto.getRoomType()).equals(RoomType.PRIVATE)){
            String code = RandomStringUtils.randomAlphanumeric(5);
            while (roomRepository.existsByCode(code)) {
                code = RandomStringUtils.randomAlphanumeric(5);
            }
            room.setCode(code);
        }

        Room savedRoom = roomRepository.save(room);
        RoomEntity newRoomEntity = new RoomEntity();

        newRoomEntity.setReadyToStart(true);
        newRoomEntity.setRoomId(savedRoom.getRoomId());

        roomRedisRepository.save(newRoomEntity, savedRoom.getRoomId());
        return roomMapper.toDto(roomRepository.findByName(room.getName()));
    }

    @Override
    public PageDto<RoomDto> getAllPublic(String typePrivateS,
                                         String typePublicS,
                                         String openS,
                                         String searchText,
                                         PageRequestDto pageRequest) {
        Integer open = Boolean.parseBoolean(openS) ? 1 : 0;

        Pageable pageable = PageRequest.of(pageRequest.getPage() - 1, pageRequest.getItemsPerPage(),
                pageRequest.getSort());
        Page<Room> page = roomRepository.findAll(searchText, open, pageable);
        Page<RoomDto> pageDto = page.map(roomMapper::toDto);
        return new PageDto<RoomDto>(pageDto);
    }

    @Override
    @Transactional
    public List<RoomDto> getAllByGameUser(Integer userId) {
        GameUser gameUser = gameUserRepository.findById(userId).orElseThrow();
        List<Room> rooms = roomRepository.findAllByRoomPlayersContains(gameUser);
        return roomMapper.toDto(rooms);
    }

    @Override
    @Transactional
    public RoomDto addRoomPlayer(Integer roomId, String gameUserLogin) throws NoSuchElementException {
        Room room = roomRepository.findById(roomId).orElseThrow();
        return addRoomPlayer(gameUserLogin, room);
    }

    @Transactional
    @Override
    public RoomDto addPrivateRoomPlayer(String code, String gameUserLogin) {
        Room room = roomRepository.findByCode(code);
        if(room.getRoomType().equals(RoomType.PRIVATE)){
           return addRoomPlayer(gameUserLogin, room);
        }
        return null;
    }

    private RoomDto addRoomPlayer(String gameUserLogin, Room room) {
        if (room.getGameTemplate().getNumberOfPlayers() >= room.getRoomPlayers().size() + 1) {
            GameUser gameUser = gameUserRepository.findByLogin(gameUserLogin).orElseThrow();
            room.addRoomPlayer(gameUser);
            roomRepository.save(room);
            return roomMapper.toDto(room);
        }
        return null;
    }

    @Override
    public RoomDto getById(Integer roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isPresent()) {
            return roomMapper.toDto(room.get());
        } else return new RoomDto();
    }

    @Override
    @Transactional
    public void exitRoom(Integer roomId, String gameUserLogin) {
        Optional<GameUser> gameUser = gameUserRepository.findByLogin(gameUserLogin);
        Optional<Room> room = roomRepository.findById(roomId);
        if (gameUser.isPresent() && room.isPresent()) {
            gameUser.get().removeRoom(room.get());
            room.get().removeGameUser(gameUser.get());
            gameUserRepository.save(gameUser.get());
            if (room.get().getRoomPlayers().isEmpty()) {
                roomRepository.delete(room.get());
                return;
            }

            if (gameUser.get().equals(room.get().getGameLeader())) {
                Set<GameUser> roomPlayersSet = room.get().getRoomPlayers();
                List<GameUser> roomPlayers = new ArrayList<>(roomPlayersSet);
                GameUser randomGameUser = roomPlayers.get(new Random().nextInt(roomPlayers.size()));
                room.get().setGameLeader(randomGameUser);
            }

            roomRepository.save(room.get());
        }

    }


    @Override
    @Transactional
    public void editRoom(RoomDto roomDto) {
        Room room = roomRepository.findById(roomDto.getRoomId()).orElseThrow();
        room.setName(roomDto.getName());
        room.setRoomType(RoomType.valueOf(roomDto.getRoomType()));
        roomRepository.save(room);
    }

    @Override
    @Transactional
    public void passGameLeaderRight(Integer roomId, String passToUsername, String hostUsername) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        Optional<GameUser> optionalGameUser = gameUserRepository.findByLogin(passToUsername);
        if (optionalRoom.isPresent() && optionalGameUser.isPresent()) {
            Room room = optionalRoom.get();
            GameUser gameUser = optionalGameUser.get();
            if (room.getGameLeader().getLogin().equals(hostUsername)) {
                room.setGameLeader(gameUser);
                roomRepository.save(room);
            }
        }
    }

    @Override
    public boolean isTheLastGamePlayer(Integer roomId, String gameUserLogin) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        return room.getRoomPlayers().size() == 1;
    }

    @Override
    public List<String> getAlivePlayersLogins(Integer roomId) {
        RoomEntity room = roomRedisRepository.findByRoomId(roomId);
        return room.getAlivePlayersUsernames();
    }
}
