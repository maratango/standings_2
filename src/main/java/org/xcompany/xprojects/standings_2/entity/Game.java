package org.xcompany.xprojects.standings_2.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "game_seq",
            sequenceName = "game_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_seq")
    @NotNull
    private Long id;

    @NotNull
    private Long tournamentId;

    @NotNull
    private String homeTeam;

    @Column(name = "home_goal", nullable = false)
    private Integer homeGoal;

    @NotNull
    private String guestTeam;

    @NotNull
    private Integer guestGoal;

    private Instant createDate;
}
