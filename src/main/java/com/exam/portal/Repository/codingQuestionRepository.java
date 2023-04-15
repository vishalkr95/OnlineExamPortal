package com.exam.portal.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.portal.Model.codingQuestion;


@Repository
public interface codingQuestionRepository extends JpaRepository<codingQuestion, Long>{

}
