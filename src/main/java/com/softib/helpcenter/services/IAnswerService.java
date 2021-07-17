package com.softib.helpcenter.services;

import java.util.List;
import java.util.Optional;

import com.softib.helpcenter.entities.Answer;
import com.softib.helpcenter.exception.SoftibException;

public interface IAnswerService {

	public List<Answer> getAllAnswer();

	public List<Answer> findByUser(String username);
	public List<Answer> findByQuestionId(int questionId);

	public Answer updateAnswer(Answer Answer) throws SoftibException;

	public Optional<Answer> findById(Integer id);

	public Answer addAnswer(Answer Answer, int questionId) throws SoftibException;
	
	public void deleteAnswer(int AnswerId);

}
