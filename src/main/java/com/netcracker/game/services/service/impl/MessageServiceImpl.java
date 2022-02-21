package com.netcracker.game.services.service.impl;

import com.netcracker.game.data.model.Message;
import com.netcracker.game.data.model.Room;
import com.netcracker.game.data.model.RoomEntity;
import com.netcracker.game.data.repository.RoomRedisRepository;
import com.netcracker.game.data.repository.RoomRepository;
import com.netcracker.game.services.service.IMessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements IMessageService {

    private final RoomRedisRepository roomRedisRepository;

    public MessageServiceImpl(RoomRedisRepository roomRedisRepository) {
        this.roomRedisRepository = roomRedisRepository;
    }

    @Override
    public void saveMessage(Integer roomId, Message message) {
        message.setMessageId();
        message.setDate(LocalDateTime.now());
        RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);
        if (roomEntity == null) {
            roomEntity = new RoomEntity();
            roomEntity.setRoomId(roomId);
        }
        roomEntity.addMessage(message);
        roomRedisRepository.save(roomEntity, roomId);
    }

    @Override
    public List<Message> getAllByRoom(Integer roomId, String lastMessageDate) {
        RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);
        List<Message> collect = new ArrayList<Message>();
        if (roomEntity == null) {
            return collect;
        }

        if (lastMessageDate.equals("null")) {
            collect = roomEntity.getChatMessages();
        } else {
            collect = roomEntity.getChatMessages().stream()
                    .filter(message -> message.getDate().compareTo(LocalDateTime.parse(lastMessageDate)) > 0)
                    .collect(Collectors.toList());
        }

        if (Objects.isNull(collect)) {
            return new ArrayList<>();
        }
        collect.sort(Comparator.comparing(Message::getDate));
        return collect;
    }

    @Override
    public void saveRoundMessage(Integer roomId, Message message) {
        message.setMessageId();
        message.setDate(LocalDateTime.now());
        RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);
        roomEntity.addRoundMessage(message);
        roomRedisRepository.save(roomEntity, roomId);
    }

    @Override
    public List<Message> getAllRoundMessagesByRoom(Integer roomId, String lastMessageDate) {
        RoomEntity roomEntity = roomRedisRepository.findByRoomId(roomId);
        List<Message> collect = new ArrayList<Message>();
        if (roomEntity == null) {
            return collect;
        }

        if (lastMessageDate.equals("null")) {
            collect = roomEntity.getRoundMessages();
        } else {
            collect = roomEntity.getRoundMessages().stream()
                    .filter(message -> message.getDate().compareTo(LocalDateTime.parse(lastMessageDate)) > 0)
                    .collect(Collectors.toList());
        }

        if(collect != null){
            collect.sort(Comparator.comparing(Message::getDate));
        }
        return collect;
    }
}


