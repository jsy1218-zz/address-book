package com.sijiang.addressbook.service.contact_list.enrichment;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sijiang.addressbook.appconfig.JDBCDriverConfig;
import com.sijiang.addressbook.appconfig.ServiceConfig;
import com.sijiang.addressbook.service.AddressBookManager;
import com.sijiang.addressbook.service.impl.AddressBookManagerImpl;
import com.sijiang.addressbook.util.DefaultAddressBookParams;

public class AddressBookAddEmailToPersonServiceHappyTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressBookAddEmailToPersonServiceHappyTest.class);



	@Test
	public void runAddressBookAddEmailToPersonServiceHappyTest()
			throws JsonGenerationException, JsonMappingException, IOException {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				ServiceConfig.class, JDBCDriverConfig.class);

		try {
			AddressBookManager addressBookManager = context
					.getBean("com.sijiang.addressbook.service.impl.AddressBookManagerImpl", AddressBookManagerImpl.class);
			
			addressBookManager.addEmailToPerson(DefaultAddressBookParams.DEFAULT_PERSON_ID, 
					DefaultAddressBookParams.DEFAULT_EMAIL_AS_STRING);
			
		} finally {
			((AnnotationConfigApplicationContext) context).close();
		}
	}


}
