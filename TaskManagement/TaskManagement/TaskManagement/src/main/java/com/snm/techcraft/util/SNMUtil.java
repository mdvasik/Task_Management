package com.snm.techcraft.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SNMUtil {

	public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
		return new ResponseEntity<String>("{\"message\":\"" + responseMessage + "\"}", httpStatus);
	}

	public static SNMResponse makeResponse(Object obj, String message, int code) {
		SNMResponse SNMResponse = new SNMResponse();
		SNMResponse.setMessage(message);
		SNMResponse.setStatusCode(code);
		SNMResponse.setData(obj);
		return SNMResponse;
	}

}
