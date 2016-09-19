/* COPYRIGHT (C) 2016 LTS. All Rights Reserved. */

package com.lts.api.manager;

import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.util.Factory;


/**
 * @author veeru
 *
 */
public class ShiroSecurityManager {
	
	//create an object of SingleObject
	private static ShiroSecurityManager instance = new ShiroSecurityManager();
	private Factory<SecurityManager> factory;
	private SecurityManager securityManager;
	
	//make the constructor private so that this class cannot be
	//instantiated
	private ShiroSecurityManager(){
		factory = new IniSecurityManagerFactory();
		securityManager = factory.getInstance();
	}
	
	//Get the only object available
	public static ShiroSecurityManager getInstance(){
		if(instance == null){
			instance = new ShiroSecurityManager();
		}
		return instance;
	}
	
	public SecurityManager getSecurityManager(){
		return securityManager;
	}
}