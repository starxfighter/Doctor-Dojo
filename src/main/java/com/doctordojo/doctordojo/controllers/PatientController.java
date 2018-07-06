package com.doctordojo.doctordojo.controllers;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.doctordojo.doctordojo.models.Billing;
import com.doctordojo.doctordojo.models.Patient;
import com.doctordojo.doctordojo.services.BillingService;
import com.doctordojo.doctordojo.services.LogAndRegService;
import com.doctordojo.doctordojo.services.PatientService;


@Controller
public class PatientController {
	
	private final PatientService patientService;
	private final LogAndRegService logandregService;
	private final BillingService billingService;
	
	public PatientController(BillingService billingService, PatientService patientService, LogAndRegService logandregService) {
		this.patientService = patientService;
		this.logandregService = logandregService;
		this.billingService = billingService;
	}
	

	
	@RequestMapping("/patient/new")
	public String newPat(Model model, @ModelAttribute Patient patient) {
		return "/doctordojo/NewPat.jsp";
	}
	
	@PostMapping("/patient/new")
	public String addPat(@Valid @ModelAttribute Patient patient, BindingResult result,@RequestParam("fDob") String fDob, Model model) {
		Random rnd = new Random();
		int n = 100000 + rnd.nextInt(900000);
		patient.setMpi(n);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		try {
			Date tempDate = format.parse(fDob);
			Date curDate = new Date();
			if(tempDate.after(curDate)) {
				model.addAttribute("error", "Date of birth can not be after current date");
				return "/doctordojo/NewPat.jsp";
			} else {
				patient.setDob(tempDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int testZip = patient.getZip();
		int length = (int) (Math.log10(testZip) + 1);
		if(length > 5 && length < 9) {
			model.addAttribute("error", "Zip code must be either 5 or 9 digits");
			return "/doctordojo/NewPat.jsp";
		}
		if(result.hasErrors()) {
			return "/doctordojo/NewPat.jsp";
		} else {
			patientService.createPatient(patient);
			List<Billing> tempBills = billingService.findOpenBills();
			model.addAttribute("bills", tempBills);
			return "redirect:/dashboard";
		}
	}
	
	@RequestMapping("/patient/search")
	public String search(@RequestParam(value="search") String search, Model model) {
		List<Patient> patient = patientService.searchPatient(search);
		model.addAttribute("patient", patient);
		model.addAttribute("search", search);
		return "/doctordojo/Search.jsp";
	}
	
	@RequestMapping("/patient/search2")
	public String search2(@RequestParam(value="search") String search, Model model, Principal principal) {
		List<Patient> patient = patientService.searchPatient(search);
		model.addAttribute("patient", patient);
		model.addAttribute("search", search);
		String username = principal.getName();
		model.addAttribute("currentUser", logandregService.findByUsername(username));
		List<Billing> tempBills = billingService.findOpenBills();
		model.addAttribute("bills", tempBills);
		return "/doctordojo/Dashboard.jsp";
	}
	
	
	
	@RequestMapping("/patient/update/{id}")
	public String editPatient(Model model, @PathVariable("id") Long id) {
		Patient patient = patientService.findPatient(id);
		DateFormat df = new SimpleDateFormat("dd/MM/yyy");
		String stringDate = df.format(patient.getDob());
		model.addAttribute("eDob", stringDate);
		model.addAttribute("patient", patient);
		return "/doctordojo/EditPat.jsp";
	}
	
	@RequestMapping(value="/patient/edit/{id}", method=RequestMethod.PUT)
	public String update(@Valid @ModelAttribute Patient patient, BindingResult result, @RequestParam("eDob") String eDob, Model model) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		try {
			System.out.println("in allergy try");
			Date tempDate = format.parse(eDob);
			Date curDate = new Date();
			if(tempDate.after(curDate)) {
				model.addAttribute("error", "Date of birth can not be after current date");
				return "/doctordojo/EditPat.jsp";
			} else {
				patient.setDob(tempDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int testZip = patient.getZip();
		int length = (int) (Math.log10(testZip) + 1);
		if(length > 5 && length < 9) {
			System.out.println("sending zip error");
			model.addAttribute("error", "Zip code must be either 5 or 9 digits");
			return "/doctordojo/EditPat.jsp";
		}
		if(result.hasErrors()) {
			return "/doctordojo/EditPat.jsp";
		} else {
			Patient tempPatient = patientService.updatePatient(patient);
			List<Billing> tempBills = billingService.findOpenBills();
			model.addAttribute("bills", tempBills);
			return "redirect:/dashboard";
		}
	}
	
//	@RequestMapping(value="/patient/delete/{id}", method=RequestMethod.DELETE)
//	public String destroy(@PathVariable("id") Long id) {
//		System.out.println("in pat delete controller");
//		patientService.deletePatient(id);
//		return "redirect:/dashboard";
//	}
}
