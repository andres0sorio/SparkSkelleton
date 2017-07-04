/**
 * 
 */
package co.phystech.aosorio.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Sql2o;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.phystech.aosorio.controllers.IModel;
import co.phystech.aosorio.controllers.Sql2oModel;
import co.phystech.aosorio.controllers.SqlController;
import co.phystech.aosorio.exceptions.WrongPasswordException;
import co.phystech.aosorio.exceptions.WrongUserException;
import co.phystech.aosorio.models.BackendMessage;
import co.phystech.aosorio.models.NewUserPayload;
import co.phystech.aosorio.models.User;
import co.phystech.aosorio.services.AuthenticationJWT;
import io.jsonwebtoken.SignatureException;
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
						
		pResponse.type("application/json");
		
		try {
			
			NewUserPayload data =  mapper.readValue(pRequest.body(), NewUserPayload.class);

			IAuthentication authMethod = new AuthenticationJWT();
		
			authMethod.doAuthentication(data.getUsername(), data.getPassword());
			response = authMethod.getToken();
			
			pResponse.status(200);
			return response;
			
		} catch ( WrongUserException ex) {
			pResponse.status(401);
			
		} catch ( WrongPasswordException ex ) {
			pResponse.status(401);
			
		} catch (Exception e) {
			pResponse.status(401);
			e.printStackTrace();
		}
		
		return response;
		
	}
	
	public static Object checkAccess(Request pRequest, Response pResponse) {
		
		slf4jLogger.info("login body: " + pRequest.body());
		
		BackendMessage messager = new BackendMessage();
		
		Object response = null;
		
		pResponse.type("application/json");
		
		try {
		
			String token = pRequest.headers("Authorization").split(" ")[1];
			
			Sql2o sql2o = SqlController.getInstance().getAccess();
			IModel model = new Sql2oModel(sql2o);
			
			String username = GeneralSvc.getTokenClaim(token, "sub");
			User currentUser = model.getUser(username);
			AuthenticationJWT.validateJWT( currentUser, token );
			
			response = messager.getOkMessage("valid");
			
			pResponse.status(200);
			
		} catch (SignatureException ex) {
			slf4jLogger.info(ex.getMessage());
			response = messager.getNotOkMessage("invalid");
			pResponse.status(401);
			
		} catch ( Exception ex ) {
			ex.printStackTrace();
			response = messager.getNotOkMessage("invalid");
			pResponse.status(401);
		}
		
		return response;
		
	}

}
