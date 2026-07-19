package com.vn.vnptit.Oneapp_Com.common;


import java.time.Instant;
import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;


@Configuration
@Service

public class JwtService {

	public static String getUsernameFromToken(String token) {

		DecodedJWT jwt = JWT.decode(token);
		return jwt.getClaim("user_name").as(String.class);
	}

	public static Boolean validateTokenLogin(String token) {

		return !isTokenExpired(token);
	}

	public static Boolean validateAuthToken(String authtoken) {
		String token = authtoken.split(" ")[1];
		return validateTokenLogin(token);
	}

	private static Boolean isTokenExpired(String token) {
		DecodedJWT jwt = JWT.decode(token);
		Date expDate = jwt.getExpiresAt();
		return expDate.before(Date.from(Instant.now()));
	}

	public static String getIdentifyFromToken(String token) {
		DecodedJWT jwt = JWT.decode(token);
		return jwt.getClaim("identify").as(String.class);
	}

	public static String getMaNhanVienFromToken(String token) {
		DecodedJWT jwt = JWT.decode(token);
		return jwt.getClaim("ma_nv").as(String.class);
	}

	public static String getPhanVungFromToken(String token) {
		DecodedJWT jwt = JWT.decode(token);
		return jwt.getClaim("id_tinhthanh").as(String.class);
	}

}