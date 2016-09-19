/* COPYRIGHT (C) 2016 LTS. All Rights Reserved. */

package com.lts.api.exceptions;

/**
 * @author veeru
 *
 */
public final class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) { super(message); }

	public String getCode() { return "RESOURCE_NOT_FOUND"; }
}
