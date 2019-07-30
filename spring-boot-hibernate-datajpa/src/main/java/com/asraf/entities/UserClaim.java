package com.asraf.entities;
// Generated Jun 12, 2019 3:26:05 PM by Hibernate Tools 5.2.12.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UserClaim generated by hbm2java
 */
@Entity
@Table(catalog = "myauthdb")
public class UserClaim extends BaseEntity implements java.io.Serializable {

	private Long id;
	private User user;
	private String claimType;
	private String claimValue;

	public UserClaim() {
	}

	public UserClaim(User user, String claimType, String claimValue) {
		this.user = user;
		this.claimType = claimType;
		this.claimValue = claimValue;
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
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "claim_type", nullable = false, length = 100)
	public String getClaimType() {
		return this.claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	@Column(name = "claim_value", nullable = false, length = 100)
	public String getClaimValue() {
		return this.claimValue;
	}

	public void setClaimValue(String claimValue) {
		this.claimValue = claimValue;
	}

}
