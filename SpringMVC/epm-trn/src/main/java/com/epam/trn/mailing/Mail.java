/**
 * 
 */
package com.epam.trn.mailing;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * @author Siarhei Klimuts
 *
 */
public class Mail {
	
	private static final String API_KEY = "key-052zlryo5hfg6snyjheha9b5nc6rw563";
	private static final String DOMAIN_URL = "https://api.mailgun.net/v2/galiaf.mailgun.org/messages";
	private static final String MAIL_FROM = "epm-trn <system@epm-trn.herokuapp.com>";
	private static final String MAIL_SUBJECT_REGISTRATION = "You registered in epm-trn";
	private static final String MAIL_TEXT_REGISTRATION = "";
	
	public static Boolean sendRegistrationMessage(String mailTo, String login, String password) {
	    Client client = Client.create();
	    client.addFilter(new HTTPBasicAuthFilter("api", API_KEY));
	    WebResource webResource = client.resource(DOMAIN_URL);
	   
	    MultivaluedMapImpl formData = new MultivaluedMapImpl();
	    formData.add("from", MAIL_FROM);
	    formData.add("to", mailTo);
	    formData.add("subject", MAIL_SUBJECT_REGISTRATION);
	    formData.add("text", MAIL_TEXT_REGISTRATION + "Login: " + login + "\nPassword: " + password);
	   
	    ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
	    return response.getStatus() == 200;
	}
}
