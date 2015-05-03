package com.sijiang.addressbook.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sijiang.addressbook.model.Address;
import com.sijiang.addressbook.model.Email;
import com.sijiang.addressbook.model.Person;
import com.sijiang.addressbook.model.PhoneNumber;
import com.sijiang.addressbook.repo.AddressBookRepository;
import com.sijiang.addressbook.repo.impl.AddressBookRepositoryImpl;
import com.sijiang.addressbook.repository_config.JDBCDriverConfig;
import com.sijiang.addressbook.util.DefaultAddressBookParams;

public class AddressBookFindByPersonIdHappyTest {
	
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
	public void runAddressBookOpenAccountHappyTest()
			throws JsonGenerationException, JsonMappingException, IOException {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				JDBCDriverConfig.class);

		try {
			AddressBookRepository addressBookRepo = context
					.getBean("com.sijiang.addressbook.repo.impl.AddressBookRepositoryImpl", AddressBookRepositoryImpl.class);

			Person person = addressBookRepo.findByPersonId(DefaultAddressBookParams.DEFAULT_PERSON_ID);
			
		} finally {
			((AnnotationConfigApplicationContext) context).close();
		}
	}

}
