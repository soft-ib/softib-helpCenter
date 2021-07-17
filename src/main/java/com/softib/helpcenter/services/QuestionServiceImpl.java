package com.softib.helpcenter.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softib.helpcenter.entities.Question;
import com.softib.helpcenter.exception.SoftibException;
import com.softib.helpcenter.repositories.QuestionRepository;
import com.softib.helpcenter.util.HelpCenterUtils;

 
@Service
public class QuestionServiceImpl implements IQuestionService {

	@Autowired
	QuestionRepository questionRepository;

	@Override
	public List<Question> getAllQuestion() {
		List<Question> result = new ArrayList<>();
		questionRepository.findAll().forEach(result::add);
		return result;
	}

	@Override
	public List<Question> findByUser(String username) {
		List<Question> result = new ArrayList<>();
	 	questionRepository.findAll().forEach(result::add);
		return result.stream().filter(q -> 
			q.getUsername().equals(username)
		).collect(Collectors.toList());
	}

 	@Override
	public Question updateQuestion(Question question, String action) {
		question.setUpdateDate(new Date());
		if(action !=null ) {
			
			String username = question.getUsername();
			String content ="Question updated, new "+ action;
			HelpCenterUtils.pushNotification(username, content);

		}
		return questionRepository.save(question);
	}

	@Override
	public Question addQuestion(Question question) {
		return questionRepository.save(question);
	}

	@Override
	public void deleteQuestion(int questionId) throws SoftibException {
		Optional<Question> question = questionRepository.findById(questionId);
		if(question.isPresent()) {
			 questionRepository.delete(question.get());
		}
		else {
			throw new SoftibException("Question with id:" + questionId+" is not found");
		}
	}

	@Override
	public List<Question> advancedSearch(String summary, String content, boolean popular) {
		List<Question> result = new ArrayList<>();
		questionRepository.findAll().forEach(result::add);
		if (popular) {
			Collections.sort(result, new QuestionSort());
		}
		if (summary != null) {
			result = result.stream().filter(q -> HelpCenterUtils.compare(q.getSummary(), summary) > 0.75)
					.collect(Collectors.toList());
		}

		if (content != null) {
			result = result.stream().filter(q -> HelpCenterUtils.compare(q.getContent(), content) > 0.5)
					.collect(Collectors.toList());
		}
		return result;

	}

	@Override
	public Optional<Question> findById(Integer id) {
		return questionRepository.findById(id);
	}

}
