package com.netcracker.game.security.services;

import com.netcracker.game.data.model.GameUser;
import com.netcracker.game.data.repository.GameUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used in {@link com.netcracker.game.security.WebSecurityConfig#configure(AuthenticationManagerBuilder)}.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    GameUserRepository gameUserRepository;

    /**
     * Returns {@link UserDetails} object for a provided [@link login]{@link GameUser#getLogin()}.
     *
     * @param username login/username of a {@link GameUser}
     * @return {@link UserDetails} object
     * @throws UsernameNotFoundException if users are not exists in a {@link GameUserRepository}
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GameUser user = gameUserRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

}
