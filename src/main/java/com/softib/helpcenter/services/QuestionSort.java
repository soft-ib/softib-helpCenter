package com.softib.helpcenter.services;

import java.util.Comparator;

import com.softib.helpcenter.entities.Question;

public class QuestionSort implements Comparator<Question> {

	public int compare(Question a, Question b) {
		return b.getLikes() - a.getLikes();
	}
}
