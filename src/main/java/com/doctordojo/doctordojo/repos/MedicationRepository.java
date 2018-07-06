package com.doctordojo.doctordojo.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.doctordojo.doctordojo.models.Medication;

@Repository
public interface MedicationRepository extends CrudRepository<Medication, Long>{

}
