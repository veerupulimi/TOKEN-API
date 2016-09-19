/* COPYRIGHT (C) 2016 LTS. All Rights Reserved. */

package com.lts.api.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.GenericFilterBean;


/**
 * @author veeru
 *
 */
public class AuthTokenProcessingFilter extends GenericFilterBean {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
//		Subject currentUser = SecurityUtils.getSubject();
//		
//		if (!currentUser.isAuthenticated()) {
//			BaseResponseDTO brd = new BaseResponseDTO();
//			HttpStatus status = HttpStatus.UNAUTHORIZED;//Session Expired or Invalid Token
//			brd.setErrorCode(status.value()+"");
//			brd.setErrorMessage("Your session has expired");
//			brd.setSuccess(false);
//			ObjectMapper mapper = new ObjectMapper();
//		    
//		    response.getWriter().write(mapper.writeValueAsString(brd));
//		    return;
//		    
//		}
		chain.doFilter(request, response);
		
	}

}
