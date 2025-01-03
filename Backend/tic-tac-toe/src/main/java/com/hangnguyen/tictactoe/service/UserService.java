package com.hangnguyen.tictactoe.service;

import org.springframework.stereotype.Service;

import com.hangnguyen.tictactoe.model.User;
import com.hangnguyen.tictactoe.repository.PlayerRepository;
import com.hangnguyen.tictactoe.repository.entity.Player;

@Service
public class UserService {
  private final PlayerRepository repository;

  public UserService(PlayerRepository repository) {
    this.repository = repository;
  }

  public Player login(User user) {
    return this.findOrCreateUser(user);
  }

  private Player findOrCreateUser(User user) {
    Player playerEntity = new Player();
    playerEntity.setName(user.getUserName());

    return repository.findByName(user.getUserName())
        .orElseGet(() -> repository.save(playerEntity));
  }

}
