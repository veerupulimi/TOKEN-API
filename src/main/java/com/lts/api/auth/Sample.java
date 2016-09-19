/* COPYRIGHT (C) 2016 LTS. All Rights Reserved. */

package com.lts.api.auth;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.lts.api.utils.PathConstants;
import com.lts.core.dto.BaseResponseDTO;
import com.lts.core.service.UserService;


/**
 * @author veeru
 *
 */
@RestController
@RequestMapping(value = PathConstants.RESOURCE_SECURITY_BASE_PATH)
public class Sample {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthenticationResource.class);
	@Autowired
	UserService userService;
	
	@RequestMapping(value = PathConstants.AUTHENTICATION, method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces ={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<BaseResponseDTO> testMsg() {
		
		BaseResponseDTO baseResponse = new BaseResponseDTO();
		
		baseResponse.setSuccess(true);
		baseResponse.setSuccessMessage(userService.getUser());
		
		
		return new ResponseEntity<>(baseResponse, HttpStatus.OK);
		
	}
	
	
		

}
