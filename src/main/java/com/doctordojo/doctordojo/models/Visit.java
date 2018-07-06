package com.doctordojo.doctordojo.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="visits")
public class Visit {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private int caseNumber;
	private String visitStatus;
	@Size(min=1, message = "Enter a reason")
	private String reason;
	private String diagnosis;
	private Date visitDate;
	private int diastolic;
	private int systolic;
	private int pulserate;
	private int respiration;
	private float temperature;
	private int heightFeet;
	private int heightInches;
	private int weight;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="patient_id")
	private Patient patient;
	
	@OneToMany(mappedBy = "visit", fetch = FetchType.LAZY)
	private List<Note> notes;
	
	@OneToOne(mappedBy="visit", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Billing billing;
	
	@Column(updatable=false)
	private Date createdAt;
	@SuppressWarnings("unused")
	private Date updatedAt;
	
	public Visit() {}

	
	public int getDiastolic() {
		return diastolic;
	}

	public void setDiastolic(int diastolic) {
		this.diastolic = diastolic;
	}

	public int getRespiration() {
		return respiration;
	}


	public void setRespiration(int respiration) {
		this.respiration = respiration;
	}


	public int getSystolic() {
		return systolic;
	}

	public void setSystolic(int systolic) {
		this.systolic = systolic;
	}

	public int getPulserate() {
		return pulserate;
	}

	public void setPulserate(int pulserate) {
		this.pulserate = pulserate;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public int getHeightFeet() {
		return heightFeet;
	}

	public void setHeightFeet(int heightFeet) {
		this.heightFeet = heightFeet;
	}

	public int getHeightInches() {
		return heightInches;
	}

	public void setHeightInches(int heightInches) {
		this.heightInches = heightInches;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Billing getBilling() {
		return billing;
	}

	public void setBilling(Billing billing) {
		this.billing = billing;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(int caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getVisitStatus() {
		return visitStatus;
	}

	public void setVisitStatus(String status) {
		this.visitStatus = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
	
}
