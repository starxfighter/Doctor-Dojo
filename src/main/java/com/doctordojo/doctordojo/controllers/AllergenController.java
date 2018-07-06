package com.doctordojo.doctordojo.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
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

import com.doctordojo.doctordojo.models.Allergy;
import com.doctordojo.doctordojo.models.Billing;
import com.doctordojo.doctordojo.models.Patient;
import com.doctordojo.doctordojo.models.Visit;
import com.doctordojo.doctordojo.services.AllergyService;
import com.doctordojo.doctordojo.services.BillingService;
import com.doctordojo.doctordojo.services.PatientService;
import com.doctordojo.doctordojo.services.VisitService;

import net.minidev.json.parser.ParseException;

@Controller
public class AllergenController {
	
	private final AllergyService allergyService;
	private final PatientService patientService;
	private final VisitService visitService;
	private final BillingService billingService;
	
	public AllergenController(AllergyService allergyService, PatientService patientService, VisitService visitService, BillingService billingService) {
		this.allergyService = allergyService;
		this.patientService = patientService;
		this.visitService = visitService;
		this.billingService = billingService;
	}
	
	@RequestMapping("/allergy/index")
	public String index(HttpSession session, Model model) {
		Long patId = (Long) session.getAttribute("pid");
		Patient patient = patientService.findPatient(patId);
		model.addAttribute("patient", patient);
		
		Long visitId = (Long) session.getAttribute("eid");
		Visit currVisit = visitService.findVisitById(visitId);
		model.addAttribute("visit", currVisit);
		String nkaCheck = "false";
		List<Allergy> tempAllergies = patient.getAllergies();
		for(int x = 0; x < tempAllergies.size(); x++) {
			if(tempAllergies.get(x).getAllergen().equals("No Known Allergies") && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
				nkaCheck = "true";
			}
		}
		model.addAttribute("nkaCheck", nkaCheck);
		return "/doctordojo/AllergyProfile.jsp";
	}
	
	@RequestMapping("/allergy/nka")
	public String nka(HttpSession session, Model model) {
		Long patId = (Long) session.getAttribute("pid");
		Patient patient = patientService.findPatient(patId);
		Allergy nka = new Allergy();
		nka.setAllergen("No Known Allergies");
		nka.setCategory("None");
		nka.setSeverity("None");
		nka.setReaction("None");
		Date curr = new Date();
		nka.setOnsetDate(curr);
		nka.setAllergyStatus("Active");
		nka.setPatient(patient);
		allergyService.createAllergy(nka);
		Long visitId = (Long) session.getAttribute("eid");
		Visit currVisit = visitService.findVisitById(visitId);
		Long billId = currVisit.getBilling().getId();
		Billing tempBill = billingService.findBilling(billId);
		Double tempCharge = tempBill.getCharge();
		tempCharge += 3.00;
		tempBill.setCharge(tempCharge);
		billingService.createBilling(tempBill);
		model.addAttribute("patient", patient);
		model.addAttribute("visit", currVisit);
		String nkaCheck = "false";
		List<Allergy> tempAllergies = patient.getAllergies();
		for(int x = 0; x < tempAllergies.size(); x++) {
			if(tempAllergies.get(x).getAllergen().equals("No Known Allergies") && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
				nkaCheck = "true";
			}
		}
		model.addAttribute("nkaCheck", nkaCheck);
		return "redirect:/allergy/index";
	}
	
	@RequestMapping("/allergy/new")
	public String newAllergy(Model model, @ModelAttribute Allergy allergy, HttpSession session) {
		Long patId = (Long) session.getAttribute("pid");
		Patient patient = patientService.findPatient(patId);
		model.addAttribute("patient", patient);
		
		Long visitId = (Long) session.getAttribute("eid");
		Visit currVisit = visitService.findVisitById(visitId);
		model.addAttribute("visit", currVisit);
		String nkaCheck = "false";
		List<Allergy> tempAllergies = patient.getAllergies();
		for(int x = 0; x < tempAllergies.size(); x++) {
			if(tempAllergies.get(x).getAllergen().equals("No Known Allergies") && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
				nkaCheck = "true";
			}
		}
		model.addAttribute("nkaCheck", nkaCheck);
		return "/doctordojo/NewAllergy.jsp";
	}
	
	@PostMapping("/allergy/new")
	public String addAllergy(@Valid @ModelAttribute Allergy allergy, BindingResult result, HttpSession session, @RequestParam("oDate") String oDate, Model model) {
		Long patId = (Long) session.getAttribute("pid");
		Patient patient = patientService.findPatient(patId);
		
		
		if (!allergy.getAllergen().equals("No Known Allergies")) { 
			List<Allergy> allergies = patient.getAllergies(); 
			for (Allergy a : allergies) { 
				if (a.getAllergen().equals("No Known Allergies")){ 
					a.setAllergyStatus("Resolved"); 
					a.setResolveDate(new Date()); 
				} 
			} 
		}
		
		allergy.setPatient(patient);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		try {
			Date tempDate = format.parse(oDate);
			allergy.setOnsetDate(tempDate);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		if(result.hasErrors()) {
			Long visitId = (Long) session.getAttribute("eid");
			Long patientId = (Long) session.getAttribute("pid");
			Patient currPatient = patientService.findPatient(patientId);
			Visit currVisit = visitService.findVisitById(visitId);
			model.addAttribute("patient", currPatient);
			model.addAttribute("visit", currVisit);
			String nkaCheck = "false";
			List<Allergy> tempAllergies = currPatient.getAllergies();
			for(int x = 0; x < tempAllergies.size(); x++) {
				if(tempAllergies.get(x).getAllergen().equals("No Known Allergies") && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
					nkaCheck = "true";
				}
			}
			model.addAttribute("nkaCheck", nkaCheck);
			return "/doctordojo/NewAllergy.jsp";
		} else {
			allergyService.createAllergy(allergy);
			Long visitId = (Long) session.getAttribute("eid");
			Visit tempVisit = visitService.findVisitById(visitId);
			Long billId = tempVisit.getBilling().getId();
			Billing tempBill = billingService.findBilling(billId);
			Double tempCharge = tempBill.getCharge();
			tempCharge = tempBill.getCharge();
			tempCharge += 0.50;
			tempBill.setCharge(tempCharge);
			billingService.updateBilling(tempBill);
			return "redirect:/allergy/index";
		}
	}
	
	@RequestMapping("/allergy/edit/{id}")
	public String editAllergy(Model model, @PathVariable("id") Long id, HttpSession session) {
		Allergy allergy = allergyService.findAllergy(id);
		model.addAttribute("allergy", allergy);
		Long patId = (Long) session.getAttribute("pid");
		Patient patient = patientService.findPatient(patId);
		model.addAttribute("patient", patient);
		
		Long visitId = (Long) session.getAttribute("eid");
		Visit currVisit = visitService.findVisitById(visitId);
		model.addAttribute("visit", currVisit);
		String nkaCheck = "false";
		List<Allergy> tempAllergies = patient.getAllergies();
		for(int x = 0; x < tempAllergies.size(); x++) {
			if(tempAllergies.get(x).getAllergen().equals("No Known Allergies") && tempAllergies.get(x).getAllergyStatus().contentEquals("Active")) {
				nkaCheck = "true";
			}
		}
		model.addAttribute("nkaCheck", nkaCheck);
		return "/doctordojo/EditAllergy.jsp";
	}
	
	@RequestMapping(value="/allergy/edit/{id}", method=RequestMethod.PUT)
	public String updateAllergy(@Valid @ModelAttribute Allergy allergy, BindingResult result, @RequestParam("rDate") String rDate, HttpSession session, @PathVariable("id") Long id, Model model) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		try {
			Date tempDate = format.parse(rDate);
			Date currDate = new Date();
			if(tempDate.before(currDate)) {
				allergy.setResolveDate(currDate);
				allergy.setAllergyStatus("Resolved");
			} else {
				allergy.setAllergyStatus("Resolved");
				allergy.setResolveDate(tempDate);
			}
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		Allergy oldAllergy = allergyService.findAllergy(id);
		allergy.setOnsetDate(oldAllergy.getOnsetDate());
		
		if(result.hasErrors()) {
			Long visitId = (Long) session.getAttribute("eid");
			Long patientId = (Long) session.getAttribute("pid");
			Patient currPatient = patientService.findPatient(patientId);
			Visit currVisit = visitService.findVisitById(visitId);
			model.addAttribute("patient", currPatient);
			model.addAttribute("visit", currVisit);
			String nkaCheck = "false";
			List<Allergy> tempAllergies = currPatient.getAllergies();
			for(int x = 0; x < tempAllergies.size(); x++) {
				if(tempAllergies.get(x).getAllergen().equals("No Known Allergies") && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
					nkaCheck = "true";
				}
			}
			model.addAttribute("nkaCheck", nkaCheck);
			return "/doctordojo/EditAllergy.jsp";
		} else {
			allergyService.updateAllergy(allergy);
			
			Long visitId = (Long) session.getAttribute("eid");
			Visit tempVisit = visitService.findVisitById(visitId);
			Long billId = tempVisit.getBilling().getId();
			Billing tempBill = billingService.findBilling(billId);
			Double tempCharge = tempBill.getCharge();
			tempCharge += 0.50;
			tempBill.setCharge(tempCharge);
			billingService.updateBilling(tempBill);
			return "redirect:/allergy/index";
		}
	}

}
