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
import com.doctordojo.doctordojo.models.Patient;
import com.doctordojo.doctordojo.models.Visit;
import com.doctordojo.doctordojo.services.EncounterService;
import com.doctordojo.doctordojo.services.PatientService;
import com.doctordojo.doctordojo.services.VisitService;

@Controller
public class EncounterController {
	
	private final EncounterService encounterService;
	private final VisitService visitService;
	private final PatientService patientService;
	
	public EncounterController(PatientService patientService, EncounterService encounterService, VisitService visitService) {
		this.encounterService = encounterService;
		this.visitService = visitService;
		this.patientService = patientService;
	}

	@RequestMapping("/encounter/index")
	public String index(HttpSession session, Model model) {
		Long tempVid = (Long) session.getAttribute("eid");
		System.out.println("session visit id" + tempVid);
		Visit tempVisit = visitService.findVisitById(tempVid);
		model.addAttribute("visit", tempVisit);
		Long patId = (Long) session.getAttribute("pid");
		Patient tempPat = patientService.findPatient(patId);
		model.addAttribute("patient",tempPat);
		String nkaCheck = "false";
		List<Allergy> tempAllergies = tempPat.getAllergies();
		for(int x = 0; x < tempAllergies.size(); x++) {
			if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
				nkaCheck = "true";
			}
		}
		model.addAttribute("nkaCheck", nkaCheck);
		return "/doctordojo/EncounterView.jsp";
	}
	
	@RequestMapping("/encounter/index/{id}")
	public String indexAlt(Model model, @PathVariable("id") Long id, HttpSession session) {
		String message = (String) session.getAttribute("message");
		if(message != null) {
			model.addAttribute("error", message);
			session.removeAttribute("message");
		}
		Visit tempVisit = visitService.findVisitById(id);
		model.addAttribute("visit", tempVisit);
		session.setAttribute("eid", id);
		Long patId = (Long) session.getAttribute("pid");
		Patient tempPat = patientService.findPatient(patId);
		model.addAttribute("patient",tempPat);
		String nkaCheck = "false";
		List<Allergy> tempAllergies = tempPat.getAllergies();
		for(int x = 0; x < tempAllergies.size(); x++) {
			if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
				nkaCheck = "true";
			}
		}
		model.addAttribute("nkaCheck", nkaCheck);
		return "/doctordojo/EncounterView.jsp";
	}
	
	@RequestMapping(value="/encounter/edit/{id}", method=RequestMethod.PUT)
	public String update(@Valid @ModelAttribute Visit visit, BindingResult result, @PathVariable("id") Long eid, HttpSession session) {
		if(result.hasErrors()) {
			return "/doctordojo/EncounterView.jsp";
		} else {
			System.out.println("encounter edit");
			Visit oldVisit = visitService.findVisitById(eid);
			visit.setVisitDate(oldVisit.getVisitDate());
			Visit tempVisit = visitService.createVisit(visit);
			session.setAttribute("message", "Patient successfully updated");
			return "redirect:/encounter/index/" + tempVisit.getId();
		}
	}
	
}
