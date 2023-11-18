package com.hangnguyen.tictactoe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.hangnguyen.tictactoe.model.GameStatus;
import com.hangnguyen.tictactoe.model.GameStatusResponse;
import com.hangnguyen.tictactoe.model.UpdateCellRequest;

import com.hangnguyen.tictactoe.service.BoardGameManagementService;

import com.hangnguyen.tictactoe.utils.BoardUtils;

@Service
public class BoardGameManagementService {
  static Map<Integer, String> cells = new HashMap<>();
  static List<Integer> unPlayedCell = BoardUtils.getInitialCells();
  static {
    cells.putAll(BoardUtils.getEmptyBoard());
  }

  public GameStatusResponse updateCell(UpdateCellRequest request) {
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

  public GameStatusResponse newGame() {
    this.resetBoard();
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

  public void resetBoard() {
    cells.putAll(BoardUtils.getEmptyBoard());
    unPlayedCell = BoardUtils.getInitialCells();
  }

}