package com.example.tictactoe.controller;

import com.example.tictactoe.dto.GameRequestDTO;
import com.example.tictactoe.dto.GameResponseDTO;
import com.example.tictactoe.dto.MoveRequestDTO;
import com.example.tictactoe.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "http://localhost:3000")

public class GameController {
    private final GameService gameService;


    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // RestController is Controller + Response Body, so we will return everything in form of JSON,
    // therefore return type is ResponseEntity<Entity>, which will convert Entity to JSON.

    @PostMapping
    public ResponseEntity<GameResponseDTO> createGame(@RequestBody GameRequestDTO gameRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.createGame(gameRequestDTO.playerId(), gameRequestDTO.size()));
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameResponseDTO> getGame(@PathVariable UUID gameId) {
        return ResponseEntity.ok(gameService.getGame(gameId));
    }

    @PostMapping("/{gameId}/moves")
    public ResponseEntity<GameResponseDTO> playMove(@PathVariable UUID gameId, @RequestBody MoveRequestDTO moveRequestDTO) {
        GameResponseDTO gameResponseDTO = gameService.playMove(gameId, moveRequestDTO.row(), moveRequestDTO.col(), moveRequestDTO.symbol());
        return ResponseEntity.ok(gameResponseDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<GameResponseDTO>> getAllGames(@PathVariable UUID userId) {
        List<GameResponseDTO> gameResponseDTOS = gameService.getAvailableGames(userId);
        return ResponseEntity.ok(gameResponseDTOS);
    }

    @PostMapping("/{gameId}/{userId}")
    public ResponseEntity<GameResponseDTO> joinGame(@PathVariable UUID gameId, @PathVariable UUID userId) {
        GameResponseDTO gameResponseDTO = gameService.joinGame(userId, gameId);
        return ResponseEntity.ok(gameResponseDTO);
    }

    @PostMapping("/{gameId}/setProgress")
    public ResponseEntity<GameResponseDTO> setStatusInProgress(@PathVariable UUID gameId) {
        GameResponseDTO gameResponseDTO = gameService.setStatusInProgress(gameId);
        return  ResponseEntity.ok(gameResponseDTO);
    }
}
