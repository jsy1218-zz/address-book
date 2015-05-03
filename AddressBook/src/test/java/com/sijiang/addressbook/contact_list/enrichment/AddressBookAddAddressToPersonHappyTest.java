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

import com.sijiang.addressbook.model.Address;
import com.sijiang.addressbook.repo.AddressBookRepository;
import com.sijiang.addressbook.repo.impl.AddressBookRepositoryImpl;
import com.sijiang.addressbook.repository_config.JDBCDriverConfig;
import com.sijiang.addressbook.util.DefaultAddressBookParams;

public class AddressBookAddAddressToPersonHappyTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressBookAddAddressToPersonHappyTest.class);
	
	public static Address DEFAULT_ADDRESS;

	@BeforeClass
	public static void setUp() {
		
		DEFAULT_ADDRESS = new Address(DefaultAddressBookParams.DEFAULT_STREET_NAME, DefaultAddressBookParams.DEFAULT_CITY,
				DefaultAddressBookParams.DEFAULT_COUNTRY, DefaultAddressBookParams.DEFAULT_POSTAL_CODE, DefaultAddressBookParams.DEFAULT_ADDRESS_TYPE);
		Collection<Address> defaultAddresses = new ArrayList<Address>();
		defaultAddresses.add(DEFAULT_ADDRESS);
	}
	
	@Test
	public void runAddressBookOpenAccountHappyTest()
			throws JsonGenerationException, JsonMappingException, IOException {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				JDBCDriverConfig.class);

		try {
			AddressBookRepository addressBookRepo = (AddressBookRepositoryImpl) context
					.getBean("com.sijiang.addressbook.repo.impl.AddressBookRepositoryImpl");
						
			addressBookRepo.addAddressToPerson(DEFAULT_ADDRESS, 1);

		} finally {
			((AnnotationConfigApplicationContext) context).close();
		}
	}

}
