package com.netcracker.game.controller;

import com.netcracker.game.data.repository.GameUserRepository;
import com.netcracker.game.payloads.requests.LoginRequest;
import com.netcracker.game.payloads.requests.SignupRequest;
import com.netcracker.game.payloads.responses.JwtResponse;
import com.netcracker.game.payloads.responses.MessageResponse;
import com.netcracker.game.security.jwt.JwtUtils;
import com.netcracker.game.security.services.UserDetailsImpl;
import com.netcracker.game.services.dto.gameuser.GameUserDto;
import com.netcracker.game.services.service.IGameUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
  AuthenticationManager authenticationManager;

	@Autowired
	IGameUserService gameUserService;

	@Autowired
  PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	GameUserRepository userRepository;

	/**
	 * [link User]{@link com.netcracker.game.data.model.GameUser} authentication.
	 * Accepts {@link LoginRequest} form, parses it for authentication (login, password) details, returns
	 * jwt token for a provided user credentials (see {@link SecurityContextHolder} and {@link com.netcracker.game.security.WebSecurityConfig}).
	 *
	 * @param loginRequest login request form ({@link LoginRequest})
	 * @return http response with jwt token, username and email
	 */
	@PostMapping("/sign_in")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		JwtResponse response = new JwtResponse(jwt, userDetails.getUsername(), userDetails.getEmail(), roles);

		return ResponseEntity.ok(response);
	}

	/**
	 * [link User]{@link com.netcracker.game.data.model.GameUser} registration request.
	 *
	 * @param signUpRequest http request ({@link SignupRequest})
	 * @return http OK if user registered successfully
	 */
	@PostMapping("/sign_up")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		if (userRepository.existsByLogin(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use"));
		}

		GameUserDto userDto = new GameUserDto();
		userDto.setEmail(signUpRequest.getEmail());
		userDto.setLogin(signUpRequest.getUsername());
		userDto.setPassword(encoder.encode(signUpRequest.getPassword()));
		gameUserService.addUser(userDto);

		return ResponseEntity.ok(new MessageResponse("User registered successfully"));
	}
}
