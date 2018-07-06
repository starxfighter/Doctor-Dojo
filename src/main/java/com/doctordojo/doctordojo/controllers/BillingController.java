package com.doctordojo.doctordojo.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.doctordojo.doctordojo.models.Billing;
import com.doctordojo.doctordojo.services.BillingService;

@Controller
public class BillingController {
	
	private final BillingService billingService;
	
	public BillingController(BillingService billingService) {
		this.billingService = billingService;
	}
	
		
	@RequestMapping("/billing/index")
	public String index(Model model) {
		List<Billing> tempBills = billingService.findOpenBills();
		model.addAttribute("bills", tempBills);
		return "/doctordojo/BillingProfile.jsp";
	}
//

//	}
	
	@PostMapping("/billing/chargecomplete/{id}")
	public String completeCheckOut(HttpSession session, @PathVariable("id") Long id) {
		System.out.println("in charge complete");
//		Long billId = (Long) session.getAttribute("id");
//		System.out.println("billing id" + billId);
		Billing tempBill = billingService.findBilling(id);
		System.out.println(tempBill);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String today = dateFormat.format(date);
		try {
			Date currDate = dateFormat.parse(today);
			System.out.println(currDate);
			tempBill.setBillingDate(currDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		tempBill.setBillingStatus("Closed");
		tempBill.getVisit().setVisitStatus("Complete");
		System.out.println("update billing");
		billingService.updateBilling(tempBill);
		return "redirect:/dashboard";
	}
			

}
