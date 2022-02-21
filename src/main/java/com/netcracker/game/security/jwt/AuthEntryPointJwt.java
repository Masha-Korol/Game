package com.netcracker.game.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements {@link AuthenticationEntryPoint}, which allows us
 * to set custom response on auth exceptions.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

  /**
   * This method will be triggered anytime unauthenticated {@link com.netcracker.game.data.model.GameUser} requests
   * a secured HTTP resource and an {@link AuthenticationException} is thrown.
   *
   * @param request http servlet request
   * @param response http servlet response
   * @param authException latest authentication exception
   * @throws IOException io exception
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException {
    logger.error("Unauthorized error: {}", authException.toString());
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
  }
}
