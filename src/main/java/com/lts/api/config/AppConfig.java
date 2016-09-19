/* COPYRIGHT (C) 2016 LTS. All Rights Reserved. */

package com.lts.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author veeru
 *
 */
@Configuration
@ComponentScan(basePackages = "com.lts.api.*, com.lts.core.*")
public class AppConfig {
}
