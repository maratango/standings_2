package org.xcompany.xprojects.standings_2.entity;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity(name = "user_app")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserApp {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "user_app_seq",
            sequenceName = "user_app_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_app_seq")
    @NotNull
    private Long id;

    @Column(name = "login", nullable = false)
    private String login;

    @NotNull
    private String password;

    private String email;

    private Instant createDate;
}
