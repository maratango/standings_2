package org.xcompany.xprojects.standings_2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xcompany.xprojects.standings_2.entity.UserApp;
import org.xcompany.xprojects.standings_2.repository.UserAppRepository;

@Service
public class UserAppService {

    @Autowired
    private UserAppRepository userAppRepository;

    public Long getIdByLoginAndPassword(String login, String password) {
        return userAppRepository.findByLoginAndPassword(login, password).map(UserApp::getId).orElse(0L);
    }

    public boolean createNewUser(String login, String password) {
        return userAppRepository.createNewUser(login, password);
    }
}
