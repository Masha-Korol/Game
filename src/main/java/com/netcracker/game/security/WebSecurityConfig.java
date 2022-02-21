package com.netcracker.game.security;

import com.netcracker.game.security.jwt.AuthEntryPointJwt;
import com.netcracker.game.security.jwt.AuthTokenFilter;
import com.netcracker.game.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Provides {@link HttpSecurity} configurations to configure
 * cors, csrf, session management, rules for protected resources.
 * <p>
 * We can also extend and customize the default configuration that contains the elements like
 * {@link org.springframework.security.core.userdetails.UserDetailsService}, {@link AuthenticationManager}, filters and etc.
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  /**
   * Used by the default implementation of authenticationManager()
   * to attempt to obtain an AuthenticationManager.
   *
   * @param authenticationManagerBuilder the AuthenticationManagerBuilder to use
   * @throws Exception
   */
  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(9);
  }

  /**
   * Configures the {@link HttpSecurity}.
   * <p>
   * Any endpoint that requires defense against common vulnerabilities can be specified here, including public ones.
   * </p>
   *
   * @param http the {@link HttpSecurity} to modify
   * @throws Exception
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
            .cors().and()
            .csrf().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .authorizeRequests().antMatchers("/auth/**").permitAll().and()
            .authorizeRequests().antMatchers("/testchat/**").permitAll().and()
            .authorizeRequests().antMatchers(HttpMethod.GET, "/users/").hasRole("ADMIN")
            .anyRequest().authenticated();
    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }
}
