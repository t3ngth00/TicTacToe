package com.hangnguyen.tictactoe.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.hangnguyen.tictactoe.model.GameStatus;

// Utilities for Board
public class BoardUtils {
  public static Map<Integer, String> getEmptyBoard() {
    Map<Integer, String> cells = new HashMap<>();

    cells.put(1, null);
    cells.put(2, null);
    cells.put(3, null);
    cells.put(4, null);
    cells.put(5, null);
    cells.put(6, null);
    cells.put(7, null);
    cells.put(8, null);
    cells.put(9, null);

    return cells;
  }

  public static List<Integer> getInitialCells (){
    return IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toList());
  }


  public static String getWinner(Map<Integer, String> cells) {
    // If X or O are 3 consecutive cells vertically, horizontally or diagonally, it
    // is a win
    // If there are no empty cells left and no one wins, it is a tie

    // row
    for (int i = 1; i <= 9; i += 3) {
      if (cells.get(i) != null && cells.get(i).equals(cells.get(i + 1)) && cells.get(i + 1).equals(cells.get(i + 2))) {
        System.out.println(cells.get(i) + "row");
        return cells.get(i);
      }
    }

    // colummn
    for (int i = 1; i <= 3; i++) {
      if (cells.get(i) != null && cells.get(i).equals(cells.get(i + 3)) && cells.get(i + 3).equals(cells.get(i + 6))) {
        return cells.get(i);
      }
    } 

    // diagonal
    if (cells.get(1) != null && cells.get(1).equals(cells.get(5)) && cells.get(5).equals(cells.get(9))) {
      return cells.get(1);
    }
    if (cells.get(3) != null && cells.get(3).equals(cells.get(5)) && cells.get(5).equals(cells.get(7))) {
      return cells.get(3);
    }

    return GameStatus.TIE.name();
  }
}
