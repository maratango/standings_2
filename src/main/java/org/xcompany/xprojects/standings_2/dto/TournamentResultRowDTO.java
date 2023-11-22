package org.xcompany.xprojects.standings_2.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TournamentResultRowDTO {
    private int place;
    private String teamName;
    private int points;
    private int gameCount;
    private int winCount;
    private int drawCount;
    private int lossCount;
    private int goalsScoredCount;
    private int goalsСoncededCount;
    private int goalsDifferenceBetween;
}
