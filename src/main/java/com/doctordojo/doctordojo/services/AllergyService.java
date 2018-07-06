package com.doctordojo.doctordojo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.doctordojo.doctordojo.models.Allergy;
import com.doctordojo.doctordojo.repos.AllergyRepository;

@Service
public class AllergyService {
	
	private final AllergyRepository allergyRepo;
	
	public AllergyService(AllergyRepository allergyRepo) {
		this.allergyRepo = allergyRepo;
	}
	
	public Allergy createAllergy(Allergy allergy) {
		return allergyRepo.save(allergy);
	}
	
	public Allergy findAllergy(Long id) {
		Optional<Allergy> optionalAllergy = allergyRepo.findById(id);
		if(optionalAllergy.isPresent()) {
			return optionalAllergy.get();
		} else {
			return null;
		}
	}
	
	public Allergy updateAllergy(Allergy a) {
		return allergyRepo.save(a);
	}

}
