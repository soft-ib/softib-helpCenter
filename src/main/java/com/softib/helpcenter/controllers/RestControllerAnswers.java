package com.softib.helpcenter.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.softib.helpcenter.entities.Answer;
import com.softib.helpcenter.exception.SoftibException;
import com.softib.helpcenter.services.IAnswerService;
@RestController
public class RestControllerAnswers {
	@Autowired
	IAnswerService answerService;

	@GetMapping(value = "/answers/find/all")
 	public List<Answer> getAnswers() {
		return answerService.getAllAnswer();
	}
	@PostMapping(value = "/answers/{questionId}/add")
 	public Object addAnswer(@PathVariable int questionId, @RequestBody Answer answer) throws SoftibException {
		
  		return answerService.addAnswer(answer, questionId);
	}
	
	@PutMapping(value = "/answers/update")
 	public Object updateAnswer( @RequestBody Answer answer) throws SoftibException {
  		return answerService.updateAnswer(answer);
	}
	
	@DeleteMapping(value = "/answers/{answerId}/delete")
 	public void deleteAnswer( @PathVariable int answerId) {
  		 answerService.deleteAnswer(answerId);
	}
	
	@GetMapping(value = "/answers/{questionId}/find")
 	public Object getAnswersByQuestion(@PathVariable int questionId) throws SoftibException {
  		return answerService.findByQuestionId(questionId);
	}
	
	
}
