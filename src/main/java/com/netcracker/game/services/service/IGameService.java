package com.netcracker.game.services.service;

import com.netcracker.game.data.model.CoordinatesStructure;
import com.netcracker.game.data.model.PlayerDeathInfo;
import com.netcracker.game.data.model.TerminalInfo;
import com.netcracker.game.data.model.enums.Color;
import com.netcracker.game.data.model.enums.GameStage;
import com.netcracker.game.data.model.enums.PlayerRole;
import com.netcracker.game.services.dto.ButtonClickEvent;
import com.netcracker.game.services.dto.voting.VotingRequest;
import com.netcracker.game.services.dto.voting.VotingResultResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.HashMap;
import java.util.Set;

@Service
public interface IGameService {
    void startGame(Integer roomId);
    void submitTaskResult(Integer roomId, String roundUUID, String gameUserLogin, String terminalUUID, String taskResult);
    HashMap<String, Boolean> checkTaskResults(Integer roomId, String roundUUID);
    void vote(VotingRequest votingRequest);
    VotingResultResponse getVotingResult(Integer roomId);
    GameStage getGameStage(Integer roomId, String username);
    void clickButton(ButtonClickEvent buttonClickEvent);
    void updateRoundMapCoordinates(CoordinatesStructure structure);
    List<CoordinatesStructure> getRoundMapCoordinates(Integer roomId);
    void invalidateTerminal(TerminalInfo terminalInfo);
    Set<String> getInvalidatedTerminals(Integer roomId);
    String getRoundUUID(Integer roomId);
    HashMap<String, PlayerRole> getPlayersRoles(Integer roomId);
    List<String> getAlivePlayers(Integer roomId);
    void killPlayer(PlayerDeathInfo playerDeathInfo);
    HashMap<String, Color> getPlayersColors(Integer roomId);
}
