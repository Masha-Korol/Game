package com.netcracker.game.data.model;

import com.netcracker.game.data.model.enums.GameStage;
import com.netcracker.game.data.repository.RoomRepository;
import org.springframework.stereotype.Component;

@Component
public class GameStageDeterminant {

    private final RoomRepository roomRepository;

    public GameStageDeterminant(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void defineGameStage(RoomEntity roomEntity) {
        if (roomEntity.hasAnybodyClicked()) {
            //первая игра (если еще не присвоен gameStage или если игра однажды уже была закончена и
            // у нее стоит флаг readyToStart)
            if (roomEntity.isReadyToStart()) {
                Room room = roomRepository.findById(roomEntity.getRoomId()).orElseThrow();
                if (roomEntity.haveAllPlayersClicked() && roomEntity.getButtonClicks().size() == room.getGameTemplate().getNumberOfPlayers()) {
                    roomEntity.setGameStage(GameStage.IN_PROGRESS);
                    roomEntity.setVotingResultResponse(null);
                    roomEntity.nullifyButtonClicks();
                }
            } else if (roomEntity.haveAllPlayersClicked() &&
                    roomEntity.getGameStage().equals(GameStage.IN_PROGRESS)) {
                roomEntity.setGameStage(GameStage.ROUND_RESULTS);
                roomEntity.nullifyButtonClicks();
            } else if (roomEntity.haveAllPlayersClicked() &&
                    roomEntity.getGameStage().equals(GameStage.VOTING_RESULTS)) {
                // все нажали на кнопку дальше (за вычетом выбывшего игрока) ---> start_new_round
                roomEntity.setGameStage(GameStage.IN_PROGRESS);
                roomEntity.clearRoundInfo();
                roomEntity.nullifyButtonClicks();
            } else if (roomEntity.haveAllPlayersClicked() &&
                    roomEntity.getGameStage().equals(GameStage.ROUND_RESULTS)) {
                // все нажали на кнопку голосовать ---> voting
                roomEntity.setGameStage(GameStage.VOTING);
                roomEntity.nullifyButtonClicks();
            }
        } else {
            // кол-во голосов всего = кол-во игроков и не было разделения голосов ---> voting_results
            if (roomEntity.haveAllPlayersVoted() &&
            !roomEntity.haveVotesSeparated()) {
                roomEntity.setGameStage(GameStage.VOTING_RESULTS);
            }
        }
    }

    // cases:
    // case 1) первая игра --> считаем клики, переводим в сост ROUND_RESULTS
    // case 2) ROUND_RESULTS --> считаем клики, переводим в VOTING
    // case 3) VOTING --> считаем воты, переводим в VOTING_RESULTS
    // case 4) VOTING_RESULTS --> считаем клики, очищаем раунд, переводим в ROUND_RESULTS
    // case 5) VOTING_RESULTS и конец игры --> очищаем раунд (потом игру) и выходим в лобби
    // case 6) VOTING_RESULTS и игры закончилась --> очищаем предыд игру и запусаем новую
}
