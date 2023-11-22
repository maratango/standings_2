package org.xcompany.xprojects.standings_2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.xcompany.xprojects.standings_2.dto.TournamentResultRowDTO;
import org.xcompany.xprojects.standings_2.dto.TournamentResultRowProjection;
import org.xcompany.xprojects.standings_2.entity.Tournament;

import java.util.List;

@Repository
public interface TournamentRepository extends CrudRepository<Tournament, Long> {

    List<Tournament> getByUserAppIdOrderByCreateDateDesc(Long userId);

    @Query(nativeQuery = true, value = "" +
            "select server.fn_checkandaddtournament(:userId, :tourName)"
    )
    boolean createNewTournament(@Param("userId") Long userId, @Param("tourName") String tourName);

    void deleteById(Long id);

    @Query(nativeQuery = true, value = "" +
            "select * from server.fn_turnStandings(:tournamentId)"
    )
    List<TournamentResultRowProjection> getTournamentResult(@Param("tournamentId") Long tournamentId);
}
