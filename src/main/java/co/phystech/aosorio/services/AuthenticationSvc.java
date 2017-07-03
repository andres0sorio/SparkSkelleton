/**
 * 
 */
package co.phystech.aosorio.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.phystech.aosorio.exceptions.WrongPasswordException;
import co.phystech.aosorio.exceptions.WrongUserException;
import co.phystech.aosorio.models.NewUserPayload;
import spark.Request;
import spark.Response;

/**
 * @author AOSORIO
 *
 */
public class AuthenticationSvc {
	
	private final static Logger slf4jLogger = LoggerFactory.getLogger(AuthenticationSvc.class);

	public static Object doLogin(Request pRequest, Response pResponse) {

		Object response = null;
		
		slf4jLogger.info("login body: " + pRequest.body());
		
		ObjectMapper mapper = new ObjectMapper();
				
		try {
			
			NewUserPayload data =  mapper.readValue(pRequest.body(), NewUserPayload.class);

			IAuthentication authMethod = new AuthenticationJWT();
		
			authMethod.doAuthentication(data.getUsername(), data.getPassword());
			response = authMethod.getToken();
			
			return response;
			
		} catch ( WrongUserException ex) {
			
		} catch ( WrongPasswordException ex ) {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
		
	}
	

}
