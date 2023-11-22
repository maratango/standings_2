package org.xcompany.xprojects.standings_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xcompany.xprojects.standings_2.dto.CreatingDTO;
import org.xcompany.xprojects.standings_2.dto.TournamentResultRowDTO;
import org.xcompany.xprojects.standings_2.entity.Tournament;
import org.xcompany.xprojects.standings_2.logic.TournamentLogic;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class TournamentController {

    @Autowired
    TournamentLogic tournamentLogic;

    @GetMapping("/tournaments/{id}")
    public List<Tournament> getUserSortedTournaments(@PathVariable("id") @NotNull Long userId) {
        List<Tournament> result = tournamentLogic.getByUserAppIdOrderByCreateDateDesc(userId);
        return result;
    }

    @PostMapping("/tournament/add")
    public CreatingDTO createTournament(@RequestBody CreatingDTO creatingDTO) {
        CreatingDTO result = tournamentLogic.createNewTournament(creatingDTO);
        return result;
    }

    @DeleteMapping("/tournament/{id}")
    public void deleteTournament(@PathVariable("id") @NotNull Long tourId) {
        tournamentLogic.deleteById(tourId);
    }

    @GetMapping("/tournament/{id}")
    public List<TournamentResultRowDTO> getTournamentResult(@PathVariable("id") @NotNull Long tournamentId) {
        List<TournamentResultRowDTO> result = tournamentLogic.getTournamentResult(tournamentId);
        return result;
    }
}
