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

import com.sijiang.addressbook.model.Email;
import com.sijiang.addressbook.repo.AddressBookRepository;
import com.sijiang.addressbook.repo.impl.AddressBookRepositoryImpl;
import com.sijiang.addressbook.repository_config.JDBCDriverConfig;
import com.sijiang.addressbook.util.DefaultAddressBookParams;

public class AddressBookAddEmailToPersonHappyTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressBookAddEmailToPersonHappyTest.class);

	public static Email DEFAULT_EMAIL;

	@BeforeClass
	public static void setUp() {
		DEFAULT_EMAIL = new Email(DefaultAddressBookParams.DEFAULT_EMAIL_AS_STRING);
		Collection<Email> defaultEmails = new ArrayList<Email>();
		defaultEmails.add(DEFAULT_EMAIL);
	}

	@Test
	public void runAddressBookAddEmailToPersonHappyTest()
			throws JsonGenerationException, JsonMappingException, IOException {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				JDBCDriverConfig.class);

		try {
			AddressBookRepository addressBookRepo = (AddressBookRepositoryImpl) context
					.getBean("com.sijiang.addressbook.repo.impl.AddressBookRepositoryImpl");
						
			addressBookRepo.addEmailToPerson(DEFAULT_EMAIL, 1);
			
		} finally {
			((AnnotationConfigApplicationContext) context).close();
		}
	}

}
