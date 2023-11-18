package com.hangnguyen.tictactoe;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hangnguyen.tictactoe.model.GameStatusResponse;
import com.hangnguyen.tictactoe.model.UpdateCellRequest;
import com.hangnguyen.tictactoe.model.User;

public interface GameEndpoint {
  @PostMapping("/game/login")
  public ResponseEntity<String> login(@RequestBody User loginRequest);

  @PostMapping("/game/userId")
  public GameStatusResponse updateCell(@RequestBody UpdateCellRequest request);

  @GetMapping("/game/new")
  public GameStatusResponse newGame();
}