package org.crank.security.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@NamedQuery(name = "in3.loadRole", 
		query = "select role from In3Role role where role.name=:name")
		
@Table(name="IN3_ROLE")
@Entity(name="In3Role")
public class Role {
	
	
	@Id
	@GeneratedValue
	private Long id;
	

	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role(String name) {
		this.name = name;
	}

	public Role() {
	}

}
