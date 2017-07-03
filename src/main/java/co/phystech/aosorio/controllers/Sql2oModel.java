/**
 * 
 */
package co.phystech.aosorio.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import co.phystech.aosorio.models.User;
import co.phystech.aosorio.services.IUuidGenerator;
import co.phystech.aosorio.services.RandomUuidGenerator;

/**
 * @author AOSORIO
 *
 */
public class Sql2oModel implements IModel {

	private final static Logger slf4jLogger = LoggerFactory.getLogger(Sql2oModel.class);

	private Sql2o sql2o;
	private IUuidGenerator uuidGenerator;

	public Sql2oModel(Sql2o sql2o) {
		this.sql2o = sql2o;
		uuidGenerator = new RandomUuidGenerator();
	}

	@Override
	public UUID createUser(String username, String role, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getUser(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
