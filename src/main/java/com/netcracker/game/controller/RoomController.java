package com.netcracker.game.controller;

import com.netcracker.game.exception.BadInputDataException;
import com.netcracker.game.security.services.UserDetailsImpl;
import com.netcracker.game.services.dto.pagination.PageDto;
import com.netcracker.game.services.dto.pagination.PageRequestDto;
import com.netcracker.game.services.dto.room.RoomCreationDto;
import com.netcracker.game.services.dto.room.RoomDto;
import com.netcracker.game.services.service.IRoomService;
import com.netcracker.game.services.service.impl.MessageServiceImpl;
import com.netcracker.game.services.service.impl.RoomServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping(path = "/rooms")
@CrossOrigin
public class RoomController {

    private final IRoomService roomService;
    private final MessageServiceImpl messageService;

    public RoomController(RoomServiceImpl roomService, MessageServiceImpl messageService) {
        this.roomService = roomService;
        this.messageService = messageService;
    }

    @PostMapping("/")
    public RoomDto addRoom(@RequestBody RoomCreationDto dto) throws BadInputDataException {
        return roomService.addRoom(dto);
    }

    @GetMapping("/add/player/{roomId}")
    public RoomDto addRoomPlayer(@PathVariable("roomId") Integer roomId,
                           @RequestParam String gameUserLogin) throws NoSuchElementException{
        return roomService.addRoomPlayer(roomId, gameUserLogin);
    }

    @GetMapping("/add/player/private")
    public RoomDto addPrivateRoomPlayer(@RequestParam String code,
                                     @RequestParam String gameUserLogin){
        return roomService.addPrivateRoomPlayer(code, gameUserLogin);
    }

    @GetMapping("/exit/{roomId}/{userName}")
    public ResponseEntity<?> exitRoom(@PathVariable("roomId") Integer roomId,
                                      @PathVariable("userName") String gameUserLogin){
        UserDetailsImpl details = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpStatus status;
        if (Objects.nonNull(details)) {
            if (gameUserLogin.equals(details.getUsername())) {
                roomService.exitRoom(roomId, details.getUsername());
                status = HttpStatus.ACCEPTED;
            } else status = HttpStatus.FORBIDDEN;
        } else status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).build();
    }

    @GetMapping("/")
    public PageDto<RoomDto> getAllPublic(@RequestParam String typePrivate,
                                   @RequestParam String typePublic,
                                   @RequestParam String open,
                                   @RequestParam String searchText,
                                   PageRequestDto pageRequest){
        return roomService.getAllPublic(typePrivate, typePublic, open, searchText, pageRequest);
    }

    @GetMapping("/all/{userId}")
    public List<RoomDto> getAllByUser(@PathVariable("userId") Integer userId) {
        return roomService.getAllByGameUser(userId);
    }

    @GetMapping("/{roomId}")
    public RoomDto getById(@PathVariable("roomId") Integer roomId){
        return roomService.getById(roomId);
    }

    @PutMapping("/edit")
    public void editRoom(@RequestBody RoomDto roomDto){
        roomService.editRoom(roomDto);
    }

    @GetMapping("/leader/{roomId}/{userName}")
    public ResponseEntity<?> passGameLeaderRight(@PathVariable("roomId") Integer roomId,
                                                 @PathVariable("userName") String gameUserLogin) {
        UserDetailsImpl details = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.isNull(details)) {
            roomService.passGameLeaderRight(roomId, gameUserLogin, details.getUsername());
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/last/{roomId}/{userName}")
    public boolean isTheLastGamePlayer(@PathVariable("roomId") Integer roomId,
                                       @PathVariable("userName") String gameUserLogin){
        return roomService.isTheLastGamePlayer(roomId, gameUserLogin);
    }

    @GetMapping("/players/{roomId}")
    public List<String> getAlivePlayersLogins(@PathVariable("roomId") Integer roomId){
        return roomService.getAlivePlayersLogins(roomId);
    }
}
