package com.collegegroup.personaldiary.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class CommonConfigProperties {

	@Autowired
	private Environment environment;
	
	public String getConfigValue(String configKey) {
		return environment.getProperty(configKey);
	}
	
}
