package com.exam.portal.Controller;

import com.exam.portal.OrganiserDetails;
import com.exam.portal.Repository.ExamRepository;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.exam.portal.Model.Organiser;
import com.exam.portal.Repository.OrganiserRepository;


@Controller
public class OrganiserController {

	@Autowired
	OrganiserRepository repo;

	@Autowired
	ExamRepository examRepository;

	public static String LOGIN_ROUTE="redirect:/organiser/login";
	
	

	@GetMapping("organiser/register")
	public String register(Model model) {
		model.addAttribute("organiser",new Organiser());
		return "organiser/register";
	}
	
	@PostMapping("organiser/register")
	public String registerPost(Organiser org) {
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		String encodedPassword=encoder.encode(org.getPassword());
		org.setPassword(encodedPassword);
		repo.save(org);
		return "redirect:/organiser/dashboard";
	}
	
	@GetMapping("organiser/login")
	public String login(Model m) {
		Object user= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(user instanceof OrganiserDetails){
			return "redirect:/organiser/dashboard";
		}
		return "organiser/login";
	}
	
	@GetMapping("organiser/dashboard")
	public String dashboard(Model model,Principal principal ) {
		int examcount=0;
		Object user=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String cuser=principal.getName();
		Organiser userobj=this.repo.findByEmail(cuser);
		model.addAttribute("user", userobj);
		if(user instanceof OrganiserDetails){
			Organiser org = ((OrganiserDetails) user).getOrg();
			examcount = examRepository.findByOrganiserId(org.getId()).size();
		}
		model.addAttribute("examcount",examcount);
		return "organiser/dashboard";
	}
}
