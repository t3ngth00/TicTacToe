package com.hangnguyen.tictactoe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hangnguyen.tictactoe.model.GameStatus;
import com.hangnguyen.tictactoe.model.GameStatusResponse;
import com.hangnguyen.tictactoe.model.UpdateCellRequest;
import com.hangnguyen.tictactoe.model.User;
import com.hangnguyen.tictactoe.repository.entity.Player;
import com.hangnguyen.tictactoe.service.UserService;
import com.hangnguyen.tictactoe.utils.BoardUtils;

@CrossOrigin(origins = "*")
@RestController
public class GameController implements GameEndpoint {

  private final UserService userService;

  public GameController(UserService userService) {
    this.userService = userService;
  }

  static Map<Integer, String> cells = new HashMap<>();
  static List<Integer> unPlayedCell = BoardUtils.getInitialCells();
  static {
    cells.putAll(BoardUtils.getEmptyBoard());
  }

  @Override
  public ResponseEntity<String> login(@RequestBody User loginRequest) {
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setContentType(MediaType.APPLICATION_JSON);

    // if user not exist yet, save the user to db and return 200 OK.
    // if the username is already exist, return 200 OK
    Player player = userService.findOrCreateUser(loginRequest);
    resetBoard();

    return new ResponseEntity<>("Success", responseHeaders, 200);
  }

  @Override
  public GameStatusResponse updateCell(@RequestBody UpdateCellRequest request) {
    if (cells.get(request.getCell()) != null) {
      throw new IllegalArgumentException("Cell has been chosen");
    }

    markPlayedCell(request.getCell(), GameStatus.X.name());

    String status = getGameStatus();

    if (!status.equalsIgnoreCase(GameStatus.TIE.name())) {
      return new GameStatusResponse(cells, status);
    } else if (unPlayedCell.size() > 1) {
      autoPlay();

      if (!getGameStatus().equalsIgnoreCase(GameStatus.TIE.name())) {
        return new GameStatusResponse(cells, GameStatus.O.name());
      }
    }
    return new GameStatusResponse(cells, status);
  }

  @Override
  public GameStatusResponse newGame() {
    resetBoard();

    return new GameStatusResponse(cells, GameStatus.TIE.name());
  }

  private void autoPlay() {
    int move = randomMove();

    if (cells.get(move) == null) {
      markPlayedCell(move, GameStatus.O.name());
    } else {
      autoPlay();
    }
  }

  private void markPlayedCell(int cell, String player) {
    cells.put(cell, player);
    unPlayedCell.remove(unPlayedCell.indexOf(cell));
  }

  private int randomMove() {
    int rnd = new Random().nextInt(unPlayedCell.size() - 1);
    return unPlayedCell.get(rnd);
  }

  private static String getGameStatus() {
    return BoardUtils.getWinner(cells);
  }

  private void resetBoard() {
    cells.putAll(BoardUtils.getEmptyBoard());
    unPlayedCell = BoardUtils.getInitialCells();
  }
}