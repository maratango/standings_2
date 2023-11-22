package org.xcompany.xprojects.standings_2.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xcompany.xprojects.standings_2.dto.CreatingDTO;
import org.xcompany.xprojects.standings_2.dto.LoginInfoDTO;
import org.xcompany.xprojects.standings_2.service.UserAppService;

@Service
public class UserAppLogic {

    @Autowired
    private UserAppService userAppService;

    public Long getIdByLoginAndPassword(String login, String password) {
        return userAppService.getIdByLoginAndPassword(login, password);
    }

    public LoginInfoDTO createNewUser(LoginInfoDTO loginInfoDTO) {
        try {
            boolean result = userAppService.createNewUser(loginInfoDTO.getLogin(), loginInfoDTO.getPassword());
            if (result) {
                loginInfoDTO.setCreatingResult("Пользователь " + "\"" + loginInfoDTO.getLogin() + "\"" + " добавлен");
            } else {
                loginInfoDTO.setCreatingResult("Пользователь " + "\"" + loginInfoDTO.getLogin() + "\"" + " уже существует");
            }
        } catch(Exception e) {
            loginInfoDTO.setCreatingResult("При добавлении пользователя " + "\"" + loginInfoDTO.getLogin() + "\"" + " произошла системная ошибка");
        } finally {
            return loginInfoDTO;
        }
    }
}
