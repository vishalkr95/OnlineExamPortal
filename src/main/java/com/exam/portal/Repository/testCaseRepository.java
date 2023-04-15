package com.exam.portal.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.portal.Model.testCase;


@Repository
public interface testCaseRepository extends JpaRepository<testCase, Long>{

}
