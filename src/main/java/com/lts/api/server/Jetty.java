/* COPYRIGHT (C) 2016 LTS. All Rights Reserved. */

package com.lts.api.server;

import java.io.IOException;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.lts.api.manager.ShiroSecurityManager;
import com.lts.api.security.filter.AuthTokenProcessingFilter;
import com.lts.api.utils.ConfigConstants;
import com.lts.core.manager.AppContextManager;

/**
 * @author veeru
 *
 */
public class Jetty {

    private static final Logger LOGGER = LoggerFactory.getLogger(Jetty.class);
    private Server jettyServer; 
    private ServletContextHandler servletContextHandler; 
    
    public static void main(String[] args) throws Exception {
    	 AbstractApplicationContext context = AppContextManager
 				.getAppContextManager().getContext();
    	// SecurityUtils.setSecurityManager(ShiroSecurityManager.getInstance().getSecurityManager());
      new Jetty().startJetty(ConfigConstants.PORT);
       
    }
    /**
     * 
     * @param port
     * @throws Exception
     */
    private void startJetty(int port) throws Exception {
    	
        LOGGER.debug("Starting server at port {}", port);
        
        jettyServer = new Server(port);        
        jettyServer.setHandler(getServletContextHandler());        
        addRuntimeShutdownHook(jettyServer);        
        
        jettyServer.start();
        
        LOGGER.info("Server started at port {}", port);
        jettyServer.join();
    }
    /**
     * 
     * @return
     * @throws IOException
     */
    private ServletContextHandler getServletContextHandler() throws IOException {
    	servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS); // SESSIONS requerido para JSP 
    	servletContextHandler.setErrorHandler(null);
        
    	servletContextHandler.setResourceBase(new ClassPathResource(ConfigConstants.WEBAPP_DIRECTORY).getURI().toString());        
    	servletContextHandler.setContextPath(ConfigConstants.CONTEXT_PATH);
        
    	// This is used for allowing access to different domains/ports.
//        FilterHolder filterHolder = new FilterHolder(AuthTokenProcessingFilter.class);
//        filterHolder.setInitParameter("allowedOrigins", "*");
//        filterHolder.setInitParameter("allowedMethods", "GET, POST");
//        servletContextHandler.addFilter(filterHolder, "/*", null);
    	
    	
    	
    	servletContextHandler.setInitParameter( "shiroConfigLocations", "classpath:shiro.ini" );
    	servletContextHandler.addEventListener( new EnvironmentLoaderListener() );

    	servletContextHandler.addFilter( AuthTokenProcessingFilter.class, "/api/resource/*", EnumSet.of( DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR ) );

        // JSP
        //contextHandler.setClassLoader(Thread.currentThread().getContextClassLoader()); // Necesario para cargar JspServlet
        //contextHandler.addServlet(JspServlet.class, "*.jsp");
        
        // Spring
        WebApplicationContext webAppContext = getWebApplicationContext();
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webAppContext);
        ServletHolder springServletHolder = new ServletHolder("mvc-dispatcher", dispatcherServlet);
        servletContextHandler.addServlet(springServletHolder, ConfigConstants.MAPPING_URL);
        servletContextHandler.addEventListener(new ContextLoaderListener(webAppContext));
        
        return servletContextHandler;
    }
    /**
     * 
     * @return
     */
    private static WebApplicationContext getWebApplicationContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(ConfigConstants.CONFIG_LOCATION_PACKAGE);
        return context;
    }
    /**
     * 
     * @param server
     */
    private static void addRuntimeShutdownHook(final Server server) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if (server.isStarted()) {
                	server.setStopAtShutdown(true);
                    try {
                    	server.stop();
                    } catch (Exception e) {
                        System.out.println("Error while stopping jetty server: " + e.getMessage());
                        LOGGER.error("Error while stopping jetty server: " + e.getMessage(), e);
                    }
                }
            }
        }));
	}

}

