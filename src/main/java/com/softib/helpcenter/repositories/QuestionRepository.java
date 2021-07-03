package com.softib.helpcenter.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softib.helpcenter.entities.Question;

 
@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {

 }
