package com.asraf.rsrc.entities;
// Generated Jul 29, 2019 12:51:18 PM by Hibernate Tools 5.2.12.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * StudentVerification generated by hbm2java
 */
@Entity
@Table(name = "student_verification")
public class StudentVerification extends BaseEntity implements java.io.Serializable {

	private Long id;
	private Student student;
	private String verificationCode;
	private Date creationTime;

	public StudentVerification() {
	}

	public StudentVerification(Student student, String verificationCode, Date creationTime) {
		this.student = student;
		this.verificationCode = verificationCode;
		this.creationTime = creationTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Column(name = "verification_code", nullable = false, length = 45)
	public String getVerificationCode() {
		return this.verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_time", nullable = false, length = 19)
	public Date getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

}