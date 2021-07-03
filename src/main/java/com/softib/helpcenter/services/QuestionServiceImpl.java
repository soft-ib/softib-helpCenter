package com.softib.helpcenter.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softib.helpcenter.entities.Question;
import com.softib.helpcenter.repositories.QuestionRepository;

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
	public List<Question> getQuestionByUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Question updateQuestion(Question question) {
		return questionRepository.save(question);
	}

	@Override
	public Question addQuestion(Question question) {
		return questionRepository.save(question);
	}

	@Override
	public void deleteQuestion(Question question) {
		 questionRepository.delete(question);
	}

}
