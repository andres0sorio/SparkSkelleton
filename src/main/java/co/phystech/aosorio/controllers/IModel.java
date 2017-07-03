/**
 * 
 */
package co.phystech.aosorio.controllers;

import java.util.List;
import java.util.UUID;

import co.phystech.aosorio.models.User;

/**
 * @author AOSORIO
 *
 */
public interface IModel {

	
	UUID createUser(String username, 
			String role, 
			String password);

	boolean getUser(String username);
	
	boolean updateUser(String username);
	
	boolean deleteUser(String username);

	List<User> getAllUsers();
	
	
}
