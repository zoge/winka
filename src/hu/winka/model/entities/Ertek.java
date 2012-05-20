package hu.winka.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ertek {
	
	@Id  @GeneratedValue
	Long id;
	
	private String hrsz;
	
	private String cim;

}
