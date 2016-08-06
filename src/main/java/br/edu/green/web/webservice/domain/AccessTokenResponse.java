package br.edu.green.web.webservice.domain;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessTokenResponse implements Serializable {

	private static final long serialVersionUID = -6858035597470859637L;

	@JsonProperty("scope")
	private String scope;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("expires_in")
	private String expiresIn;

	@JsonProperty("access_token")
	private String accessToken;

	public String getScope() {
		return scope;
	}

	public String getTokenType() {
		return tokenType;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public String getAccessToken() {
		return accessToken;
	}
}