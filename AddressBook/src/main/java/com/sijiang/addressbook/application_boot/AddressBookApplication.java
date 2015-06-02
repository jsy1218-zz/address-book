package com.sijiang.addressbook.application_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.sijiang.addressbook.app_config")
public class AddressBookApplication {
	public static void main(String[] args) {
		SpringApplication.run(AddressBookApplication.class, args);
	}
}
