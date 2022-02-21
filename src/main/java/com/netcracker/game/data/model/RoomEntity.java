package com.netcracker.game.data.model;

import com.netcracker.game.data.model.enums.Color;
import com.netcracker.game.data.model.enums.GameStage;
import com.netcracker.game.data.model.enums.PlayerRole;
import com.netcracker.game.services.dto.voting.VotingResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;
import java.util.Map;

public class RoomEntity implements Serializable {
    private Integer roomId;
    private List<Message> chatMessages;
    private HashMap<String, Boolean> taskResults;
    private HashMap<String, Integer> votesForRound;
    private HashMap<String, PlayerRole> alivePlayersRoles;
    private List<Message> roundMessages;
    private GameStage gameStage;
    private String currentRoundUUID;
    private Set<String> invalidateTerminals;
    private HashMap<String, Boolean> buttonClicks;
    private VotingResultResponse votingResultResponse;
    private List<CoordinatesStructure> roundMapCoordinates;
    private Boolean isReadyToStart;
    private Boolean isKillerUsedAbility;
    private Boolean isGameOver;
    private HashMap<String, Color> playersColors;

    private final static Logger logger = LoggerFactory.getLogger(RoomEntity.class);


    public HashMap<String, Color> getPlayersColors() {
        return playersColors;
    }

    public void setPlayersColors(HashMap<String, Color> playersColors) {
        this.playersColors = playersColors;
    }

    public void addPlayersColor(String playerLogin, Color color) {
        if (playersColors == null) {
            this.playersColors = new HashMap<>();
        }
        this.playersColors.put(playerLogin, color);
    }

    public Boolean isGameOver() {
        if (Objects.isNull(isGameOver)) {
            return false;
        } else {
            return isGameOver;
        }
    }

    public void removeInvalidatedTerminals() {
        if (!Objects.isNull(this.invalidateTerminals)) {
            this.invalidateTerminals.clear();
        } else this.invalidateTerminals = new HashSet<>();
    }

    public void addInvalidatedTerminal(String terminalUUID) {
        if (Objects.isNull(this.invalidateTerminals)) {
            this.invalidateTerminals = new HashSet<>();
        }
        this.invalidateTerminals.add(terminalUUID);
    }

    public Set<String> getInvalidateTerminals() {
        return this.invalidateTerminals;
    }

    public void setGameOver(Boolean gameOver) {
        isGameOver = gameOver;
    }

    public List<CoordinatesStructure> getRoundMapCoordinates() {
        return roundMapCoordinates;
    }

    public void updateRoundMapCoordinates(CoordinatesStructure newStructure) {
        if (roundMapCoordinates == null) {
            roundMapCoordinates = new ArrayList<>();
        }

        // удалить старые координаты из листа, если они есть
        roundMapCoordinates.removeIf(structure -> structure.getPlayerLogin().equals(newStructure.getPlayerLogin()));
        roundMapCoordinates.add(newStructure);
    }

    // возвращает true, если все игроки проголосовали и голоса разделились
    public boolean haveVotesSeparated() {
        List<Integer> values = new ArrayList<>(votesForRound.values());
        values.sort(Collections.reverseOrder());
        return haveAllPlayersVoted() && values.size() > 1 && values.get(0).equals(values.get(1));
    }

    public void nullifyRoundMapCoordinates() {
        this.roundMapCoordinates = null;
    }

    public void nullifyPlayersColors() {
        this.playersColors = null;
    }

    public void nullifyVotesForRound() {
        this.votesForRound = null;
    }

    public void nullifyVotingResultResponse() {
        this.votingResultResponse = null;
    }

    public void addMessage(Message message) {
        if (this.chatMessages == null) {
            this.chatMessages = new ArrayList<>();
        }
        this.chatMessages.add(message);
    }

    public void addRoundMessage(Message message) {
        if (this.roundMessages == null) {
            this.roundMessages = new ArrayList<>();
        }
        this.roundMessages.add(message);
    }

    public void addVote(String gameUserLogin) {
        if (this.votesForRound == null) {
            this.votesForRound = new HashMap<>();
        }
        if (this.votesForRound.containsKey(gameUserLogin)) {
            Integer votesCount = this.votesForRound.get(gameUserLogin);
            this.votesForRound.replace(gameUserLogin, ++votesCount);
        } else {
            this.votesForRound.put(gameUserLogin, 1);
        }
    }

    public void addTaskResult(String gameUserLogin, Boolean result) {
        if (this.taskResults == null) {
            this.taskResults = new HashMap<>();
        }
        this.taskResults.put(gameUserLogin, result);
    }

    public HashMap<String, Boolean> getButtonClicks() {
        if (Objects.isNull(buttonClicks)) {
            return new HashMap<>();
        } else return buttonClicks;
    }

    public void setButtonClicks(Set<GameUser> roomPlayers) {
        roomPlayers.forEach(gameUser -> buttonClicks.put(gameUser.getLogin(), false));
    }

    public void setAlivePlayersRoles(HashMap<String, PlayerRole> rolesForRound) {
        this.alivePlayersRoles = rolesForRound;
    }

    public void setPlayerDead(String username) {
        if (Objects.nonNull(this.alivePlayersRoles)) {
            this.alivePlayersRoles.remove(username);
        }
        if (Objects.nonNull(this.buttonClicks)) {
            this.buttonClicks.remove(username);
        }
        if (Objects.nonNull(this.taskResults)) {
            this.taskResults.remove(username);
        }
    }

    public List<String> getAlivePlayersUsernames() {
        return new ArrayList<>(this.alivePlayersRoles.keySet());
    }

    public void nullifyButtonClicks() {
        for (Map.Entry<String, Boolean> entry : this.getButtonClicks().entrySet()) {
            entry.setValue(false);
        }
    }

    public void addButtonClick(String gameUserLogin) {
        if (buttonClicks == null) {
            buttonClicks = new HashMap<>();
        }
        this.buttonClicks.put(gameUserLogin, true);
    }

    public void nullifyButtonClick(String gameUserLogin) {
        if (Objects.nonNull(buttonClicks)) {
            this.buttonClicks.put(gameUserLogin, false);
        }
    }

    public boolean hasAnybodyClicked() {
        return this.buttonClicks.entrySet().stream().anyMatch(e -> e.getValue().equals(true));
    }

    public boolean haveAllPlayersClicked() {
        return this.buttonClicks.entrySet().stream().allMatch(e -> e.getValue().equals(true));
    }

    public boolean haveAllPlayersVoted() {
        List<Integer> values = new ArrayList<>(votesForRound.values());
        int numberOfVotedPayers = 0;
        for (int value : values) {
            numberOfVotedPayers += value;
        }
        return numberOfVotedPayers == alivePlayersRoles.size();
    }

    public boolean haveAllPlayersClickedExceptForOne() {
        int falsesCount = 0;
        for (Map.Entry<String, Boolean> entry1 : this.buttonClicks.entrySet()) {
            if (!entry1.getValue()) {
                falsesCount++;
            }
        }
        if (falsesCount == 1) {
            return true;
        } else {
            return false;
        }
    }

    public GameStage getGameStage() {
        return gameStage;
    }

    public void setGameStage(GameStage gameStage) {
        this.gameStage = gameStage;
    }

    public HashMap<String, PlayerRole> getAlivePlayersRoles() {
        return alivePlayersRoles;
    }

    public void removeAlivePlayers() {
        if (Objects.nonNull(this.alivePlayersRoles)) {
            this.alivePlayersRoles.clear();
        } else this.alivePlayersRoles = new HashMap<>();
    }

    public List<Message> getRoundMessages() {
        return roundMessages;
    }

    public void removeRoundMessages() {
        if (this.roundMessages != null) {
            this.roundMessages.clear();
        }
    }

    public void removeTaskResults() {
        if (!Objects.isNull(this.taskResults)) {
            this.taskResults.clear();
        }
    }

    public void setTaskResults(HashMap<String, Boolean> taskResults) {
        this.taskResults = taskResults;
    }

    public HashMap<String, Boolean> getTaskResults() {
        return this.taskResults;
    }

    public void removeVotes() {
        if (this.votesForRound != null) {
            this.votesForRound.clear();
        }
    }

    public HashMap<String, Integer> getVotesForRound() {
        return votesForRound;
    }

    public VotingResultResponse getVotingResultResponse() {
        return votingResultResponse;
    }

    public void setVotingResultResponse(VotingResultResponse votingResultResponse) {
        this.votingResultResponse = votingResultResponse;
    }

    public void removeVotingResultsResponse() {
        this.votingResultResponse = null;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public List<Message> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<Message> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public void clearRoundInfo() {
        String roundUUID = UUID.randomUUID().toString();
        logger.info("starting new ROUND: {}", roundUUID);
        this.setKillerUsedAbility(false);
        this.nullifyButtonClicks();
        this.removeVotingResultsResponse();
        this.removeVotes();
        this.nullifyRoundMapCoordinates();
        this.removeTaskResults();
        this.removeInvalidatedTerminals();
        this.setCurrentRoundUUID(roundUUID);
        this.removeRoundMessages();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomEntity that = (RoomEntity) o;
        return Objects.equals(roomId, that.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId);
    }

    @Override
    public String toString() {
        return "RoomEntity{" +
                "\n\troomId=" + roomId +
                ", \n\troundUUID=" + currentRoundUUID +
                ", \n\tisReadyToStart=" + isReadyToStart +
//                ", chatMessages=" + chatMessages +
                ", \n\ttaskResults=" + taskResults +
                ", \n\tvotesForRound=" + votesForRound +
                ", \n\talivePlayersRoles=" + alivePlayersRoles +
//                ", roundMessages=" + roundMessages +
                ", \n\tgameStage=" + gameStage +
                ", \n\tbuttonClicks=" + buttonClicks +
                ", \n\tvotingResultResponse=" + votingResultResponse +
//                ", roundMapCoordinates=" + roundMapCoordinates +
                ", \n\tinvalidatedTerminals=" + invalidateTerminals +
                ", playersColors=" + playersColors +
                "\n}";
    }

    public String getCurrentRoundUUID() {
        return currentRoundUUID;
    }

    public void setCurrentRoundUUID(String currentRoundUUID) {
        this.currentRoundUUID = currentRoundUUID;
    }

    public boolean isReadyToStart() {
        return isReadyToStart;
    }

    public void setReadyToStart(boolean readyToStart) {
        isReadyToStart = readyToStart;
    }

    public Boolean getKillerUsedAbility() {
        if (Objects.isNull(isKillerUsedAbility)) {
            return false;
        } else return isKillerUsedAbility;
    }

    public void setKillerUsedAbility(Boolean killerUsedAbility) {
        isKillerUsedAbility = killerUsedAbility;
    }
}
