package org.xcompany.xprojects.standings_2.dto;

public interface TournamentResultRowProjection {
    public String getTeamName();
    public int getPoints();
    public int getGameCount();
    public int getWinCount();
    public int getDrawCount();
    public int getLossCount();
    public int getGoalsScoredCount();
    public int getGoalsСoncededCount();
    public int getGoalsDifferenceBetween();
}
