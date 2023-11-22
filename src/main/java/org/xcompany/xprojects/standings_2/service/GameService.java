package org.xcompany.xprojects.standings_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xcompany.xprojects.standings_2.entity.Game;
import org.xcompany.xprojects.standings_2.repository.GameRepository;

import java.util.List;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    public void save(Game game) {
        gameRepository.save(game);
    }

    public void saveAll(List<Game> games) {
        gameRepository.saveAll(games);
    }

    public List<Game> getByTournamentIdOrderByCreateDateDesc(Long tournamentId) {
       return gameRepository.getByTournamentIdOrderByCreateDateDesc(tournamentId);
    }

    public void deleteById(Long gameId) {
        gameRepository.deleteById(gameId);
    }
}
