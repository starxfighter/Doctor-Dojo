package com.doctordojo.doctordojo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.doctordojo.doctordojo.models.Medication;
import com.doctordojo.doctordojo.repos.MedicationRepository;

@Service
public class MedicationService {
	private final MedicationRepository medicationRepository;
	
	public MedicationService(MedicationRepository medicationRepository) {
		this.medicationRepository = medicationRepository;
	}
	
	public Medication createMedication(Medication medication) {
		return this.medicationRepository.save(medication);
	}
	
	public Medication findMedicationById(Long id) {
		Optional<Medication> u = medicationRepository.findById(id);
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
	}

	public void deleteMedication(Long id) {
		this.medicationRepository.deleteById(id);	
	}

}
