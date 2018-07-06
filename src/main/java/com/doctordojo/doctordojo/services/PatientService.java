package com.doctordojo.doctordojo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.doctordojo.doctordojo.models.Patient;
import com.doctordojo.doctordojo.repos.PatientRepository;


@Service
public class PatientService {
		
	private final PatientRepository patientRepo;
	
	public PatientService(PatientRepository patientRepo) {
		this.patientRepo = patientRepo;
	}
	
	public Patient createPatient(Patient patient) {
		return patientRepo.save(patient);
	}
	
	public List<Patient> searchPatient(String search){
		return patientRepo.findByLastNameContaining(search);
	}
	
	public Patient findPatient(Long id) {
		Optional<Patient> optionalPatient = patientRepo.findById(id);
		if(optionalPatient.isPresent()) {
			return optionalPatient.get();
		} else {
			return null;
		}
	}
	
	public Patient updatePatient(Patient p) {
		return patientRepo.save(p);
	}
	
	public void deletePatient(long id) {
		System.out.print("in pat delete service");
		patientRepo.deleteById(id);
	}

}
