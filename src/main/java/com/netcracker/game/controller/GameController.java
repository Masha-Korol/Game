package com.netcracker.game.controller;

import com.netcracker.game.data.model.CoordinatesStructure;
import com.netcracker.game.data.model.RoomEntity;
import com.netcracker.game.data.model.enums.Color;
import com.netcracker.game.data.model.enums.GameStage;
import com.netcracker.game.data.model.enums.PlayerRole;
import com.netcracker.game.data.repository.RoomRedisRepository;
import com.netcracker.game.security.services.UserDetailsImpl;
import com.netcracker.game.services.dto.tasks.IngameTaskDto;
import com.netcracker.game.services.dto.voting.VotingResultResponse;
import com.netcracker.game.services.service.IGameService;
import com.netcracker.game.services.service.impl.GameServiceImpl;
import com.netcracker.game.services.service.IIngameTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/games")
@CrossOrigin
public class GameController {

  private final IGameService gameService;
  private final IIngameTaskService ingameTaskService;
  private final RoomRedisRepository roomRedisRepository;
  private final static Logger logger = LoggerFactory.getLogger(GameController.class);


  public GameController(GameServiceImpl gameService, IIngameTaskService ingameTaskService, RoomRedisRepository roomRedisRepository) {
    this.gameService = gameService;
    this.ingameTaskService = ingameTaskService;
    this.roomRedisRepository = roomRedisRepository;
  }

  // at game start
  @GetMapping("/start/{roomId}")
  public ResponseEntity<?> startGame(@PathVariable("roomId") Integer roomId) {
    UserDetailsImpl details = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    logger.info("{} trying to start a new game for a [{}]", details.getUsername(), roomId);
    gameService.startGame(roomId);
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

    @GetMapping("/round/{roomId}")
    public List<String> getRoundUUID(@PathVariable("roomId") Integer roomId) {
        List<String> res = new ArrayList<>();
        res.add(gameService.getRoundUUID(roomId).replace("-", ""));
        return res;
    }

//  @GetMapping("/task/term/{uuid}")
//  public Set<String> getTerminalsUUIDs(@PathVariable("uuid") String roundUUID) {
//    return this.ingameTaskService.getTerminalsUUIDs(roundUUID, new ArrayList<>());
//  }

  @PostMapping("/task/term/{uuid}")
  public Set<String> generateTerminalsUUIDs(@PathVariable("uuid") String roundUUID,
                                            @RequestBody HashMap<String, List<String>> requestData) {
    List<Integer> taskTypeIds = requestData.get("taskIds").stream().map(Integer::parseInt).collect(Collectors.toList());
    try {
      return ingameTaskService.getTerminalsUUIDs(roundUUID, taskTypeIds);
    } catch (Exception e) {
      logger.error(e.toString());
      return new HashSet<>();
    }
  }

    // at user interaction with "terminal"
    @GetMapping("/task/{uuid}")
    public IngameTaskDto getTaskByTerminalUUID(@PathVariable("uuid") String roundUUID,
                                               @RequestParam("terminal_id") String terminalUUID) {
        return ingameTaskService.getTaskByTerminal(roundUUID, terminalUUID);
    }

  // at the user successful end of interaction with "terminal"
  @PostMapping("/task/res/{roomId}")
  public ResponseEntity<?> submitTaskResult(@PathVariable("roomId") Integer roomId,
                                            @RequestBody Map<String, String> requestBody) {
    UserDetailsImpl details = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);
    if (Objects.nonNull(details) && Objects.nonNull(roomEntity)) {
      String userLogin = details.getUsername();
      String roundUUID = requestBody.get("roundId");
      String terminalUUID = requestBody.get("terminalId");
      String taskResult = requestBody.get("taskResult");
      if (Objects.isNull(roomEntity.getTaskResults())) {
        gameService.submitTaskResult(roomId, roundUUID, userLogin, terminalUUID, taskResult);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
      } else if (!roomEntity.getTaskResults().containsKey(userLogin)) {
        gameService.submitTaskResult(roomId, roundUUID, userLogin, terminalUUID, taskResult);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
      } else {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
      }
    } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }

  // at the end of a round
  @GetMapping("/task/res/{roomId}")
  //       username, isDone as bool
  public List<Map<String, String>> checkTaskResults(@PathVariable("roomId") Integer roomId,
                                                    @RequestParam("round_id") String roundUUID) {
    HashMap<String, Boolean> result = gameService.checkTaskResults(roomId, roundUUID);
    List<Map<String, String>> response = new ArrayList<>();
    if (Objects.nonNull(result)) {
      result.forEach((k, v) -> {
        Map<String, String> res = new HashMap<>();
        res.put("username", k);
        res.put("isCorrect", v.toString());
        response.add(res);
      });
    }
     return response;
  }

//  // at the end of round (voting phase)
//  @PostMapping("/vote/{roomId}")
//  public ResponseEntity<?> vote(@PathVariable("roomId") Integer roomId) {
//    UserDetailsImpl details = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    if (!Objects.isNull(details)) {
//        String userLogin = details.getUsername();
//        gameService.vote(userLogin, roomId);
//        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//      } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//  }

    @GetMapping("/result/{roomId}")
    public VotingResultResponse getVotingResult(@PathVariable("roomId") Integer roomId) {
        return gameService.getVotingResult(roomId);
    }

    @GetMapping("/stage/{roomId}")
    public GameStage getGameStage(@PathVariable("roomId") Integer roomId) {
        UserDetailsImpl details = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.nonNull(details)) {
          GameStage gameStage = gameService.getGameStage(roomId, details.getUsername());
          return gameStage;
        } else return GameStage.GAME_RESULT;
    }

    @GetMapping("/coord/{roomId}")
    public List<CoordinatesStructure> getPlayersRoundCoordinates(
            @PathVariable("roomId") Integer roomId) {
        return gameService.getRoundMapCoordinates(roomId);
    }

    @GetMapping("/terminals/{roomId}")
    public Set<String> getInvalidTerminals(@PathVariable("roomId") Integer roomId) {
        return gameService.getInvalidatedTerminals(roomId);
    }

    @GetMapping("/players/state/{roomId}")
    public List<String> alivePlayers(@PathVariable("roomId") Integer roomId) {
        return gameService.getAlivePlayers(roomId);
    }

    @GetMapping("/players/roles/{roomId}")
    public HashMap<String, PlayerRole> getPlayersRoles(@PathVariable("roomId") Integer roomId) {
        return gameService.getPlayersRoles(roomId);
    }

    @GetMapping("/players/colors/{roomId}")
    public HashMap<String, Color> getPlayersColors(@PathVariable("roomId") Integer roomId) {
        return gameService.getPlayersColors(roomId);
    }
}
