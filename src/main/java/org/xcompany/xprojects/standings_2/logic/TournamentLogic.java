package org.xcompany.xprojects.standings_2.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xcompany.xprojects.standings_2.dto.CreatingDTO;
import org.xcompany.xprojects.standings_2.dto.TournamentResultRowDTO;
import org.xcompany.xprojects.standings_2.entity.Tournament;
import org.xcompany.xprojects.standings_2.service.TournamentService;

import java.util.List;

@Service
public class TournamentLogic {

    @Autowired
    TournamentService tournamentService;

    public List<Tournament> getByUserAppIdOrderByCreateDateDesc(Long userId) {
        return tournamentService.getByUserAppIdOrderByCreateDateDesc(userId);
    }

    public CreatingDTO createNewTournament(CreatingDTO creatingDTO) {
        try {
            boolean result = tournamentService.createNewTournament(creatingDTO.getUserId(), creatingDTO.getTourName());
            if (result) {
                creatingDTO.setCreatingResult("Турнир " + "\"" + creatingDTO.getTourName() + "\"" + " добавлен");
            } else {
                creatingDTO.setCreatingResult("Турнир " + "\"" + creatingDTO.getTourName() + "\"" + " уже существует");
            }
        } catch(Exception e) {
            creatingDTO.setCreatingResult("При добавлении турнира " + "\"" + creatingDTO.getTourName() + "\"" + " произошла системная ошибка");
        } finally {
            return creatingDTO;
        }
    }

    public void deleteById(Long id) {
        tournamentService.deleteById(id);
    }

    public List<TournamentResultRowDTO> getTournamentResult(Long tournamentId) {
        return tournamentService.getTournamentResult(tournamentId);
    }
}
