package org.xcompany.xprojects.standings_2.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginInfoDTO {
    private String login;
    private String password;
    private String creatingResult;
}
