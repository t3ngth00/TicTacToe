package com.hangnguyen.tictactoe;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hangnguyen.tictactoe.model.UpdateCellRequest;
@CrossOrigin(origins = "*")
@RestController

public class Controller {
  private String currentPlayer = "X";
  static Map<Integer, String> cells = new HashMap<>();
  static {
    cells.put(1, null);
    cells.put(2, null);
    cells.put(3, null);
    cells.put(4, null);
    cells.put(5, null);
    cells.put(6, null);
    cells.put(7, null);
    cells.put(8, null);
    cells.put(9, null);

  }

  @PostMapping("/game/userId")
  public Map<Integer, String> updateCell(@RequestBody UpdateCellRequest request) {
    if (cells.get(request.getCell()) != null) {
      throw new IllegalArgumentException("Cell has been chosen");
    }
    if (!currentPlayer.equals(request.getPlayer()))
    {
      throw new IllegalArgumentException("It's not your turn");
    }
    if (currentPlayer.equals("X")) {
      currentPlayer = "O";
    } else {
      currentPlayer = "X";
    }
    cells.put(request.getCell(), request.getPlayer());
    return cells;
  }
}
