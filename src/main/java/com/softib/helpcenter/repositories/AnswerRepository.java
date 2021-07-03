package com.softib.helpcenter.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softib.helpcenter.entities.Answer;

 
@Repository
public interface AnswerRepository extends CrudRepository<Answer, Integer> {

 }
