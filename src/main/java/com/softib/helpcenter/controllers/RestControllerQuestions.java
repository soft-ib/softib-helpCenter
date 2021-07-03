package com.softib.helpcenter.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.softib.helpcenter.entities.Question;
import com.softib.helpcenter.services.IQuestionService;
import com.softib.helpcenter.util.IUserService;

@RestController
public class RestControllerQuestions {

	@Autowired
	IUserService userService;
	@Autowired
	IQuestionService questionService;
	Logger logger = LoggerFactory.getLogger(RestControllerQuestions.class);
	@Autowired
	private EurekaClient eurekaClient;

	private String coreServiceId ="core-service";

	@GetMapping(value = "questions")
	@ResponseBody
	Object showTestQuestion() { 
		List<Question> questions = null;
		try {
			 questions = questionService.getAllQuestion();

		}
		catch (Throwable t){
			logger.error(t.getMessage());
		}
		 return questions;
	}
	@PostMapping(value = "/questions/add")
	@ResponseBody
	public Question register(@RequestBody Question question) {
		return questionService.addQuestion(question);
	}

	@GetMapping(value = "/from-core/users")
	@ResponseBody
	Object fromCore(@RequestHeader("Authorization") String token) {
		Application application = eurekaClient.getApplication(coreServiceId);
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/users";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
	}
}
