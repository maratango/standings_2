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
public class Tournament {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "tourn_seq",
            sequenceName = "tournament_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tourn_seq")
    @NotNull
    private Long id;

    @NotNull
    private Long userAppId;

    @NotNull
    private String name;

    private Instant createDate;
}
