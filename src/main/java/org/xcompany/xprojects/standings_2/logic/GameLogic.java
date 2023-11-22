package org.xcompany.xprojects.standings_2.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xcompany.xprojects.standings_2.dto.CreatingDTO;
import org.xcompany.xprojects.standings_2.entity.Game;
import org.xcompany.xprojects.standings_2.service.GameService;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GameLogic {

    @Autowired
    GameService gameService;

    public CreatingDTO saveGame(CreatingDTO creatingDTO) {
        String gameRow = creatingDTO.getGameData();
        Long tournamentId = creatingDTO.getTournamentId();
        gameService.save(getGameFromStringData(gameRow, tournamentId));

        String creatingResultMessage = "матч \"" + gameRow + "\" добавлен";
        return CreatingDTO.builder().creatingResult(creatingResultMessage).build();
    }

    public CreatingDTO saveGamesFromFile(Long tournamentId, MultipartFile loadFile) throws IOException {
        byte[] bytes = loadFile.getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        BufferedInputStream bis = new BufferedInputStream(in);

        int c;
        String text = "";
        while((c=bis.read())!=-1){
            text = text + (char)c;
        }

        List<Game> games = new ArrayList<>();
        List<String> gameRows = Arrays.asList(text.split(";"));
        gameRows.forEach(gameRow -> {
            games.add(getGameFromStringData(gameRow, tournamentId));
        });

        gameService.saveAll(games);

        String creatingResultMessage = games.size() == 0
                ? "нет матчей для добавления"
                : "матчи из файла \"" + loadFile.getOriginalFilename() + "\" добавлены";

        return CreatingDTO.builder().creatingResult(creatingResultMessage).build();
    }

    public Game getGameFromStringData(String gameRow, Long tournamentId) {
        List<String> teamResults = Arrays.asList(gameRow.split(":"));
        String homeTeamResult = teamResults.get(0);
        String guestTeamResult = teamResults.get(1);

        List<String> homeTeamNameAndGoalCount = Arrays.asList(homeTeamResult.split(" "));
        String homeTeamGoalCount = homeTeamNameAndGoalCount.get(homeTeamNameAndGoalCount.size() - 1);
        String homeTeamName = "";
        for (int i = 0; i < homeTeamNameAndGoalCount.size() - 1; i++) {
            String a = i > 0 ? " " : "";
            homeTeamName = homeTeamName + a + homeTeamNameAndGoalCount.get(i);
        }
        homeTeamName = homeTeamName.replaceAll("\\r\\n", "");

        List<String> guestTeamNameAndGoalCount = Arrays.asList(guestTeamResult.split(" "));
        String guestTeamGoalCount = guestTeamNameAndGoalCount.get(0);
        String guestTeamName = "";
        for (int i = 1; i < guestTeamNameAndGoalCount.size(); i++) {
            String a = i > 1 ? " " : "";
            guestTeamName = guestTeamName + a + guestTeamNameAndGoalCount.get(i);
        }

        return Game.builder()
                .tournamentId(tournamentId)
                .homeTeam(homeTeamName)
                .homeGoal(Integer.valueOf(homeTeamGoalCount))
                .guestTeam(guestTeamName)
                .guestGoal(Integer.valueOf(guestTeamGoalCount))
                .createDate(Instant.now())
                .build();
    }

    public List<Game> getByTournamentIdOrderByCreateDateDesc(Long tournamentId) {
        return gameService.getByTournamentIdOrderByCreateDateDesc(tournamentId);
    }

    public void deleteById(Long gameId) {
        gameService.deleteById(gameId);
    }
}
