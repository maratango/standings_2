package org.xcompany.xprojects.standings_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xcompany.xprojects.standings_2.dto.CreatingDTO;
import org.xcompany.xprojects.standings_2.entity.Game;
import org.xcompany.xprojects.standings_2.logic.GameLogic;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
public class GameController {

    @Autowired
    GameLogic gameLogic;

    @PostMapping("/game/add")
    public CreatingDTO addGame(@RequestBody CreatingDTO creatingDTO) {
        return gameLogic.saveGame(creatingDTO);
    }

    @PostMapping("/game/add-from-file/{id}")
    public CreatingDTO addGamesFromFile(
            @PathVariable("id") @NotNull Long tournamentId,
            @RequestParam("loadFile") MultipartFile loadFile) throws IOException {
        return gameLogic.saveGamesFromFile(tournamentId, loadFile);
    }

    @GetMapping("/games/{id}")
    public List<Game> getTournamentSortedGames(@PathVariable("id") @NotNull Long tournamentId) {
        List<Game> result = gameLogic.getByTournamentIdOrderByCreateDateDesc(tournamentId);
        return result;
    }

    @DeleteMapping("/game/{id}")
    public void deleteGame(@PathVariable("id") @NotNull Long gameId) {
        gameLogic.deleteById(gameId);
    }
}
