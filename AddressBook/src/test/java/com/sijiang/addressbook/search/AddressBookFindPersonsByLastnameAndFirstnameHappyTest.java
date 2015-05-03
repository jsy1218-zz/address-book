package com.sijiang.addressbook.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sijiang.addressbook.model.Address;
import com.sijiang.addressbook.model.Email;
import com.sijiang.addressbook.model.Person;
import com.sijiang.addressbook.model.PhoneNumber;
import com.sijiang.addressbook.repository_config.JDBCDriverConfig;
import com.sijiang.addressbook.service.AddressBookManager;
import com.sijiang.addressbook.service.impl.AddressBookManagerImpl;
import com.sijiang.addressbook.service_config.ServiceConfig;
import com.sijiang.addressbook.util.DefaultAddressBookParams;

public class AddressBookFindPersonsByLastnameAndFirstnameHappyTest {

	public static Person DEFAULT_PERSON;
	public static Email DEFAULT_EMAIL;
	public static Address DEFAULT_ADDRESS;
	public static PhoneNumber DEFAULT_PHONE_NUMBER;

	@BeforeClass
	public static void setUp() {
		DEFAULT_EMAIL = new Email(DefaultAddressBookParams.DEFAULT_EMAIL_AS_STRING);
		Collection<Email> defaultEmails = new ArrayList<Email>();
		defaultEmails.add(DEFAULT_EMAIL);

		DEFAULT_ADDRESS = new Address(DefaultAddressBookParams.DEFAULT_STREET_NAME, DefaultAddressBookParams.DEFAULT_CITY,
				DefaultAddressBookParams.DEFAULT_COUNTRY, DefaultAddressBookParams.DEFAULT_POSTAL_CODE, DefaultAddressBookParams.DEFAULT_ADDRESS_TYPE);
		Collection<Address> defaultAddresses = new ArrayList<Address>();
		defaultAddresses.add(DEFAULT_ADDRESS);

		DEFAULT_PHONE_NUMBER = new PhoneNumber.PhoneNumberBuilder(
				DefaultAddressBookParams.DEFAULT_AREA_CODE, DefaultAddressBookParams.DEFAULT_PREFIX, DefaultAddressBookParams.DEFAULT_LINE_NUMBER)
				.buildPhoneNumber();
		Collection<PhoneNumber> defaultPhoneNumbers = new ArrayList<PhoneNumber>();
		defaultPhoneNumbers.add(DEFAULT_PHONE_NUMBER);

		DEFAULT_PERSON = new Person(defaultEmails, DefaultAddressBookParams.DEFAULT_AGE,
				DefaultAddressBookParams.DEFAULT_FIRST_NAME, DefaultAddressBookParams.DEFAULT_LAST_NAME, defaultAddresses,
				defaultPhoneNumbers);

	}

	@Test
	public void runAddressBookFindPersonsByLastnameAndFirstnameHappyTest()
			throws JsonGenerationException, JsonMappingException, IOException {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				JDBCDriverConfig.class, ServiceConfig.class);

		try {
			AddressBookManager addressBookRepo = context
					.getBean("com.sijiang.addressbook.service.impl.AddressBookManagerImpl", AddressBookManagerImpl.class);

			List<Person> persons = addressBookRepo.findPersonsByLastnameAndFirstname(DEFAULT_PERSON.getFirstName(), 
					DEFAULT_PERSON.getLastName());
			
			for (Person person : persons) {
				System.out.println(person.toString());
			}
			
		} finally {
			((AnnotationConfigApplicationContext) context).close();
		}
	}

}
