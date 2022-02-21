package com.netcracker.game.security.websockets;

import com.netcracker.game.security.jwt.JwtUtils;
import com.netcracker.game.security.services.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class WebSocketAuthenticatorService {

  @Inject
  JwtUtils jwtUtils;

  @Inject
  UserDetailsServiceImpl userDetailsService;

  public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String jwtToken) throws AuthenticationException {
    if (jwtToken == null) {
      throw new AuthenticationCredentialsNotFoundException("JWT token is empty");
    }
    if (!jwtUtils.validateJwtToken(jwtToken)) {
      throw new AuthenticationCredentialsNotFoundException("Provided token is not a valid JWT token");
    }
    String username = jwtUtils.getUserNameFromJwtToken(jwtToken);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
  }
}
