package com.netcracker.game.controller;

import com.netcracker.game.data.model.CoordinatesStructure;
import com.netcracker.game.data.model.Message;
import com.netcracker.game.data.model.PlayerDeathInfo;
import com.netcracker.game.data.model.TerminalInfo;
import com.netcracker.game.services.dto.ButtonClickEvent;
import com.netcracker.game.services.dto.voting.VotingRequest;
import com.netcracker.game.services.dto.voting.VotingResultResponse;
import com.netcracker.game.services.service.IGameService;
import com.netcracker.game.services.service.IMessageService;
import com.netcracker.game.services.service.impl.GameServiceImpl;
import com.netcracker.game.services.service.impl.MessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
//@RequestMapping(path = "/api/v1/messages")
@CrossOrigin
public class SocketController {

    private final IMessageService messageService;
    private final IGameService gameService;
    private final static Logger logger = LoggerFactory.getLogger(SocketController.class);


    public SocketController(MessageServiceImpl messageService, GameServiceImpl gameService) {
        this.messageService = messageService;
        this.gameService = gameService;
    }

    @MessageMapping("/send")
    @SendTo("/receive/changes")
    public String send(Message message) {
        messageService.saveMessage(message.getRoomId(), message);
        return message.getText();
    }

    @PostMapping("/all/{roomId}")
    public List<Message> getAll(@PathVariable("roomId") Integer roomId,
                                @RequestBody String lastMessageDate){
        return messageService.getAllByRoom(roomId, lastMessageDate);
    }

    @MessageMapping("/round/send")
    @SendTo("/receive/round/changes")
    public String sendRoundMessage(Message message){
        messageService.saveRoundMessage(message.getRoomId(), message);
        return message.getText();
    }

    @PostMapping("/all/round/{roomId}")
    public List<Message> getAllRoundMessages(@PathVariable("roomId") Integer roomId,
                                @RequestBody String lastMessageDate){
        return messageService.getAllRoundMessagesByRoom(roomId, lastMessageDate);
    }

    @MessageMapping("/click")
    @SendTo("/receive/click/changes")
    public String clickButton(ButtonClickEvent buttonClickEvent){
        gameService.clickButton(buttonClickEvent);
        return "";
    }

    @MessageMapping("/vote")
    @SendTo("/receive/vote/changes")
    public String vote(VotingRequest votingRequest){
        gameService.vote(votingRequest);
        return "";
    }

    @MessageMapping("/move")
    @SendTo("/receive/position/changes")
    public String move(CoordinatesStructure structure){
        gameService.updateRoundMapCoordinates(structure);
        return "";
    }


    @MessageMapping("/add_iterminal")
    @SendTo("/receive/terminals/changes")
    public String invalidateTerminal(TerminalInfo termInfo) {
        gameService.invalidateTerminal(termInfo);
        return "";
    }

    @MessageMapping("/kill_player")
    @SendTo("/receive/kills/changes")
    public String playerDeathEndpoint(PlayerDeathInfo deathInfo) {
        gameService.killPlayer(deathInfo);
        return "";
    }

    @MessageMapping("/notify_votes_separation")
    @SendTo("/receive/votes/separation")
    public VotingResultResponse notifyVotesSeparation(VotingResultResponse votingResultResponse) {
        return votingResultResponse;
    }
}
