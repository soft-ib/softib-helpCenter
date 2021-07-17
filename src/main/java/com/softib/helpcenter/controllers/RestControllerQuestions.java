package com.softib.helpcenter.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.softib.helpcenter.entities.Question;
import com.softib.helpcenter.exception.SoftibException;
import com.softib.helpcenter.services.IQuestionService;
import com.softib.helpcenter.util.HelpCenterUtils;
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

	@GetMapping(value = "/questions/find/all")
	@ResponseBody
	public List<Question> getQuestions() {
		return questionService.getAllQuestion();
	}
	@PostMapping(value = "/questions/add")
	@ResponseBody
	public Object addQuestion(@RequestBody Question question, @RequestParam Boolean force) {
		List<Question> questions = questionService.getAllQuestion();
		if(questions.stream().anyMatch(q -> q.getSummary().equals(question.getSummary()))) {
			if(force.equals(true)) {
				String username = userService.getCurrentUserName();
				String content ="Question added";
				HelpCenterUtils.pushNotification(username, content);
				return questionService.addQuestion(question);
			}
			return "Similar questions exist please check them, "
					+ "questions : " + questions.stream().filter(q -> q.getSummary().equals(question.getSummary()))
							.collect(Collectors.toList()).toString();
		}
		return questionService.addQuestion(question);
	}
	
	@PutMapping(value = "/questions/update")
	@ResponseBody
	public Question updateQuestion(@RequestBody Question question) {
		String username = userService.getCurrentUserName();
		String content ="Question updated";
		HelpCenterUtils.pushNotification(username, content);

		return questionService.updateQuestion(question, null);
	}
	
	@DeleteMapping(value = "/questions/{id}/delete")
	@ResponseBody
	public void deleteQuestion(@RequestParam(required = true) int id) throws SoftibException {
		String username = userService.getCurrentUserName();
		String content ="Question deleted";
		HelpCenterUtils.pushNotification(username, content);

		 questionService.deleteQuestion(id);
	}
	
	
	@GetMapping(value = "/questions/find")
	@ResponseBody
	public List<Question> advancedQuestionSearch(@RequestParam(required = false) String summary,
			@RequestParam(required = false) String content, @RequestParam(required = false) Boolean popular) {
		return questionService.advancedSearch(summary, content, popular);
	}
	
	@GetMapping(value = "/questions/users/{username}/find")
	@ResponseBody
	public List<Question> findByUser(@PathVariable String username) {
		return questionService.findByUser(username);
	}
	

	@GetMapping(value = "/questions/{id}/like")
	@ResponseBody
	public Question likeQuestion(@PathVariable Integer id) throws SoftibException {
		Optional<Question> question = questionService.findById(id);
		if(question.isPresent()) {
			question.get().incrementLikes();
			String username = userService.getCurrentUserName();
			String content ="Question liked";
			HelpCenterUtils.pushNotification(username, content);

			return questionService.updateQuestion(question.get(), "like");
		}
		throw new SoftibException("Question with id "+ id+" is not found" );
	}

	@GetMapping(value = "/from-core/users")
	@ResponseBody
	public Object getUsers(@RequestHeader("Authorization") String token) {
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
