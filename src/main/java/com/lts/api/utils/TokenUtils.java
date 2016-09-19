/* COPYRIGHT (C) 2016 LTS. All Rights Reserved. */

package com.lts.api.utils;

import javax.servlet.http.HttpServletRequest;

import com.lts.core.context.ExecutionContext;


/**
 * @author veeru
 *
 */
public class TokenUtils {
	
	public String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
		
		String authToken = httpRequest.getHeader("X_AUTH_TOKEN");
		return authToken;
		
	}
	
	public static boolean validateToken(String authToken, ExecutionContext context) {
		
		Long sessionTimeOut = context.getSessionTimeOut();
		sessionTimeOut = 1000L * 60 * sessionTimeOut;
		/* Token validation */
		if (context.getSecurityToken() == null || !(context.getSecurityToken().equals(authToken))) {
			return false;
		}
		/* Check for session timeout */
		if ((System.currentTimeMillis() - context.getLastAccessedTimestamp()) > sessionTimeOut) {
			return false;
		}
		return true;
	}

}
