package com.doctordojo.doctordojo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.doctordojo.doctordojo.models.Visit;
import com.doctordojo.doctordojo.repos.VisitRepository;

@Service
public class VisitService {
	private final VisitRepository visitRepository;
	
	public VisitService(VisitRepository visitRepository) {
		this.visitRepository = visitRepository;
	}
	
	public Visit createVisit(Visit visit) {
		return this.visitRepository.save(visit);
	}
	
	public Visit findVisitById(Long id) {
		Optional<Visit> u = visitRepository.findById(id);
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
	}
	
	public void deleteVisit(Long id) {
		this.visitRepository.deleteById(id);
	}

}
