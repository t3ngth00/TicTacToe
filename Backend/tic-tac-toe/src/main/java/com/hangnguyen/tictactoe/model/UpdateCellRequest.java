package com.hangnguyen.tictactoe.model;

public class UpdateCellRequest {
  private int cell;

  public UpdateCellRequest(){

  }

  public UpdateCellRequest(int cell){
    this.cell = cell;
  }

  public int getCell() {
    return cell;
  }

  public void setCell(int cell) {
    this.cell = cell;
  }
}
