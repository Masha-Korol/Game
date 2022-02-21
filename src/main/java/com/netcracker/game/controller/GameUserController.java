package com.netcracker.game.controller;

import com.netcracker.game.services.dto.gameuser.GameUserDto;
import com.netcracker.game.services.dto.gameuser.GameUserInfoDto;
import com.netcracker.game.services.service.IGameUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/users")
public class GameUserController {

    private final IGameUserService gameUserService;

    public GameUserController(IGameUserService gameUserService) {
        this.gameUserService = gameUserService;
    }

    @PostMapping("/")
    public void addUser(@RequestBody GameUserDto dto) {
        gameUserService.addUser(dto);
    }

    @GetMapping("/")
    public List<GameUserDto> getAll() {
        return gameUserService.getAll();
    }

    @GetMapping("/{roomId}")
    public List<GameUserInfoDto> getAllByRoomId(@PathVariable("roomId") Integer roomId){
        return gameUserService.getAllByRoomId(roomId);
    }
}
