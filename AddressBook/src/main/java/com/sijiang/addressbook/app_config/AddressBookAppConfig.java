package com.sijiang.addressbook.app_config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.sijiang.addressbook.service_config", 
				"com.sijiang.addressbook.webservice_config",
				"com.sijiang.addressbook.repository_config"})
public class AddressBookAppConfig {
	
}
