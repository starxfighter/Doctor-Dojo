package com.doctordojo.doctordojo.controllers;

import java.util.List;

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

import com.doctordojo.doctordojo.models.Allergy;
import com.doctordojo.doctordojo.models.Billing;
import com.doctordojo.doctordojo.models.Note;
import com.doctordojo.doctordojo.models.Patient;
import com.doctordojo.doctordojo.models.Visit;
import com.doctordojo.doctordojo.services.BillingService;
import com.doctordojo.doctordojo.services.NoteService;
import com.doctordojo.doctordojo.services.PatientService;
import com.doctordojo.doctordojo.services.VisitService;

@Controller
public class NoteController {
	
	private final NoteService noteService;
	private final PatientService patientService;
	private final VisitService visitService;
	private final BillingService billingService;
	
	public NoteController(BillingService billingService, NoteService noteService, PatientService patientService, VisitService visitService) {
		this.noteService = noteService;
		this.patientService = patientService;
		this.visitService = visitService;
		this.billingService = billingService;
	}
	
	@RequestMapping("/note/index")
	public String index(HttpSession session, Model model) {
		Long patId = (Long) session.getAttribute("pid");
		Patient patient = patientService.findPatient(patId);
		model.addAttribute("patient", patient);
		Long visitId = (Long) session.getAttribute("eid");
		Visit visit = visitService.findVisitById(visitId);
		model.addAttribute("visit", visit);
		String nkaCheck = "false";
		List<Allergy> tempAllergies = patient.getAllergies();
		for(int x = 0; x < tempAllergies.size(); x++) {
			if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
				System.out.println("make switch");
				nkaCheck = "true";
			}
		}
		model.addAttribute("nkaCheck", nkaCheck);
		
		return "/doctordojo/NoteProfile.jsp";
	}
	
	@RequestMapping("/note/new")
	public String newNote(Model model, @ModelAttribute Note note, HttpSession session) {
		Long patId = (Long) session.getAttribute("pid");
		Patient patient = patientService.findPatient(patId);
		model.addAttribute("patient", patient);
		Long visitId = (Long) session.getAttribute("eid");
		Visit visit = visitService.findVisitById(visitId);
		model.addAttribute("visit", visit);
		String nkaCheck = "false";
		List<Allergy> tempAllergies = patient.getAllergies();
		for(int x = 0; x < tempAllergies.size(); x++) {
			if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
				System.out.println("make switch");
				nkaCheck = "true";
			}
		}
		model.addAttribute("nkaCheck", nkaCheck);
		return "/doctordojo/NewNote.jsp";
	}
	
	@PostMapping("/note/new")
	public String addNote(@Valid @ModelAttribute Note note, BindingResult result, HttpSession session, Model model) {
		Long visitId = (Long) session.getAttribute("eid");
		Visit tempVisit = visitService.findVisitById(visitId);
		note.setVisit(tempVisit);
		note.setNoteStatus("Active");
		if(result.hasErrors()) {
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
			return "/doctordojo/NewNote.jsp";
		} else {
			noteService.createNote(note);
			
	
			Long billId = tempVisit.getBilling().getId();
			System.out.println("visitid" + visitId);
			System.out.println("Print bid" + billId);
			Billing tempBill = billingService.findBilling(billId);
			Double tempCharge =  tempBill.getCharge();
			tempCharge += 10.00;
			tempBill.setCharge(tempCharge);
			billingService.updateBilling(tempBill);

			return "redirect:/note/index";
		}
	}
	
	@RequestMapping("/note/edit/{id}")
	public String editNote(Model model, @PathVariable("id") Long id, HttpSession session) {
		Note note = noteService.fineNote(id);
		model.addAttribute("note", note);
		Long patId = (Long) session.getAttribute("pid");
		Patient patient = patientService.findPatient(patId);
		model.addAttribute("patient", patient);
		Long visitId = (Long) session.getAttribute("eid");
		Visit visit = visitService.findVisitById(visitId);
		model.addAttribute("visit", visit);
		String nkaCheck = "false";
		List<Allergy> tempAllergies = patient.getAllergies();
		for(int x = 0; x < tempAllergies.size(); x++) {
			if(tempAllergies.get(x).getAllergen().equals("No Known Allergies")  && tempAllergies.get(x).getAllergyStatus().equals("Active")) {
				System.out.println("make switch");
				nkaCheck = "true";
			}
		}
		model.addAttribute("nkaCheck", nkaCheck);
		return "/doctordojo/EditNote.jsp";
	}
	
	@RequestMapping(value="/note/edit/{id}", method=RequestMethod.PUT)
	public String updateNote(@Valid @ModelAttribute Note note, BindingResult result, HttpSession session,@PathVariable("id") Long id, Model model) {
		if(result.hasErrors()) {
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
			return "doctordojo/EditNote.jsp";
		} else {
			noteService.updateNote(note);
			
			Long visitId = (Long) session.getAttribute("eid");
			Visit tempVisit = visitService.findVisitById(visitId);
			Long billId = tempVisit.getBilling().getId();
			System.out.println("visitid" + visitId);
			System.out.println("Print bid" + billId);
			Billing tempBill = billingService.findBilling(billId);
			Double tempCharge =  tempBill.getCharge();
			tempCharge += 5.00;
			tempBill.setCharge(tempCharge);
			billingService.updateBilling(tempBill);
			
			return "redirect:/note/index";
		}
	}

}
