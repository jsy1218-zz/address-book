package com.sijiang.addressbook.contact_list.enrichment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

import com.sijiang.addressbook.model.Account;
import com.sijiang.addressbook.model.Address;
import com.sijiang.addressbook.model.Email;
import com.sijiang.addressbook.model.Person;
import com.sijiang.addressbook.model.PhoneNumber;
import com.sijiang.addressbook.repo.AddressBookRepository;
import com.sijiang.addressbook.repo.impl.AddressBookRepositoryImpl;
import com.sijiang.addressbook.repository_config.JDBCDriverConfig;
import com.sijiang.addressbook.util.DefaultAddressBookParams;

@PropertySource("classpath:src/test/java/default_params.properties")
public class AddressBookAddPersonHappyTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressBookAddPersonHappyTest.class);
	
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
			AddressBookRepository addressBookRepo = (AddressBookRepositoryImpl) context
					.getBean("com.sijiang.addressbook.repo.impl.AddressBookRepositoryImpl");
			
			// TODO: read the default user from a property file;
			// TODO: everytime before the test, we make sure that the default
			// user exists,
			// TODO: otherwise add it into table
			Account existingAccount = addressBookRepo
					.findAccountByUsername(DefaultAddressBookParams.DEFAULT_USERNAME);

			// TODO: add person into existing account test
			// TODO: encrypt and decrypt the username pw, look up online the
			// popular way to do that
			String username = existingAccount.getUserName();
			
			addressBookRepo.addPersonIntoExistingAccount(DEFAULT_PERSON, username);

		} finally {
			((AnnotationConfigApplicationContext) context).close();
		}
	}
}
