package com.softib.helpcenter.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.softib.helpcenter.util.Constants;
import com.softib.helpcenter.util.IUserService;

@RestController
public class RestControllerQuestions {

	@Autowired
	IUserService userService;
	
	@GetMapping(value = "questions")
	@ResponseBody
	String showTestQuestion() {
		return "Hello, Help center is working !!!!"+" User and roles : " +userService.getCurrentUserName() + " " +userService.getCurrentUserRole();
	}
	
	@GetMapping(value = "/from-core/users")
	@ResponseBody
	Object fromCore(@RequestHeader("Authorization") String token) {
		RestTemplate restTemplate = new RestTemplate();
		final String uri = Constants.CORE_BASE_PATH + "/users";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		return restTemplate.exchange(uri, HttpMethod.GET, entity, Object.class);
	}
	
}
