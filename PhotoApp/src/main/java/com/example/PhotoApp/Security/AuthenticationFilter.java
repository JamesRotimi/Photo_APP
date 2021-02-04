package com.example.PhotoApp.Security;

import com.example.PhotoApp.DTO.UserDTO;
import com.example.PhotoApp.Model.UserLoginRequest;
import com.example.PhotoApp.Service.UserService;
import com.example.PhotoApp.SpringApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {


  private  final AuthenticationManager authenticationManager;

  public AuthenticationFilter(
      AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req,
      HttpServletResponse response) throws AuthenticationException {
    try {
      UserLoginRequest creds = new ObjectMapper()
          .readValue(req.getInputStream(), UserLoginRequest.class);

      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              creds.getEmail(),
              creds.getPassword(),
              new ArrayList<>())
      );

    } catch (IOException e) {
      throw new RuntimeException();
    }
  }


  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse response,
      FilterChain chain, Authentication auth) throws IOException, ServletException {

    String userName = ((User) auth.getPrincipal()).getUsername();

    String token = Jwts.builder()
        .setSubject(userName)
        .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
        .compact();
       UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
    UserDTO userDTO = userService.getUser(userName);

    response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    response.addHeader("UserID", userDTO.getUserId());


  }

}
