package com.doctordojo.doctordojo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.doctordojo.doctordojo.models.Billing;

@Repository
public interface BillingRepository extends CrudRepository<Billing, Long> {
	
	@Query("Select b FROM Billing b WHERE billingStatus = 'Open'")
	List<Billing> findWherebillingStatusOpen();

}
