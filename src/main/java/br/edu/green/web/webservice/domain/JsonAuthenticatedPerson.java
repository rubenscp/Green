package br.edu.green.web.webservice.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("usuario")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonAuthenticatedPerson implements Serializable {
	private static final long serialVersionUID = 1167028721673838428L;

	@JsonProperty("login")
	private String login;

	@JsonProperty("matricula")
	private String registration;

	@JsonProperty("nome")
	private String name;

	@JsonProperty("email")
	private String email;

	@JsonProperty("lotacaoId")
	private long workplaceId;

	@JsonProperty("lotacaoNome")
	private String workplaceName;

	@JsonProperty("lotacaoSigla")
	private String workplaceInitials;

	@JsonProperty("indicadorSituacaoGeral")
	private short generalSituationIndicator;

	@JsonProperty("tipo")
	private short internalExternalPerson;

	public String getLogin() {
		return login;
	}

	public String getRegistration() {
		return registration;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public long getWorkplaceId() {
		return workplaceId;
	}

	public String getWorkplaceName() {
		return workplaceName;
	}

	public String getWorkplaceInitials() {
		return workplaceInitials;
	}

	public short getGeneralSituationIndicator() {
		return generalSituationIndicator;
	}

	public short getinternalExternalPerson() {
		return internalExternalPerson;
	}

	@Override
	public String toString() {
		return "JsonAuthenticatedPerson { login: " + this.login
				+ ", registration: " + this.registration + ", name: "
				+ this.name + ", email: " + this.email + ", workplaceId: "
				+ this.workplaceId + ", workplaceName: " + this.workplaceName
				+ ", workplaceInitials: " + this.workplaceInitials
				+ ", generalSituationIndicator: "
				+ this.generalSituationIndicator + ", internalExternalPerson: "
				+ this.internalExternalPerson + " }";
	}
}
