package br.edu.green.web.webservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EntityWrapperSingle<T> {
	@JsonProperty("usuario")
	private T authenticatePerson;

	public T getAuthenticatePerson() {
		return authenticatePerson;
	}
}
