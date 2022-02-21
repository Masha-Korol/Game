package com.netcracker.game.services.service;

import com.netcracker.game.payloads.requests.SignupRequest;

public interface IUserService {

    void addUser(SignupRequest signupRequest);
}
