package com.netcracker.game.services.dto.voting;

import java.io.Serializable;
import java.util.Objects;
import java.util.*;

public class VotingResultResponse implements Serializable {
    private String login;
    private String playerRole;
    private Integer civiliansCount;
    private Integer traitorsCount;
    // эта переменная заполняется в случае, если голоса разделились
    private ArrayList<String> votesSeparationPlayers;

    public VotingResultResponse() {
    }

    public ArrayList<String> getVotesSeparationPlayers() {
        return votesSeparationPlayers;
    }

    public void setVotesSeparationPlayers(ArrayList<String> votesSeparationPlayers) {
        this.votesSeparationPlayers = votesSeparationPlayers;
    }

    public Integer getCiviliansCount() {
        return civiliansCount;
    }

    public void setCiviliansCount(Integer civiliansCount) {
        this.civiliansCount = civiliansCount;
    }

    public Integer getTraitorsCount() {
        return traitorsCount;
    }

    public void setTraitorsCount(Integer traitorsCount) {
        this.traitorsCount = traitorsCount;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(String playerRole) {
        this.playerRole = playerRole;
    }

    public VotingResultResponse(String login,
                                String playerRole,
                                Integer civiliansCount,
                                Integer traitorsCount) {
        this.login = login;
        this.playerRole = playerRole;
        this.civiliansCount = civiliansCount;
        this.traitorsCount = traitorsCount;
    }

    public VotingResultResponse(ArrayList<String> votesSeparationPlayers) {
        this.votesSeparationPlayers = votesSeparationPlayers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VotingResultResponse that = (VotingResultResponse) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(playerRole, that.playerRole) &&
                Objects.equals(civiliansCount, that.civiliansCount) &&
                Objects.equals(traitorsCount, that.traitorsCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, playerRole, civiliansCount, traitorsCount);
    }

    @Override
    public String toString() {
        return "VotingResultResponse{" +
                "login='" + login + '\'' +
                ", playerRole='" + playerRole + '\'' +
                ", civiliansCount=" + civiliansCount +
                ", traitorsCount=" + traitorsCount +
                ", votesSeparationPlayers=" + votesSeparationPlayers +
                '}';
    }
}
