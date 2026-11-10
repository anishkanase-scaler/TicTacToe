package com.example.tictactoe.repository;

import com.example.tictactoe.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
    @Query("SELECT game FROM Game game WHERE (game.px != :id AND game.po IS NULL) OR (game.po != :id AND game.px is NULL)")
    public List<Game> getAllJoinableGames(@Param("id") UUID userId);
}
// findByPxNotAndPoNullOrPoNotAndPxNull: This means px != userId and po is null OR po != userId and px is null. Only these cases are valid.
// So when a user is trying to join a game, he cannot join a game he created and also one slot should be free.