package com.exam.portal.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "testcase")
public class testCase{
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

	@ManyToOne
    @JoinColumn( name= "codingQuestion_id", nullable=false)
    private codingQuestion codingquestions;
	
	private String testcase;
	
	private String answer;

	public Long getId() {
		return id;
	}

	public testCase(codingQuestion codingquestions, String testcase, String answer) {
		this.codingquestions = codingquestions;
		this.testcase = testcase;
		this.answer = answer;
	}

	public testCase() {
		
	}

	public void setId(Long id) {
		this.id = id;
	}

	public codingQuestion getCodingquestions() {
		return codingquestions;
	}

	public void setCodingquestions(codingQuestion codingquestions) {
		this.codingquestions = codingquestions;
	}

	public String getTestcase() {
		return testcase;
	}

	public void setTestcase(String testcase) {
		this.testcase = testcase;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
    
    


}
