package com.sijiang.addressbook.service.add_person;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sijiang.addressbook.model.Person;
import com.sijiang.addressbook.service.AddressBookManager;
import com.sijiang.addressbook.service.impl.AddressBookManagerImpl;
import com.sijiang.addressbook.service_config.ServiceConfig;
import com.sijiang.addressbook.util.DefaultAddressBookParams;

public class AddressBookAddPersonServiceHappyTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressBookAddPersonServiceHappyTest.class);

	@Test
	public void runAddressBookCloseAccountServiceHappyTest()
			throws JsonGenerationException, JsonMappingException, IOException {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				ServiceConfig.class, com.sijiang.addressbook.repository_config.JDBCDriverConfig.class);

		try {
			AddressBookManager addressBookManager = context
					.getBean("com.sijiang.addressbook.service.impl.AddressBookManagerImpl", AddressBookManagerImpl.class);
			
			int age = DefaultAddressBookParams.DEFAULT_AGE;
			String firstName = DefaultAddressBookParams.DEFAULT_FIRST_NAME;
			String lastName = DefaultAddressBookParams.DEFAULT_LAST_NAME;
			String username = DefaultAddressBookParams.DEFAULT_USERNAME;
			
			addressBookManager.addPerson(age, firstName, lastName, username);
		
			List<Person> persons = addressBookManager.findPersonsByLastnameAndFirstname(firstName, lastName);
			
			LOGGER.info(persons.get(0).toString());
			
		} finally {
			((AnnotationConfigApplicationContext) context).close();
		}
	}
}
