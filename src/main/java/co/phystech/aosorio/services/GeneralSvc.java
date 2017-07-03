/**
 * 
 */
package co.phystech.aosorio.services;

import java.io.IOException;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import spark.ResponseTransformer;

/**
 * @author AOSORIO
 *
 */
public class GeneralSvc {

	private final static Logger slf4jLogger = LoggerFactory.getLogger(GeneralSvc.class);

	public static String dataToJson(Object data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			// mapper.enable(SerializationFeature.INDENT_OUTPUT);
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, data);
			return sw.toString();
		} catch (IOException e) {
			throw new RuntimeException("IOException from a StringWriter?");
		}
	}

	public static ResponseTransformer json() {

		return GeneralSvc::dataToJson;
	}

	// .

	public static String getHash(String pPwd, String pSalt) {

		String result;
		String saltedPwd = pSalt + pPwd;

		byte[] digest = null;
		byte[] buffer = saltedPwd.getBytes();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.reset();
			messageDigest.update(buffer);
			digest = messageDigest.digest();
	
		} catch (NoSuchAlgorithmException ex) {
			slf4jLogger.error("Error creating hash");
		}

		result = toHexadecimal(digest);
		return result;

	}
	
	/***
	 * Converts a bytes array to a string hexadecimal values
	 * 
	 * @param digest bytes array to be converted
	 * @return String of resulting data
	 */
	private static String toHexadecimal(byte[] pDigest) {
		
		String hash = "";
		for (byte aux : pDigest) {
			int b = aux & 0xff;
			if (Integer.toHexString(b).length() == 1)
				hash += "0";
			hash += Integer.toHexString(b);
		}
		return hash;
	
	}

}
