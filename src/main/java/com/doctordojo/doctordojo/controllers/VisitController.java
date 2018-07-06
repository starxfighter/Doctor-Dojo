package com.doctordojo.doctordojo.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.doctordojo.doctordojo.models.Allergy;
import com.doctordojo.doctordojo.models.Billing;
import com.doctordojo.doctordojo.models.Patient;
import com.doctordojo.doctordojo.models.Visit;
import com.doctordojo.doctordojo.services.BillingService;
import com.doctordojo.doctordojo.services.PatientService;
import com.doctordojo.doctordojo.services.VisitService;

@Controller
public class VisitController {
	private final VisitService visitService;
	private final PatientService patientService;
	private final BillingService billingService;
	
	public VisitController(VisitService visitService, PatientService patientService, BillingService billingService) {
		this.visitService = visitService;
		this.patientService = patientService;
		this.billingService = billingService;
	}
	
	@RequestMapping("/visit/dashboard/{id}")
	public String dashboard(HttpSession session, Model model,@PathVariable("id") Long pid) {
		Patient tempPat = patientService.findPatient(pid);
		session.setAttribute("pid", pid);
		String nkaCheck = "false";
		model.addAttribute("patient", tempPat);
		List<Allergy> tempAllergies = tempPat.getAllergies();
		for(int x = 0; x < tempAllergies.size(); x++) {
			if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
				nkaCheck = "true";
			}
		}
		model.addAttribute("nkaCheck", nkaCheck);
		model.addAttribute("pid", pid);
		return "/doctordojo/VisitDashboard.jsp";
	}
	
	@RequestMapping("visit/historical/{id}")
	public String historyView(HttpSession session, Model model, @PathVariable("id") Long eid) {
		Long patId = (Long) session.getAttribute("pid");
		Patient tempPat = patientService.findPatient(patId);
		model.addAttribute("patthx", tempPat);
		
		Visit tempVisit = visitService.findVisitById(eid);
		model.addAttribute("visithx", tempVisit);
		
		Billing tempBill = billingService.findBilling(tempVisit.getBilling().getId());
		model.addAttribute("billhx", tempBill);
		return "/doctordojo/VisitDashboard.jsp";
	}
	
	@RequestMapping("/visit/new/{patientId}")
	public String visitForm(@ModelAttribute("visitToCreate") Visit visit, @PathVariable("patientId") Long patientId, Model model, HttpSession session) {
		session.setAttribute("pid", patientId);
		model.addAttribute("patientId", patientId);
		Patient tempPat = patientService.findPatient(patientId);
		model.addAttribute("patient",tempPat);
		String nkaCheck = "false";
		List<Allergy> tempAllergies = tempPat.getAllergies();
		for(int x = 0; x < tempAllergies.size(); x++) {
			System.out.println(tempAllergies.get(x).getAllergen());
			System.out.println(tempAllergies.get(x).getAllergyStatus());
			if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
				System.out.println("make switch");
				nkaCheck = "true";
			}
		}
		System.out.println("checking nka status" + nkaCheck);
		model.addAttribute("nkaCheck", nkaCheck);
		return "/doctordojo/createVisit.jsp";
	}
	
	@RequestMapping(value="/visit/new/{patientId}", method=RequestMethod.POST)
	public String visitProcess(@Valid @ModelAttribute("visitToCreate") Visit visit, BindingResult result, @PathVariable("patientId") Long patientId, @RequestParam(required=false, name="visitDay") String visitDate, Model model, HttpSession session) {
		Long patId = (Long) session.getAttribute("pid");
		Patient tempPat = patientService.findPatient(patId);
		visit.setPatient(tempPat);
		model.addAttribute("patient", tempPat);
		if (result.hasErrors()) {
			System.out.println("Something went wrong while creating the visit!");
			System.out.println(result);
			session.setAttribute("pid", patId);
			String nkaCheck = "false";
			model.addAttribute("patient", tempPat);
			List<Allergy> tempAllergies = tempPat.getAllergies();
			for(int x = 0; x < tempAllergies.size(); x++) {
				if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
					nkaCheck = "true";
				}
			}
			model.addAttribute("nkaCheck", nkaCheck);
			return "/doctordojo/createVisit.jsp";
		}
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date dateToAdd;
		try {
			dateToAdd = df.parse(visitDate);
			Date dateToday = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(dateToday);
			c.add(Calendar.DATE, -1);
			Date dateBeforeToday = c.getTime();
			if (dateToAdd.before(dateBeforeToday)) {
				model.addAttribute("error", "Date must be in the future.");
				session.setAttribute("pid", patId);
				String nkaCheck = "false";
				model.addAttribute("patient", tempPat);
				List<Allergy> tempAllergies = tempPat.getAllergies();
				for(int x = 0; x < tempAllergies.size(); x++) {
					if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
						nkaCheck = "true";
					}
				}
				model.addAttribute("nkaCheck", nkaCheck);
				return "/doctordojo/createVisit.jsp";
			}
		    visit.setVisitDate(dateToAdd);
		    System.out.println(dateToAdd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Random random = new Random();
		visit.setCaseNumber(random.nextInt(900000) + 100000);
		
		Visit tempVisit = visitService.createVisit(visit);
		Billing tempBill = new Billing();
		tempBill.setVisit(tempVisit);
		tempBill.setCharge(20.00);
		tempBill.setBillingStatus("Open");
		billingService.createBilling(tempBill);
		session.setAttribute("bid", tempBill.getId());
		session.setAttribute("eid", tempVisit.getId());
		model.addAttribute("visit", tempVisit);
		
		return "redirect:/encounter/index";
	}
	
	@RequestMapping("/visit/edit/{visitId}")
	public String editVisit(@ModelAttribute("visitToEdit") Visit visit, Model model, @PathVariable("visitId") Long visitId, HttpSession session) {
		Visit currVisit = visitService.findVisitById(visitId);
		model.addAttribute("currVisit", currVisit);
		Long patId = (Long) session.getAttribute("pid");
		Patient tempPat = patientService.findPatient(patId);
		model.addAttribute("patient",tempPat);
		String nkaCheck = "false";
		List<Allergy> tempAllergies = tempPat.getAllergies();
		for(int x = 0; x < tempAllergies.size(); x++) {
			if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
				System.out.println("make switch");
				nkaCheck = "true";
			}
		}
		model.addAttribute("nkaCheck", nkaCheck);
		return "/doctordojo/editVisit.jsp";
	}
	
	@RequestMapping(value="/visit/edit/{visitId}", method=RequestMethod.PUT)
	public String editVisitProcess(@Valid @ModelAttribute("visitToEdit") Visit visit, BindingResult result, Model model, @RequestParam("visitDay") String visitDate, @PathVariable("visitId") Long visitId, HttpSession session) {
		//for date
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date dateToAdd;
		try {
			//Add proper date format
			dateToAdd = df.parse(visitDate);
			Date dateToday = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(dateToday);
			c.add(Calendar.DATE, -1);
			Date dateBeforeToday = c.getTime();
			if (dateToAdd.before(dateBeforeToday)) {
				model.addAttribute("error", "Date must be in the future.");
				Long patientId = (Long) session.getAttribute("pid");
				Patient currPatient = patientService.findPatient(patientId);
				Visit currVisit = visitService.findVisitById(visitId);
				model.addAttribute("patient", currPatient);
				model.addAttribute("currVisit", currVisit);
				String nkaCheck = "false";
				List<Allergy> tempAllergies = currPatient.getAllergies();
				for(int x = 0; x < tempAllergies.size(); x++) {
					if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
						System.out.println("make switch");
						nkaCheck = "true";
					}
				}
				model.addAttribute("nkaCheck", nkaCheck);
				return "/doctordojo/editVisit.jsp";
			}
				
		    visit.setVisitDate(dateToAdd);
		    System.out.println(dateToAdd);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if (result.hasErrors()) {
			
			Long patientId = (Long) session.getAttribute("pid");
			Patient currPatient = patientService.findPatient(patientId);
			Visit currVisit = visitService.findVisitById(visitId);
			
			model.addAttribute("patient", currPatient);
			model.addAttribute("currVisit", currVisit);
			String nkaCheck = "false";
			List<Allergy> tempAllergies = currPatient.getAllergies();
			for(int x = 0; x < tempAllergies.size(); x++) {
				if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
					System.out.println("make switch");
					nkaCheck = "true";
				}
			}
			model.addAttribute("nkaCheck", nkaCheck);
			System.out.println(result);
			return "/doctordojo/editVisit.jsp";
		}
		
		String reasonToAdd = visit.getReason();
		String diagnosisToAdd = visit.getDiagnosis();
		String statusToAdd = visit.getVisitStatus();
		int diastolicToAdd = visit.getDiastolic();
		int systolicToAdd = visit.getSystolic();
		int pulserateToAdd = visit.getPulserate();
		int respirationToAdd = visit.getRespiration();
		float temperatureToAdd = visit.getTemperature();
		int heightFeetToAdd = visit.getHeightFeet();
		int heightInchesToAdd = visit.getHeightInches();
		int weightToAdd = visit.getWeight();
		
		Visit currVisit = visitService.findVisitById(visitId);
		currVisit.setReason(reasonToAdd);
		currVisit.setDiagnosis(diagnosisToAdd);
		currVisit.setVisitStatus(statusToAdd);
		currVisit.setVisitDate(visit.getVisitDate());
		currVisit.setDiastolic(diastolicToAdd);
		currVisit.setSystolic(systolicToAdd);
		currVisit.setPulserate(pulserateToAdd);
		currVisit.setRespiration(respirationToAdd);
		currVisit.setTemperature(temperatureToAdd);
		currVisit.setHeightFeet(heightFeetToAdd);
		currVisit.setHeightInches(heightInchesToAdd);
		currVisit.setWeight(weightToAdd);
		visitService.createVisit(currVisit);
		
		Long patId = (Long) session.getAttribute("pid");
		
		return "redirect:/visit/dashboard/" + patId;
	}
	
	@RequestMapping("/visit/delete/{visitId}")
	public String deleteVisit(@PathVariable("visitId") Long visitId, HttpSession session) {
		visitService.deleteVisit(visitId);
		Long patId = (Long) session.getAttribute("pid");
		
		return "redirect:/visit/dashboard/" + patId;
	}

}
