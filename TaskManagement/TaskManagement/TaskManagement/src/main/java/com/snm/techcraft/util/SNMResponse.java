package com.snm.techcraft.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SNMResponse {

	private String message;

	private int statusCode;

	private Object data;
}
