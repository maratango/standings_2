package org.xcompany.xprojects.standings_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xcompany.xprojects.standings_2.dto.TournamentResultRowDTO;
import org.xcompany.xprojects.standings_2.entity.Tournament;
import org.xcompany.xprojects.standings_2.repository.TournamentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TournamentService {

    @Autowired
    TournamentRepository tournamentRepository;

    public void save(Tournament tournament) {
        tournamentRepository.save(tournament);
    }

    public List<Tournament> getByUserAppIdOrderByCreateDateDesc(Long userId) {
        return tournamentRepository.getByUserAppIdOrderByCreateDateDesc(userId);
    }

    public boolean createNewTournament(Long userId, String tourName) {
        return tournamentRepository.createNewTournament(userId, tourName);
    }

    public void deleteById(Long id) {
        tournamentRepository.deleteById(id);
    }

    public List<TournamentResultRowDTO> getTournamentResult(Long tournamentId) {
        List<TournamentResultRowDTO> result = tournamentRepository.getTournamentResult(tournamentId).stream().map(proj ->
                TournamentResultRowDTO.builder()
                        .teamName(proj.getTeamName())
                        .points(proj.getPoints())
                        .gameCount(proj.getGameCount())
                        .winCount(proj.getWinCount())
                        .drawCount(proj.getDrawCount())
                        .lossCount(proj.getLossCount())
                        .goalsScoredCount(proj.getGoalsScoredCount())
                        .goalsСoncededCount(proj.getGoalsСoncededCount())
                        .goalsDifferenceBetween(proj.getGoalsDifferenceBetween())
                        .build()
        ).collect(Collectors.toList());
        for (int i = 0; i < result.size(); i++) {
            result.get(i).setPlace(i + 1);
        }
        return result;
    }
}
