/* COPYRIGHT (C) 2016 LTS. All Rights Reserved. */

package com.lts.api.auth;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lts.api.manager.ShiroSecurityManager;
import com.lts.api.utils.PathConstants;
import com.lts.core.config.ConfigurationConstants;
import com.lts.core.context.ExecutionContext;
import com.lts.core.dto.BaseResponseDTO;
import com.lts.core.redis.RedisManager;
import com.lts.core.redis.SerializeUtils;

/**
 * @author veeru
 *
 */
@RestController
@RequestMapping(value = PathConstants.SECURITY_BASE_PATH)
public class AuthenticationResource {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthenticationResource.class);
	
	@RequestMapping(value = PathConstants.AUTHENTICATION, method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces ={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<BaseResponseDTO> authenticateUser(@RequestBody UserVO credentials) {
		
		BaseResponseDTO baseResponse = null;
		ExecutionContext executionContext;
		
		String userName = credentials.getName();
        String passWord = credentials.getPwd();
        new ShiroAuthService().testAuth(userName,passWord);
        
//        AuthenticationToken token = 	new UsernamePasswordToken(userName, passWord);
//        Subject currentUser = SecurityUtils.getSubject();
//        //currentUser.logout();
//        
//        if (!currentUser.isAuthenticated()) {
//			try {
//				currentUser.login(token);
//				
//				// Token Creation 
//				String authToken = currentUser.getSession().getId().toString();
//				
//				executionContext = new ExecutionContext();
//				
//				executionContext.setLastAccessedTimestamp(System.currentTimeMillis());
//				executionContext.setSecurityToken(authToken);
//				executionContext.setUserName(userName);
//				//executionContext.setClientIPAddress(request.getRemoteAddr());
//				executionContext.setSessionTimeOut(ConfigurationConstants.SESSION_TIMEOUT);
//				// cache.addUserToKenToCache(authToken, executionContext);
//				RedisManager rm = RedisManager.getInstance();
//				//rm.init();
//				rm.set(authToken.getBytes(), SerializeUtils.serialize(executionContext), 120);
//			
//				
//				baseResponse = new BaseResponseDTO();				
//				baseResponse.setUserName(userName);
//				baseResponse.setDataBean(executionContext);
//				baseResponse.setSuccess(true);
//				baseResponse.setTempToken(authToken);
//				
//				
//				
//			} catch (UnknownAccountException uae) {
//				baseResponse.setSuccess(false);
//				baseResponse.setErrorMessage("There is no user with username of "+ token.getPrincipal());
//				
//			} catch (IncorrectCredentialsException ice) {
//				baseResponse.setSuccess(false);
//				baseResponse.setErrorMessage("Password for account "+ token.getPrincipal()+ " was incorrect!");
//			} catch (LockedAccountException lae) {
//				baseResponse.setSuccess(false);
//				baseResponse.setErrorMessage("The account for username " + token.getPrincipal()	+ " is locked.  " + "Please contact your administrator to unlock it.");
//			}
//		} else {
//			System.out.println("already logged in");
//			//return true; // already logged in
//		}
		
		
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = PathConstants.SIGNOUT, method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces ={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<BaseResponseDTO> logoutUser() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		BaseResponseDTO brd = new BaseResponseDTO();
		brd.setSuccess(false);
		return new ResponseEntity<>(brd, HttpStatus.OK);	
		
	}
		

}
