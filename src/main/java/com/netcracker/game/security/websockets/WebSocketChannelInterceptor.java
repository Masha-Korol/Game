package com.netcracker.game.security.websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class WebSocketChannelInterceptor implements ChannelInterceptor {

  private static final String AUTH_TOKEN_HEADER = "Authorization";
  private static final Logger logger = LoggerFactory.getLogger(WebSocketChannelInterceptor.class);

  @Inject
  WebSocketAuthenticatorService webSocketAuthenticatorService;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    String jwtToken = accessor.getFirstNativeHeader(AUTH_TOKEN_HEADER);
    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
        UsernamePasswordAuthenticationToken user = webSocketAuthenticatorService.getAuthenticatedOrFail(jwtToken);
        accessor.setUser(user);
    }
    return message;
  }
}
