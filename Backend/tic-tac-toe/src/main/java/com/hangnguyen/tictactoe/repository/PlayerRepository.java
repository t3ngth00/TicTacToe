package com.hangnguyen.tictactoe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hangnguyen.tictactoe.repository.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
  Optional<Player> findByName(String name); //return available or unavailable
}
