package org.xcompany.xprojects.standings_2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.xcompany.xprojects.standings_2.entity.Game;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    List<Game> getByTournamentIdOrderByCreateDateDesc(Long tournamentId);
}
