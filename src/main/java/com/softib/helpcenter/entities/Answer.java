package com.softib.helpcenter.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

 
@Entity
public class Answer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
 	private int answerId;
 	private String username;
  	private String content;

 	private Date creationDate;
	private Date updateDate;
	@Enumerated(EnumType.STRING)
	private AnswerStatus status;
	
	@JsonIgnore
	@ManyToOne
 	private Question question;

	public Answer(int answerId, String username, String content, 
			AnswerStatus status) {
		super();
		this.answerId = answerId;
		this.username = username;
		this.content = content;
		this.creationDate = new Date();
 		this.status = status;
 	}
	public Answer() {
		
	}
	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public AnswerStatus getStatus() {
		return status;
	}

	public void setStatus(AnswerStatus status) {
		this.status = status;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
