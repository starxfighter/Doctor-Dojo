package com.doctordojo.doctordojo.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.doctordojo.doctordojo.models.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long>{

	List<Patient> findByLastNameContaining(String search);
}
