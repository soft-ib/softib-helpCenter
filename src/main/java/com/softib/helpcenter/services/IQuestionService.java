package com.softib.helpcenter.services;

import java.util.List;
import java.util.Optional;

import com.softib.helpcenter.entities.Question;
import com.softib.helpcenter.exception.SoftibException;

public interface IQuestionService {

	public List<Question> getAllQuestion();

	public List<Question> findByUser(String username);

 
	public Optional<Question> findById(Integer id);

	public Question addQuestion(Question question);
	
	public List<Question> advancedSearch(String summary, String content, boolean popular);


	public void deleteQuestion(int questionId) throws SoftibException;

	Question updateQuestion(Question question, String action);

}
