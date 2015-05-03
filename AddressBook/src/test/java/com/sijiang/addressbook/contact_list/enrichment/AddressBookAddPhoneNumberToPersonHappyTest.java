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

import com.sijiang.addressbook.model.PhoneNumber;
import com.sijiang.addressbook.repo.AddressBookRepository;
import com.sijiang.addressbook.repo.impl.AddressBookRepositoryImpl;
import com.sijiang.addressbook.repository_config.JDBCDriverConfig;
import com.sijiang.addressbook.util.DefaultAddressBookParams;

public class AddressBookAddPhoneNumberToPersonHappyTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressBookAddEmailToPersonHappyTest.class);
	
	public static PhoneNumber DEFAULT_PHONE_NUMBER;

	@BeforeClass
	public static void setUp() {
		
		DEFAULT_PHONE_NUMBER = new PhoneNumber.PhoneNumberBuilder(
				DefaultAddressBookParams.DEFAULT_AREA_CODE, DefaultAddressBookParams.DEFAULT_PREFIX, DefaultAddressBookParams.DEFAULT_LINE_NUMBER)
				.buildPhoneNumber();
		Collection<PhoneNumber> defaultPhoneNumbers = new ArrayList<PhoneNumber>();
		defaultPhoneNumbers.add(DEFAULT_PHONE_NUMBER);

	}

	@Test
	public void runAddressBookAddEmailToPersonHappyTest()
			throws JsonGenerationException, JsonMappingException, IOException {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				JDBCDriverConfig.class);

		try {
			AddressBookRepository addressBookRepo = (AddressBookRepositoryImpl) context
					.getBean("com.sijiang.addressbook.repo.impl.AddressBookRepositoryImpl");
						
			addressBookRepo.addPhoneNumberToPerson(DEFAULT_PHONE_NUMBER, 1);
			
		} finally {
			((AnnotationConfigApplicationContext) context).close();
		}
	}

}
