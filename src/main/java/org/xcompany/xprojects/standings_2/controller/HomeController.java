package org.xcompany.xprojects.standings_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xcompany.xprojects.standings_2.dto.EntranceInfoDTO;
import org.xcompany.xprojects.standings_2.dto.LoginInfoDTO;
import org.xcompany.xprojects.standings_2.logic.UserAppLogic;

@Controller
public class HomeController {

	@Autowired
	private UserAppLogic userAppLogic;

	@GetMapping("/")
	public String home() {
		return "html/home";
	}

//	@PostMapping("/entrance")
//	@ResponseBody
//	public EntranceDTO entrance(@RequestBody EntranceDTO entranceDTO) {
//		return EntranceDTO.builder().login(entranceDTO.getLogin()).password(entranceDTO.getPassword()).build();
//	}

	@PostMapping("/entrance")
	@ResponseBody
	public EntranceInfoDTO entrance(@RequestBody LoginInfoDTO loginInfoDTO) {
		Long userId = userAppLogic.getIdByLoginAndPassword(loginInfoDTO.getLogin(), loginInfoDTO.getPassword());
		return EntranceInfoDTO.builder().userId(userId).build();
	}

	@GetMapping("/tourList")
	public String getTournaments() {
		return "/html/tourList";
	}

	@GetMapping("/tournament")
	public String getTournamentGames() {
		return "/html/tournament";
	}

	@GetMapping("/registration")
	public String registration() {
		return "/html/registration";
	}

	@PostMapping("/registrateUser")
	@ResponseBody
	public LoginInfoDTO registerUser(@RequestBody LoginInfoDTO loginInfoDTO) {
		LoginInfoDTO result = userAppLogic.createNewUser(loginInfoDTO);
		return result;
	}
}
