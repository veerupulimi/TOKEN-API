/* COPYRIGHT (C) 2016 LTS. All Rights Reserved. */

package com.lts.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author veeru
 *
 */
@Configuration
public class PropertySourcesConfig {

    private static final Resource[] PROPERTIES_RESOURCES = new ClassPathResource[]{
            new ClassPathResource("sample.properties"),
    };

    public static class DefaultConfig {
        @Bean
        public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
            pspc.setLocations(PROPERTIES_RESOURCES);
            return pspc;
        }
    }

}