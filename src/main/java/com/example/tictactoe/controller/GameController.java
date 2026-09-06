package com.example.tictactoe.controller;

import com.example.tictactoe.dto.GameResponseDTO;
import com.example.tictactoe.dto.MoveRequestDTO;
import com.example.tictactoe.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "http://localhost:3000")

public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<GameResponseDTO> createGame(@RequestParam(defaultValue = "3") int size) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.createGame(size));
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
}
