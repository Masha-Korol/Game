package com.netcracker.game.services.service.impl;

import com.netcracker.game.data.model.GameUser;
import com.netcracker.game.data.repository.GameUserRepository;
import com.netcracker.game.payloads.requests.SignupRequest;
import com.netcracker.game.services.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private final GameUserRepository gameUserRepository;

    public UserServiceImpl(GameUserRepository gameUserRepository) {
        this.gameUserRepository = gameUserRepository;
    }

    @Override
    public void addUser(SignupRequest signupRequest) {
        GameUser gameUser = new GameUser();
        gameUser.setLogin(signupRequest.getUsername());
        gameUser.setPassword(signupRequest.getPassword());
        gameUser.setEmail(signupRequest.getEmail());
        gameUserRepository.save(gameUser);
    }
}
