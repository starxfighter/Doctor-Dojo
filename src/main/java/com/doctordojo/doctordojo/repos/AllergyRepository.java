package com.doctordojo.doctordojo.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.doctordojo.doctordojo.models.Allergy;

@Repository
public interface AllergyRepository extends CrudRepository<Allergy, Long> {

}
