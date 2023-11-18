package com.hangnguyen.tictactoe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hangnguyen.tictactoe.model.GameStatusResponse;
import com.hangnguyen.tictactoe.model.UpdateCellRequest;
import com.hangnguyen.tictactoe.model.User;
import com.hangnguyen.tictactoe.service.BoardGameManagementService;
import com.hangnguyen.tictactoe.service.UserService;

@CrossOrigin(origins = "*")
@RestController
public class GameController implements GameEndpoint {

  private final UserService userService;
  private final BoardGameManagementService boardService;

  public GameController(UserService userService, BoardGameManagementService boardService) {
    this.userService = userService;
    this.boardService = boardService;
  }

  @Override
  public ResponseEntity<String> login(@RequestBody User loginRequest) {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setContentType(MediaType.APPLICATION_JSON);

    userService.login(loginRequest);
    boardService.resetBoard();

    return new ResponseEntity<>("Success", responseHeaders, 200);
  }

  @Override
  public GameStatusResponse updateCell(@RequestBody UpdateCellRequest request) {
    return boardService.updateCell(request);
  }

  @Override
  public GameStatusResponse newGame() {
    return boardService.newGame();
  }
}