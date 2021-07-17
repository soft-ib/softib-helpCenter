package com.softib.helpcenter.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.aspectj.lang.SoftException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softib.helpcenter.entities.Answer;
import com.softib.helpcenter.entities.Question;
import com.softib.helpcenter.exception.SoftibException;
import com.softib.helpcenter.repositories.AnswerRepository;
import com.softib.helpcenter.util.HelpCenterUtils;
import com.softib.helpcenter.util.IUserService;

 
@Service
public class AnswerServiceImpl implements IAnswerService {

	@Autowired
	AnswerRepository answerRepository;
	@Autowired
	IQuestionService questionService;
	@Autowired
	IUserService userService;

	@Override
	public List<Answer> getAllAnswer() {
		List<Answer> result = new ArrayList<>();
		answerRepository.findAll().forEach(result::add);
		return result;
	}

	@Override
	public List<Answer> findByUser(String username) {
		List<Answer> result = new ArrayList<>();
	 	answerRepository.findAll().forEach(result::add);
		return result.stream().filter(q -> 
			q.getUsername().equals(username)
		).collect(Collectors.toList());
	}

	@Override
	public Answer updateAnswer(Answer answer) throws SoftibException {
		answer.setUpdateDate(new Date());
 		return answerRepository.save(answer);
	}

	@Override
	public Answer addAnswer(Answer answer, int questionId) throws SoftibException {
		Optional<Question> question = questionService.findById(questionId);
		if(question.isPresent()) {
			question.get().setNumberOfAnswers(question.get().getNumberOfAnswers()+1);
 			questionService.updateQuestion(question.get()," Answer");
			answer.setQuestion(question.get());
			String username = userService.getCurrentUserName();
			String content ="Answer added";
			HelpCenterUtils.pushNotification(username, content);

			return answerRepository.save(answer);
		}
		throw new SoftibException("Can't add an answer to no question");
	}

	@Override
	public void deleteAnswer(int answerId) {
		Optional<Answer> answer = answerRepository.findById(answerId);
		if(answer.isPresent()) {
			Question question = answer.get().getQuestion();
			if(question!=null) {
				question.setNumberOfAnswers(question.getNumberOfAnswers()-1);
	 			questionService.updateQuestion(question,"Removed answer");
			}
			 answerRepository.delete(answer.get());

		}
	}



	@Override
	public Optional<Answer> findById(Integer id) {
		return answerRepository.findById(id);
	}

	@Override
	public List<Answer> findByQuestionId(int questionId) {
		List<Answer> result = new ArrayList<>();
	 	answerRepository.findAll().forEach(result::add);
	 	return result.stream().filter(a -> 
	 		a.getQuestion() !=null).filter( a -> a.getQuestion().getQuestionId() == questionId).collect(Collectors.toList());
 	}

}
