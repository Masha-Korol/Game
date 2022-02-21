package com.netcracker.game.services.service.impl;

import com.netcracker.game.data.model.*;
import com.netcracker.game.data.model.enums.Color;
import com.netcracker.game.data.model.enums.GameStage;
import com.netcracker.game.data.model.enums.PlayerRole;
import com.netcracker.game.data.repository.GameUserRepository;
import com.netcracker.game.data.repository.RoomRedisRepository;
import com.netcracker.game.data.repository.RoomRepository;
import com.netcracker.game.security.services.UserDetailsImpl;
import com.netcracker.game.services.dto.ButtonClickEvent;
import com.netcracker.game.services.dto.gameuser.GameUserDto;
import com.netcracker.game.services.dto.tasks.TaskResultDto;
import com.netcracker.game.services.dto.voting.VotingRequest;
import com.netcracker.game.services.dto.voting.VotingResultResponse;
import com.netcracker.game.services.mapstruct.GameUserMapper;
import com.netcracker.game.services.service.IGameService;
import com.netcracker.game.services.service.IIngameTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class GameServiceImpl implements IGameService {

  private final RoomRedisRepository roomRedisRepository;
  private final GameUserMapper gameUserMapper;
  private final GameUserRepository gameUserRepository;
  private final GameStageDeterminant gameStageDeterminant;
  private final IIngameTaskService ingameTaskService;
  private final RoomRepository roomRepository;
  private final ConcurrentMap<String, ReentrantLock> locks;
  private final static Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

  public GameServiceImpl(RoomRedisRepository roomRedisRepository,
                         GameUserMapper gameUserMapper,
                         GameUserRepository gameUserRepository,
                         GameStageDeterminant gameStageDeterminant,
                         IIngameTaskService ingameTaskService,
                         RoomRepository roomRepository) {
    this.roomRedisRepository = roomRedisRepository;
    this.gameUserMapper = gameUserMapper;
    this.gameUserRepository = gameUserRepository;
    this.gameStageDeterminant = gameStageDeterminant;
    this.ingameTaskService = ingameTaskService;
    this.roomRepository = roomRepository;
    this.locks = new ConcurrentHashMap<>();
  }

  @Override
  public void startGame(Integer roomId) {
    RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);

    if (roomEntity.isReadyToStart()) {
      ReentrantLock lock = locks.computeIfAbsent(roomId.toString(), (k) -> new ReentrantLock());
      lock.lock();
      try {
        roomEntity = roomRedisRepository.findByRoomId(roomId);
        if (roomEntity.isReadyToStart()) {
          Room currentRoom = roomRepository.findByRoomId(roomId);
          HashMap<String, PlayerRole> rolesForRound = new HashMap<>();
          List<String> players = new ArrayList<>();
          currentRoom.getRoomPlayers().forEach(player -> players.add(player.getLogin()));

          List<PlayerRole> roles = new ArrayList<>(PlayerRole.randomRoles(players.size()));
          Iterator<PlayerRole> iterRoles = roles.iterator();
          Iterator<String> iterPlayers = players.iterator();

          while (iterPlayers.hasNext() && iterRoles.hasNext())
            rolesForRound.put(iterPlayers.next(), iterRoles.next());

          String roundUUID = UUID.randomUUID().toString();

          cleanUpRoomEntity(roomEntity);

          // делаем из значений enum Color LinkedList и выдает игрокам цвета
          LinkedList<Color> enumValues = new LinkedList<>(Arrays.asList(Color.values()));
          if (roomEntity.getPlayersColors() != null) {
            roomEntity.nullifyPlayersColors();
          }

          HashMap<String, Color> playersColors = new HashMap<>();
          rolesForRound.forEach((playerLogin, playerRole) ->
                  playersColors.put(playerLogin, enumValues.pop()));
          roomEntity.setPlayersColors(playersColors);
          roomEntity.setAlivePlayersRoles(rolesForRound);
          roomEntity.setButtonClicks(currentRoom.getRoomPlayers());
          roomEntity.setGameStage(GameStage.IN_PROGRESS);
          roomEntity.setCurrentRoundUUID(roundUUID);
          roomEntity.setReadyToStart(false);

          roomRedisRepository.save(roomEntity, roomId);
        }
      } finally {
        lock.unlock();
      }
    }
  }

  private void cleanUpRoomEntity(RoomEntity roomEntity) {
    logger.info("Setting up entity for a new GAME");
    roomEntity.setReadyToStart(true);
    roomEntity.setGameOver(false);
    roomEntity.setKillerUsedAbility(false);
    roomEntity.setGameStage(null);
    roomEntity.nullifyButtonClicks();
    roomEntity.removeInvalidatedTerminals();
    roomEntity.removeTaskResults();
    roomEntity.removeVotes();
    roomEntity.nullifyRoundMapCoordinates();
    roomEntity.removeRoundMessages();
    roomEntity.removeAlivePlayers();
    roomEntity.removeVotingResultsResponse();
  }

//  private void cleanRoundInfo(RoomEntity roomEntity) {
//    logger.info("Setting up entity for a new ROUND");
//    String roundUUID = UUID.randomUUID().toString();
//    roomEntity.setKillerUsedAbility(false);
//    roomEntity.nullifyButtonClicks();
//    roomEntity.removeVotes();
//    roomEntity.nullifyRoundMapCoordinates();
//    roomEntity.removeTaskResults();
//    roomEntity.removeInvalidatedTerminals();
//    roomEntity.setCurrentRoundUUID(roundUUID);
//    roomEntity.removeRoundMessages();
//  }

  @Override
  public void submitTaskResult(Integer roomId, String roundUUID, String gameUserLogin, String terminalUUID, String taskResult) {
    RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);
    TaskResultDto dto = ingameTaskService.getTaskResult(roundUUID, terminalUUID, taskResult);
    roomEntity.addTaskResult(gameUserLogin, dto.isTaskSucceeded());
    roomRedisRepository.save(roomEntity, roomId);
  }

  @Override
  public HashMap<String, Boolean> checkTaskResults(Integer roomId, String roundUUID) {
    RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);
    return roomEntity.getTaskResults();
  }

  @Override
  public void vote(VotingRequest votingRequest) {
    Integer roomId = votingRequest.getRoomId();
    String login = votingRequest.getGameUserLogin();
    RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);
    // если на предыдущем шаге произошло разделение голосов, обнуляем VotesSeparationPlayers
    if (roomEntity.getVotesForRound() == null && roomEntity.getVotingResultResponse() != null &&
            roomEntity.getVotingResultResponse().getVotesSeparationPlayers() != null) {
      roomEntity.nullifyVotingResultResponse();
    }
    roomEntity.addVote(login);
//    logger.info("Vote() before calls defineGameStage: " + roomEntity.toString());
    gameStageDeterminant.defineGameStage(roomEntity);
//    logger.info("After: " + roomEntity.toString());
    roomRedisRepository.save(roomEntity, roomId);
  }

  private VotingResultResponse configureVotingResult(Integer roomId) {
    RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);
    HashMap<String, Integer> votesForRound = roomEntity.getVotesForRound();

    if (votesForRound == null) {
      return null;
    }

    List<Integer> values = new ArrayList<>(votesForRound.values());
    values.sort(Collections.reverseOrder());

    // если голоса разделились
    //if (values.size() > 1 && values.get(0).equals(values.get(1))) {
    if (roomEntity.haveVotesSeparated()) {
      ArrayList<String> votesSeparationPlayers = new ArrayList<>();
      //ищем всех игроков, за которых наибольшее количество голосов
      for (Map.Entry<String, Integer> entry : votesForRound.entrySet()) {
        if (entry.getValue().equals(values.get(0))) {
          votesSeparationPlayers.add(entry.getKey());
        }
      }
      VotingResultResponse votingResultResponse = new VotingResultResponse(votesSeparationPlayers);
      roomEntity.setVotingResultResponse(votingResultResponse);
      roomEntity.nullifyVotesForRound();
      roomRedisRepository.save(roomEntity, roomId);
//      logger.info("votes have separated; room saved: {}", roomEntity.toString());
      return votingResultResponse;
    }

    // если игрок с максимальным количеством голосов один, ищем его среди голосов и уничтожаем
    for (Map.Entry<String, Integer> entry : votesForRound.entrySet()) {
      if (entry.getValue().equals(values.get(0))) {
        GameUser gameUser = gameUserRepository.findByLogin(entry.getKey()).orElseThrow();
        GameUserDto gameUserDto = gameUserMapper.toDto(gameUser);
        HashMap<String, PlayerRole> alivePlayers = roomEntity.getAlivePlayersRoles();

        // считаем количество оставшихся мирных и предателей
        int civiliansCount = 0;
        int traitorsCount = 0;
        for (Map.Entry<String, PlayerRole> entry1 : alivePlayers.entrySet()) {
          if (entry1.getValue().equals(PlayerRole.CIVILIAN)) {
            civiliansCount++;
          } else {
            traitorsCount++;
          }
        }

        // корректируем количество оставштхся мирных/предателей, уменьшая число,
        // соответствующее роли убитого игрока, на 1
        PlayerRole playerRole = roomEntity.getAlivePlayersRoles().get(gameUserDto.getLogin());

        switch (playerRole) {
          case CIVILIAN:
            civiliansCount--;
            break;
          case TRAITOR:
            traitorsCount--;
            break;
        }

        VotingResultResponse votingResultResponse = new VotingResultResponse(gameUserDto.getLogin(), playerRole.toString(),
                civiliansCount, traitorsCount);
        roomEntity.setVotingResultResponse(votingResultResponse);
        roomEntity.setPlayerDead(gameUserDto.getLogin());
        roomRedisRepository.save(roomEntity, roomId);
        return votingResultResponse;
      }
    }
    return null;
  }

  @Override
  public VotingResultResponse getVotingResult(Integer roomId) {
    RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);

    // если все игроки проголосовали
    if (roomEntity.getVotesForRound() != null &&
            roomEntity.haveAllPlayersVoted()) {
      if (roomEntity.getVotingResultResponse() == null) {
        return configureVotingResult(roomId);
      } else {
        return roomEntity.getVotingResultResponse();
      }
    } else if (roomEntity.getVotingResultResponse() != null) {
      return roomEntity.getVotingResultResponse();
    } else return null;
  }

  @Override
  public String getRoundUUID(Integer roomId) {
    RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);
    if (Objects.nonNull(roomEntity) && Objects.nonNull(roomEntity.getCurrentRoundUUID())) {
      return roomEntity.getCurrentRoundUUID();
    } else return "";
  }

  @Override
  public GameStage getGameStage(Integer roomId, String username) {
    ReentrantLock lock = locks.computeIfAbsent(roomId.toString(), (k) -> new ReentrantLock());
    lock.lock();

    try {
      RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);
      GameStage gameStage = roomEntity.getGameStage();

      boolean clicksExists = !roomEntity.getButtonClicks().isEmpty();
      boolean noClicks = roomEntity.getButtonClicks().entrySet().stream().allMatch(e -> e.getValue().equals(false));
      boolean roundNotNull = roomEntity.getCurrentRoundUUID() != null;
      boolean isGameOver = roomEntity.isGameOver();

      // можем рестартить игру и сообщить фронту о том,
      // что пора показывать кнопку "начать играть"
      if (clicksExists && noClicks && roundNotNull && isGameOver) {
        if (!roomEntity.getCurrentRoundUUID().isEmpty()) {
          roomEntity.setCurrentRoundUUID(null);
          roomEntity.setGameStage(GameStage.GAME_RESULT);
          roomEntity.setReadyToStart(true);
          roomRedisRepository.save(roomEntity, roomId);
          return GameStage.GAME_RESULT;
        }
      }

      // если начинаем новый раунд и он не первый, очищаем информацию о предыдущем
//      if (gameStage != null && gameStage.equals(GameStage.IN_PROGRESS) &&
//              roomEntity.getVotingResultResponse() != null &&
//              roomEntity.getAlivePlayersRoles() != null) {
//        cleanRoundInfo(roomEntity);
//      }

      // если остался один цивил и один предатель - заканчиваем игру
      if (roomEntity.getAlivePlayersRoles() != null &&
              roomEntity.getAlivePlayersRoles().size() == 2 &&
              roomEntity.getAlivePlayersRoles().containsValue(PlayerRole.CIVILIAN) &&
              roomEntity.getAlivePlayersRoles().containsValue(PlayerRole.TRAITOR) && !roomEntity.isGameOver()) {
        roomEntity.setGameOver(true);
        roomEntity.nullifyButtonClick(username);
      }

      // если последний раунд, очищаем alivePlayersRoles и ставим флаг gameOver
      if (roomEntity.getVotingResultResponse() != null) {
        VotingResultResponse vrr = roomEntity.getVotingResultResponse();
        boolean votesSeparated = vrr.getVotesSeparationPlayers() != null;
        boolean votesWasInitialized = vrr.getTraitorsCount() != null ||
                vrr.getCiviliansCount() != null;
        boolean civiliansWin = vrr.getTraitorsCount() == 0;
        boolean traitorsWin = vrr.getCiviliansCount() <= 1 && vrr.getTraitorsCount() != 0;
        if (!votesSeparated && votesWasInitialized && (civiliansWin || traitorsWin) && !roomEntity.isGameOver()) {
          roomEntity.setGameOver(true);
          roomEntity.nullifyButtonClick(username);
        }
      }
      roomRedisRepository.save(roomEntity, roomId);
      GameStage curGameStage = roomEntity.getGameStage();
      return curGameStage;
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void clickButton(ButtonClickEvent buttonClickEvent) {
    RoomEntity roomEntity = roomRedisRepository.findByRoomId(buttonClickEvent.getRoomId());
    logger.info("took {}", roomRedisRepository.findByRoomId(buttonClickEvent.getRoomId()).toString());

    roomEntity.addButtonClick(buttonClickEvent.getClickedGameUserLogin());
    gameStageDeterminant.defineGameStage(roomEntity);

    roomRedisRepository.save(roomEntity, buttonClickEvent.getRoomId());
    logger.info("saved {}", roomRedisRepository.findByRoomId(buttonClickEvent.getRoomId()).toString());
  }


  @Override
  public void updateRoundMapCoordinates(CoordinatesStructure structure) {
    ReentrantLock lock = locks.computeIfAbsent(structure.getPlayerLogin() + structure.getRoomId().toString(), (k) -> new ReentrantLock());
    lock.lock();
    try {
      RoomEntity roomEntity = roomRedisRepository.findByRoomId(structure.getRoomId());
      roomEntity.updateRoundMapCoordinates(structure);
      roomRedisRepository.save(roomEntity, structure.getRoomId());
    } finally {
      lock.unlock();
    }
  }

  @Override
  public List<CoordinatesStructure> getRoundMapCoordinates(Integer roomId) {
    RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);
    return roomEntity.getRoundMapCoordinates();
  }

  @Override
  public void invalidateTerminal(TerminalInfo terminalInfo) {
    RoomEntity roomEntity = roomRedisRepository.findByRoomId(terminalInfo.getRoomId());
    roomEntity.addInvalidatedTerminal(terminalInfo.getTerminalUUID());
    roomRedisRepository.save(roomEntity, terminalInfo.getRoomId());
  }

  @Override
  public Set<String> getInvalidatedTerminals(Integer roomId) {
    RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);
    if (!Objects.isNull(roomEntity)) {
      return roomEntity.getInvalidateTerminals();
    } else {
      return new HashSet<>();
    }
  }

  @Override
  public HashMap<String, PlayerRole> getPlayersRoles(Integer roomId) {
    RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);
    return roomEntity.getAlivePlayersRoles();
  }

  @Override
  public List<String> getAlivePlayers(Integer roomId) {
    RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);
    return roomEntity.getAlivePlayersUsernames();
  }

  @Override
  public HashMap<String, Color> getPlayersColors(Integer roomId) {
    RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);
    return roomEntity.getPlayersColors();
  }

  @Override
  public void killPlayer(PlayerDeathInfo playerDeathInfo) {

    CoordinatesStructure victim = playerDeathInfo.getVictimInfo();
    CoordinatesStructure killer = playerDeathInfo.getKillerInfo();
    String senderName = "";

    RoomEntity roomEntity = roomRedisRepository.findByRoomId(killer.getRoomId());

    if (!roomEntity.getKillerUsedAbility()) {
      ReentrantLock lock = locks.computeIfAbsent(killer.getPlayerLogin(), (k) -> new ReentrantLock());
      lock.lock();
      try {
        roomEntity = roomRedisRepository.findByRoomId(killer.getRoomId());
        if (!roomEntity.getKillerUsedAbility()) {
          UserDetailsImpl details = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
          if (Objects.nonNull(details)) {
            senderName = details.getUsername();
          }

          // sanity checks
          boolean isSenderAndKillerSameUsername = !senderName.isEmpty() && senderName.equals(killer.getPlayerLogin());
          boolean isKillerNearVictim = Math.abs(Math.abs(victim.getX() - killer.getX())) < 10d &&
                  Math.abs(victim.getY() - killer.getY()) < 10d;
          boolean isSameUsername = killer.getPlayerLogin().equals(victim.getPlayerLogin());
          boolean isSameRoomId = killer.getRoomId().equals(victim.getRoomId());

          if (isSenderAndKillerSameUsername && !isSameUsername && isKillerNearVictim && isSameRoomId) {
            List<String> alivePlayers = roomEntity.getAlivePlayersUsernames();
            if (alivePlayers.stream().anyMatch(victim.getPlayerLogin()::equals)) {
              roomEntity.setPlayerDead(victim.getPlayerLogin());
              roomEntity.setKillerUsedAbility(true);
              roomRedisRepository.save(roomEntity, roomEntity.getRoomId());
            }
          }
        }
      } finally {
        lock.unlock();
      }
    }
  }
}
