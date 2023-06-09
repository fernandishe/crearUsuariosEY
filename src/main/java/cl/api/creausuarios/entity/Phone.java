package cl.api.creausuarios.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String number;
	private String citycode;
	private String contrycode;

	public String getNumber() {
	return number;
	}

	public void setNumber(String number) {
	this.number = number;
	}

	public String getCitycode() {
	return citycode;
	}

	public void setCitycode(String citycode) {
	this.citycode = citycode;
	}

	public String getContrycode() {
	return contrycode;
	}

	public void setContrycode(String contrycode) {
	this.contrycode = contrycode;
	}

}
