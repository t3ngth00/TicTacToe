package com.hangnguyen.tictactoe.model;

import java.util.Map;

public class GameStatusResponse {
  private Map<Integer, String> cells;
  private String status;

  
  public GameStatusResponse(Map<Integer, String> cells, String status) {
    this.cells = cells;
    this.status = status;
  }
  public Map<Integer, String> getCells() {
    return cells;
  }
  public void setCells(Map<Integer, String> cells) {
    this.cells = cells;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
}
