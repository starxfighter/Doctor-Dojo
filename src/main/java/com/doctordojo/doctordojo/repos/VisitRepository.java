package com.doctordojo.doctordojo.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.doctordojo.doctordojo.models.Visit;

@Repository
public interface VisitRepository extends CrudRepository<Visit, Long>{

}
