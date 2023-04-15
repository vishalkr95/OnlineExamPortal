package com.exam.portal.Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name ="codingquestion")
public class codingQuestion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false,length=300)
	private String statement;

	
	/*
	 * foreign key
	 * many question will be connected to one exam
	 * 
	 */
	@ManyToOne
	@JoinColumn(name= "exam_id", nullable=false)
	private Exam exams;


	@OneToMany(mappedBy="codingquestions")
	private List<testCase> testcases= new ArrayList<testCase>();


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getStatement() {
		return statement;
	}


	public void setStatement(String statement) {
		this.statement = statement;
	}


	public Exam getExams() {
		return exams;
	}


	public void setExams(Exam exams) {
		this.exams = exams;
	}


	public List<testCase> getTestCase() {
		return testcases;
	}


	public void setTestCase(List<testCase> testcases) {
		this.testcases = testcases;
	}

}
