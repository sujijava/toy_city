package ca.sheridancollege.controller;


import java.util.ArrayList;

import javax.annotation.security.RolesAllowed;
import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.beans.Toy;
import ca.sheridancollege.database.DatabaseAccess;
import ca.sheridancollege.mail.EmailServiceImpl;


@Controller
public class HomeController {

	@Autowired
	@Lazy
	private DatabaseAccess da; 
		
	@Autowired
	@Lazy
	EmailServiceImpl esi;
	
	@GetMapping("/")
	public String goHome() {
		return "home.html"; 
	}
	
	@GetMapping("/access-denied")
	public String goAccessDenied() {
		return "/error/access-denied.html"; 
	}
	
	@GetMapping("/worker/boss/add")
	public String createUser(Model model) {
				
			model.addAttribute("toy", new Toy());
			return "/worker/boss/add.html"; 
	}
	
	@GetMapping("/worker/boss/save")
	public String saveToy(Model model, @ModelAttribute Toy toy) {
			model.addAttribute("toy", new Toy()); //added
			da.addToy(toy);			
			return "redirect:/worker/boss/add"; 		
	}	
	
	@GetMapping("/login")
	public String goLoginPage() {
		return "login.html";
	}
			
	@GetMapping("/view")
	public String viewStudent(Model model, Authentication authentication) {
		model.addAttribute("toys", da.getToys()); 				
		return "view.html";
	}
		
	@GetMapping("worker/boss/delete/{id}")
	public String deleteStudent(@PathVariable int id, Model model) {

		da.deleteToy(id);
		return "redirect:/view";
	}

	@GetMapping("worker/boss/edit/{id}")
	public String editContact(@PathVariable int id, Model model) {
		
		Toy toy = da.getToyById(id);
		model.addAttribute("toy", toy);
		return "worker/boss/modify.html";
		
	}
	
	@GetMapping("worker/boss/modify")
	public String editContact(Model model, @ModelAttribute Toy toy) {
		da.editToy(toy);
		System.out.println("This is the contact from controller" + toy.getId());
		model.addAttribute("toys", da.getToys());
		return "redirect:/view";
	}
	
	@GetMapping("/register")
	public String goRegistration() {
		return "registration.html";
	}

	@PostMapping("/register")
	public String processRegistration(@RequestParam String name, @RequestParam String password,
			@RequestParam String role) {
		
		da.createNewUser(name, password);
		long userId = da.findUserAccount(name).getUserId();

		System.out.println(role);
		
		if (role.contains("boss")) {
			da.addRole(userId, 1);
		}

		if (role.contains("worker")) {
			da.addRole(userId, 11);
		}

		return "redirect:/";
	}
	
	@GetMapping("/worker/search")
	public String goSearchHome() {
		return "worker/search.html";
	}	
	
	@GetMapping("/worker/search/name")
	public String serachName(Model model, @RequestParam String name) {
		model.addAttribute("toys", da.getToyByName(name));		
		return "worker/search_r.html";
	}
	
	@GetMapping("/worker/search/qty")
	public String serachQty(Model model, @RequestParam int qty_min, @RequestParam int qty_max) {
		model.addAttribute("toys", da.getToyByQty(qty_min, qty_max));		
		return "worker/search_r.html";
	}
	
	@GetMapping("/worker/search/price")
	public String serachPrice(Model model, @RequestParam int price_min, @RequestParam int price_max) {
		model.addAttribute("toys", da.getToyByPrice(price_min, price_max));		
		return "worker/search_r.html";
	}
	
	@GetMapping("/worker/email")
	public String goEmail(Model model) {
	return "worker/email.html"; 
	}
	
	@GetMapping("/worker/email/send")
	public String sendEmail(Model model) {
		
		model.addAttribute("toys", da.getToys());
				
		try {
			esi.sendMailWithThymeleaf("Unknown User", 
					"We are sending inventory detail as your request", 
					"Toy Inc.", 
					"prog32758.suji@gmail.com",  //prog32758.suji@gmail.com //prog32758.summer2020@gmail.com 
					"Inventory Detail",
					da.getToys());
		} catch (MessagingException e) {
			
			System.out.println(e);
		}
		return "redirect:/worker/email"; 
	}
	
	

	
	
	
	
	
	
}
