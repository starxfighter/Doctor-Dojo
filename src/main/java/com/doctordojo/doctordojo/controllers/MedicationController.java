package com.doctordojo.doctordojo.controllers;

import java.text.DateFormat;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.doctordojo.doctordojo.models.Allergy;
import com.doctordojo.doctordojo.models.Billing;
import com.doctordojo.doctordojo.models.Medication;
import com.doctordojo.doctordojo.models.Patient;
import com.doctordojo.doctordojo.models.Visit;
import com.doctordojo.doctordojo.services.BillingService;
import com.doctordojo.doctordojo.services.MedicationService;
import com.doctordojo.doctordojo.services.PatientService;
import com.doctordojo.doctordojo.services.VisitService;

@Controller
public class MedicationController {
	private final MedicationService medicationService;
	private final PatientService patientService;
	private final VisitService visitService;
	private final BillingService billingService;
	
	public MedicationController(VisitService visitService, MedicationService medicationService, PatientService patientService, BillingService billingService) {
		this.medicationService = medicationService;
		this.patientService = patientService;
		this.visitService = visitService;
		this.billingService = billingService;
	}
	
	@RequestMapping("/medication/index")
	public String index(HttpSession session, Model model) {
		Long visitId = (Long) session.getAttribute("eid");
		Visit currVisit = visitService.findVisitById(visitId);
		model.addAttribute("visit", currVisit);
		Long patId = (Long) session.getAttribute("pid");
		Patient patient = patientService.findPatient(patId);
		model.addAttribute("patient", patient);
		String nkaCheck = "false";
		List<Allergy> tempAllergies = patient.getAllergies();
		for(int x = 0; x < tempAllergies.size(); x++) {
			if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
				System.out.println("make switch");
				nkaCheck = "true";
			}
		}
		model.addAttribute("nkaCheck", nkaCheck);
		return "/doctordojo/MedicationProfile.jsp";
	}
	
	@RequestMapping("/medication/new/{patientId}")
	public String newMedication(@ModelAttribute("medicationToAdd") Medication medication, @PathVariable("patientId") Long patientId, HttpSession session, Model model) {
		Long visitId = (Long) session.getAttribute("eid");
		Visit currVisit = visitService.findVisitById(visitId);
		model.addAttribute("visit", currVisit);
		Long patId = (Long) session.getAttribute("pid");
		Patient patient = patientService.findPatient(patId);
		model.addAttribute("patient", patient);
		String nkaCheck = "false";
		List<Allergy> tempAllergies = patient.getAllergies();
		for(int x = 0; x < tempAllergies.size(); x++) {
			if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
				System.out.println("make switch");
				nkaCheck = "true";
			}
		}
		model.addAttribute("nkaCheck", nkaCheck);
		return "/doctordojo/createMedication.jsp";
	}
	
	@RequestMapping(value="/medication/new/{patientId}", method=RequestMethod.POST)
	public String newMedicationprocess(@Valid @ModelAttribute("medicationToAdd") Medication medication, BindingResult result, @PathVariable("patientId") Long patientId, @RequestParam("startDay") String startDay, @RequestParam("endDay") String endDay, Model model, HttpSession session) {
		if (result.hasErrors()) {
			System.out.println("Errors creating the medication.");
			System.out.println(result);
			Long visitId = (Long) session.getAttribute("eid");
			Visit currVisit = visitService.findVisitById(visitId);
			model.addAttribute("visit", currVisit);
			Long patId = (Long) session.getAttribute("pid");
			Patient patient = patientService.findPatient(patId);
			model.addAttribute("patient", patient);
			String nkaCheck = "false";
			List<Allergy> tempAllergies = patient.getAllergies();
			for(int x = 0; x < tempAllergies.size(); x++) {
				if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
					System.out.println("make switch");
					nkaCheck = "true";
				}
			}
			model.addAttribute("nkaCheck", nkaCheck);
			return "/doctordojo/createMedication.jsp";
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		try {
			//Add proper date format
			System.out.println("in try medication add");
			Date startDateToAdd = df.parse(startDay);
			if(endDay.length() > 0) {
				Date endDateToAdd = df.parse(endDay);
				if (startDateToAdd.compareTo(endDateToAdd) > 0) {
					model.addAttribute("error", "Start and end date must be in chronological order.");
					Long visitId = (Long) session.getAttribute("eid");
					Visit currVisit = visitService.findVisitById(visitId);
					model.addAttribute("visit", currVisit);
					Long patId = (Long) session.getAttribute("pid");
					Patient patient = patientService.findPatient(patId);
					model.addAttribute("patient", patient);
					String nkaCheck = "false";
					List<Allergy> tempAllergies = patient.getAllergies();
					for(int x = 0; x < tempAllergies.size(); x++) {
						if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
							System.out.println("make switch");
							nkaCheck = "true";
						}
					}
					model.addAttribute("nkaCheck", nkaCheck);
					return "/doctordojo/createMedication.jsp";
				}
				medication.setEndDate(endDateToAdd);
			}
			

			System.out.println("setting stuff");
			Long patId = (Long) session.getAttribute("pid");
			Patient patient = patientService.findPatient(patId);
			medication.setPatient(patient);
		    medication.setStartDate(startDateToAdd);
		    
		    System.out.println("med object" + medication.getPatient());
		    
			} catch (ParseException e) {
				e.printStackTrace();
			}

		medicationService.createMedication(medication);
		
		Long visitId = (Long) session.getAttribute("eid");
		Visit tempVisit = visitService.findVisitById(visitId);
		Long billId = tempVisit.getBilling().getId();
		System.out.println("visitid" + visitId);
		System.out.println("Print bid" + billId);
		Billing tempBill = billingService.findBilling(billId);
		Double tempCharge =  tempBill.getCharge();
		tempCharge += 3.00;
		tempBill.setCharge(tempCharge);
		billingService.updateBilling(tempBill);
		return "redirect:/medication/index";
	}
	
	@RequestMapping("/medication/edit/{medicationId}")
	public String editMedication(@ModelAttribute("medicationToEdit") Medication medication, Model model, @PathVariable("medicationId") Long medicationId, HttpSession session) {
		Long visitId = (Long) session.getAttribute("eid");
		Visit currVisit = visitService.findVisitById(visitId);
		model.addAttribute("visit", currVisit);
		Long patId = (Long) session.getAttribute("pid");
		Patient patient = patientService.findPatient(patId);
		model.addAttribute("patient", patient);
		String nkaCheck = "false";
		List<Allergy> tempAllergies = patient.getAllergies();
		for(int x = 0; x < tempAllergies.size(); x++) {
			if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
				System.out.println("make switch");
				nkaCheck = "true";
			}
		}
		model.addAttribute("nkaCheck", nkaCheck);
		Medication currMedication = medicationService.findMedicationById(medicationId);
		model.addAttribute("currMedication", currMedication);
		return "/doctordojo/editMedication.jsp";
	}
	
	@RequestMapping(value="/medication/edit/{medicationId}", method=RequestMethod.PUT)
	public String editMedicationProcess(@Valid @ModelAttribute("medicationToEdit") Medication medication, BindingResult result, Model model, @PathVariable("medicationId") Long medicationId, @RequestParam("startDay") String startDate, @RequestParam("endDay") String endDate, HttpSession session) {
		if (result.hasErrors()) {
			
		
			Long visitId = (Long) session.getAttribute("eid");
			Visit currVisit = visitService.findVisitById(visitId);
			model.addAttribute("visit", currVisit);
			Long patId = (Long) session.getAttribute("pid");
			Patient patient = patientService.findPatient(patId);
			model.addAttribute("patient", patient);
			String nkaCheck = "false";
			List<Allergy> tempAllergies = patient.getAllergies();
			for(int x = 0; x < tempAllergies.size(); x++) {
				if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
					System.out.println("make switch");
					nkaCheck = "true";
				}
			}
			model.addAttribute("nkaCheck", nkaCheck);
			
			System.out.println("Editing process failed.");
			System.out.println(result);
			
			return "/doctordojo/editMedication.jsp";
		}
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date dateToAdd;
		try {
			//Add proper date format
			System.out.println("in med update try");
			Date startDateToAdd = df.parse(startDate);
			
			System.out.println("finding medication");
			Medication currMedication = medicationService.findMedicationById(medicationId);
			System.out.println("enddate" + endDate + endDate.length());
			if(endDate.length() > 0) {
				Date endDateToAdd = df.parse(endDate);
				if (startDateToAdd.compareTo(endDateToAdd) > 0) {
					model.addAttribute("error", "Start and end date must be in chronological order.");
					Long visitId = (Long) session.getAttribute("eid");
					Visit currVisit = visitService.findVisitById(visitId);
					model.addAttribute("visit", currVisit);
					Long patId = (Long) session.getAttribute("pid");
					Patient patient = patientService.findPatient(patId);
					model.addAttribute("patient", patient);
					String nkaCheck = "false";
					List<Allergy> tempAllergies = patient.getAllergies();
					for(int x = 0; x < tempAllergies.size(); x++) {
						if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
							System.out.println("make switch");
							nkaCheck = "true";
						}
					}
					model.addAttribute("nkaCheck", nkaCheck);
					return "/doctordojo/createMedication.jsp";
				}
				currMedication.setEndDate(endDateToAdd);
			}
			
			
			System.out.println("setting items");
			currMedication.setDrugName(medication.getDrugName());
			currMedication.setDrugForm(medication.getDrugForm());
			currMedication.setFrequency(medication.getFrequency());
			currMedication.setDuration(medication.getDuration());
			currMedication.setDosage(medication.getDosage());
			currMedication.setStartDate(startDateToAdd);
		    
		    System.out.println("saving med:");
		    medicationService.createMedication(currMedication);
		    
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Long visitId = (Long) session.getAttribute("eid");
		Visit tempVisit = visitService.findVisitById(visitId);
		Long billId = tempVisit.getBilling().getId();
		System.out.println("visitid" + visitId);
		System.out.println("Print bid" + billId);
		Billing tempBill = billingService.findBilling(billId);
		Double tempCharge =  tempBill.getCharge();
		tempCharge += 0.50;
		tempBill.setCharge(tempCharge);
		billingService.updateBilling(tempBill);
		return "redirect:/medication/index";
	}
	
	@RequestMapping("/medication/delete/{medicationId}")
	public String deleteMedication(@PathVariable("medicationId") Long medicationId) {
		medicationService.deleteMedication(medicationId);
		return "redirect:/medication/index";
	}
	
	

}
