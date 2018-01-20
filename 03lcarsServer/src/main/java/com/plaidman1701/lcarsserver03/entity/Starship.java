package com.plaidman1701.lcarsserver03.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table (name="starships", uniqueConstraints = @UniqueConstraint (columnNames={"registry"}))

@NamedQueries ({
	@NamedQuery(name="getAllStarships", query="SELECT s FROM Starship s"),
	@NamedQuery(name="findStarship", query="SELECT s FROM Starship s WHERE s.registry= :stringToFind")
	// had to replace the following since many ships can have the same name
	//@NamedQuery(name="findStarship", query="SELECT s FROM Starship s WHERE s.registry= :stringToFind OR s.name= :stringToFind")
})


public class Starship implements Serializable {
	
	@Id
	@NotNull(message = "error.add.starship.invalid_registry")
	@Pattern(regexp = "^(ncc|nx)(-[a-z0-9]+)+$", flags = Pattern.Flag.CASE_INSENSITIVE, message= "error.add.starship.invalid_registry")
	@Column(unique=true)
	private String registry;
	
	@Column
	@NotNull
	private String name;
	
	@Column
	@NotNull
	private String vesselclass;
	
	
	@Column
	@NotNull
	private String status;
	
	@Column
	private Date launched;

	public String getRegistry() {
		return registry;
	}

	public void setRegistry(String registry) {
		this.registry = registry.toLowerCase();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	public String getVesselclass() {
		return vesselclass;
	}

	public void setVesselclass(String vesselclass) {
		this.vesselclass = vesselclass.toLowerCase();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status.toLowerCase();
	}

	public Date getLaunched() {
		return launched;
	}

	public void setLaunched(Date launched) {
		this.launched = launched;
	}
}