package com.doctordojo.doctordojo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.doctordojo.doctordojo.models.Billing;
import com.doctordojo.doctordojo.repos.BillingRepository;

@Service
public class BillingService {
		
	private final BillingRepository billingRepo;
	
	public BillingService(BillingRepository billingRepo) {
		this.billingRepo = billingRepo;
	}
	
	public Billing createBilling(Billing billing) {
		return billingRepo.save(billing);
	}
	
	public Billing findBilling(Long id) {
		Optional<Billing> optionalBilling = billingRepo.findById(id);
		if(optionalBilling.isPresent()) {
			return optionalBilling.get();
		} else {
			return null;
		}
	}
	
	public Billing updateBilling(Billing b) {
		return billingRepo.save(b);
	}
	
	public List<Billing> findOpenBills(){
		return billingRepo.findWherebillingStatusOpen();
	}

}
