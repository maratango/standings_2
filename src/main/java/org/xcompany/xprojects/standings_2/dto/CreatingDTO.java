package org.xcompany.xprojects.standings_2.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatingDTO {
    private Long userId;
    private String tourName;
    private Long tournamentId;
    private String gameData;
    private String creatingResult;
}
