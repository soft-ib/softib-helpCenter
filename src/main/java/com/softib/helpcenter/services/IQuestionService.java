package com.softib.helpcenter.services;

import java.util.List;

import com.softib.helpcenter.entities.Question;

public interface IQuestionService {

	public List<Question> getAllQuestion();

	public List<Question> getQuestionByUser();

	public Question updateQuestion(Question question);

	public Question addQuestion(Question question);

	public void deleteQuestion(Question question);

}
