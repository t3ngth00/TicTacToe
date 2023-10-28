package com.hangnguyen.tictactoe.model;

public class UpdateCellRequest {
  private String player;
  private int cell;

  public UpdateCellRequest(String player, int cell){
    this.player = player;
    this.cell = cell;
  }

  public String getPlayer() {
    return player;
  }

  public void setPlayer(String player) {
    this.player = player;
  }

  public int getCell() {
    return cell;
  }

  public void setCell(int cell) {
    this.cell = cell;
  }
}
