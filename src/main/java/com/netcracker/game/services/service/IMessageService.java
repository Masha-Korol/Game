package com.netcracker.game.services.service;

import com.netcracker.game.data.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMessageService {
    void saveMessage(Integer roomId, Message message);
    List<Message> getAllByRoom(Integer roomId, String lastMessageDate);
    void saveRoundMessage(Integer roomId, Message message);
    List<Message> getAllRoundMessagesByRoom(Integer roomId, String lastMessageDate);
}
