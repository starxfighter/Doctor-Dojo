package com.doctordojo.doctordojo.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.doctordojo.doctordojo.models.Billing;
import com.doctordojo.doctordojo.models.User;
import com.doctordojo.doctordojo.services.BillingService;
import com.doctordojo.doctordojo.services.LogAndRegService;
import com.doctordojo.doctordojo.validator.LogAndRegValidator;

@Controller
public class LogInAndRegController {
	private LogAndRegService logandregService;
	private LogAndRegValidator logandregValidator;
	private BillingService billingService;
	
	public LogInAndRegController(BillingService billingService, LogAndRegService logandregService, LogAndRegValidator logandregValidator){
		this.logandregService = logandregService;
		this.logandregValidator = logandregValidator;
		this.billingService = billingService;
	}
	
	@RequestMapping(value = {"/", "/dashboard"})
	public String dashboard(Principal principal, Model model) {
		String username = principal.getName();
		model.addAttribute("currentUser", logandregService.findByUsername(username));
		List<Billing> tempBills = billingService.findOpenBills();
		model.addAttribute("bills", tempBills);
		return "/doctordojo/Dashboard.jsp";
	}
	
	@RequestMapping("/registration")
	public String registerForm(@Valid @ModelAttribute User user) {
		return "/doctordojo/registrationPage.jsp";
	}
	
	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute User user, BindingResult result, Model model, HttpSession session, @RequestParam("accesstype") String accesstype) {
		System.out.println("in reg post");
		logandregValidator.validate(user, result);
		if(result.hasErrors()) {
			return "/doctordojo/registrationPage.jsp";
		} else {
			if(accesstype.equals("Nurse")) {
				logandregService.saveWithNurseRole(user);
			}
			if(accesstype.equals("FrontOffice")) {
				logandregService.saveWithFrontOfficeRole(user);
			}
			if(accesstype.equals("Physician")) {
				logandregService.saveWithPhysicianRole(user);
			}
			if(accesstype.equals("Admin")) {
				System.out.println("in admin access set");
				logandregService.saveUserWithAdminRole(user);
			}
			return "redirect:/login";
		}
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model, HttpSession session) {
		if(error != null) {
			   model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
        	session.invalidate();
            model.addAttribute("logoutMessage", "Logout Successful!");
        }
		return "/doctordojo/loginPage.jsp";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {
		session.invalidate();
		model.addAttribute("logoutMessage", "Logout Successful!");
		return "redirect:/login";
	}

}
