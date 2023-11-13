package com.hangnguyen.tictactoe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hangnguyen.tictactoe.model.GameStatus;
import com.hangnguyen.tictactoe.model.GameStatusResponse;
import com.hangnguyen.tictactoe.model.UpdateCellRequest;
import com.hangnguyen.tictactoe.utils.BoardUtils;

@CrossOrigin(origins = "*")
@RestController
public class Controller {

  static Map<Integer, String> cells = new HashMap<>();
  static List<Integer> unPlayedCell = BoardUtils.getInitialCells();
  static {
   cells.putAll(BoardUtils.getEmptyBoard());
  }

  @PostMapping("/game/userId")
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

  @GetMapping("/game/new")
  public GameStatusResponse newGame() {
    cells.putAll(BoardUtils.getEmptyBoard());
    unPlayedCell = BoardUtils.getInitialCells();

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
}
