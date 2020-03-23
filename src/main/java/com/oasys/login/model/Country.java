package com.oasys.login.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "country")
public class Country {
	@Id
	@Column(name = "idcountry")
	private String idCountry;
	
	@Column(name="country")
	private String country;
	
	public String getCountry() {
		return country;
	}

	public String getIdCountry() {
		return idCountry;
	}
	public void setIdCountry(String idCountry) {
		this.idCountry = idCountry;
	}
	

	// Users du pays

	@OneToMany(mappedBy="usercountry")
  	private Set<User> lstUsers;
	
	public void setCountry(String country) {
		this.country = country;
	}
	public Country() {
		super();
	}
	
	
	

}
