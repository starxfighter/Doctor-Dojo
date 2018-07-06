package com.doctordojo.doctordojo.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="patients")
public class Patient {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Size(min=1, message = "Enter your first name.")
	private String firstName;
	@Size(min=1, message = "Enter your last name.")
	private String lastName;
	private String sex;
	private Date dob;
	private String password;
	@Transient
	private String confirmPassword;
	@Email(message = "Enter a valid email address.")
	private String email;
	@NotNull
	@Size(min=1, message = "Enter your street address.")
	private String streetAddress;
	@Size(min=1, message = "Enter your city.")
	private String city;
	@Size(min=2, max=2, message = "State must be two characters.")
	private String state;
	private int zip;
	private int mpi;
	private String phone;
	@Column(updatable=false)
	private Date createdAt;
	@SuppressWarnings("unused")
	private Date updatedAt;
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
	private List<Medication> medications;
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
	private List<Allergy> allergies;
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
	private List<Visit> visits;
	
	public Patient() {}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public int getMpi() {
		return mpi;
	}

	public void setMpi(int mpi) {
		this.mpi = mpi;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public List<Medication> getMedications() {
		return medications;
	}

	public void setMedications(List<Medication> meds) {
		this.medications = meds;
	}

	public List<Allergy> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<Allergy> allergies) {
		this.allergies = allergies;
	}

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
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
