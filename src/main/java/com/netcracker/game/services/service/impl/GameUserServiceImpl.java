package com.netcracker.game.services.service.impl;

import com.netcracker.game.data.model.AuthorizationRole;
import com.netcracker.game.data.model.enums.ERoleType;
import com.netcracker.game.data.model.GameUser;
import com.netcracker.game.data.model.Room;
import com.netcracker.game.data.repository.AuthorizationRoleRepository;
import com.netcracker.game.data.repository.GameUserRepository;
import com.netcracker.game.data.repository.RoomRepository;
import com.netcracker.game.services.dto.gameuser.GameUserDto;
import com.netcracker.game.services.dto.gameuser.GameUserInfoDto;
import com.netcracker.game.services.mapstruct.GameUserInfoMapper;
import com.netcracker.game.services.mapstruct.GameUserMapper;
import com.netcracker.game.services.service.IGameUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GameUserServiceImpl implements IGameUserService {

    private final GameUserRepository gameUserRepository;
    private final GameUserMapper gameUserMapper;
    private final RoomRepository roomRepository;
    private final GameUserInfoMapper gameUserInfoMapper;

    @Autowired
    AuthorizationRoleRepository authorizationRoleRepository;

    public GameUserServiceImpl(GameUserRepository gameUserRepository, GameUserMapper gameUserMapper, RoomRepository roomRepository, GameUserInfoMapper gameUserInfoMapper) {
        this.gameUserRepository = gameUserRepository;
        this.gameUserMapper = gameUserMapper;
        this.roomRepository = roomRepository;
        this.gameUserInfoMapper = gameUserInfoMapper;
    }

    @Override
    public void addUser(GameUserDto dto) {
        GameUser newUser = gameUserMapper.toEntity(dto);

        /*  On db start we suppose to already have ROLE_USER at least
            but if don't - we are going to add it to our authorizationRoleRepository
            so any registered user will have ERoleType.ROLE_USER afterwards */
        Set<AuthorizationRole> newUserRoles = new HashSet<>();
        boolean isRolePresentedInRepository = authorizationRoleRepository.findByRoleName(ERoleType.ROLE_USER).isPresent();
        AuthorizationRole standardRole;
        if (isRolePresentedInRepository) {
            standardRole = authorizationRoleRepository.findByRoleName(ERoleType.ROLE_USER).get();
        } else {
            standardRole = new AuthorizationRole();
            standardRole.setRoleName(ERoleType.ROLE_USER);
            authorizationRoleRepository.save(standardRole);
        }
        newUserRoles.add(standardRole);
        newUser.setRoles(newUserRoles);
        gameUserRepository.save(newUser);
    }

    @Override
    public List<GameUserDto> getAll() {
        return gameUserMapper.toDto(gameUserRepository.findAll());
    }

    @Override
    public List<GameUserInfoDto> getAllByRoomId(Integer roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        List<GameUser> gameUsers =  gameUserRepository.findAllByRoomsContains(room);
        Iterable<GameUser> all = gameUserRepository.findAll();
        return gameUserInfoMapper.toDto(gameUsers);
    }
}
