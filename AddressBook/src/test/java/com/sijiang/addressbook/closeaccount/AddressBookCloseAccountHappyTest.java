package com.sijiang.addressbook.closeaccount;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DuplicateKeyException;

import com.sijiang.addressbook.model.Account;
import com.sijiang.addressbook.model.Account.AccountStatus;
import com.sijiang.addressbook.repo.AddressBookRepository;
import com.sijiang.addressbook.repo.impl.AddressBookRepositoryImpl;
import com.sijiang.addressbook.repository_config.JDBCDriverConfig;

public class AddressBookCloseAccountHappyTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressBookCloseAccountHappyTest.class);

	@Test
	public void runAddressBookOpenAccountHappyTest()
			throws JsonGenerationException, JsonMappingException, IOException {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				JDBCDriverConfig.class);

		try {
			AddressBookRepository addressBookRepo = (AddressBookRepositoryImpl) context
					.getBean("com.sijiang.addressbook.repo.impl.AddressBookRepositoryImpl");
			String username = UUID.randomUUID().toString() + "@email.com";
			String password = "aaaBBB";
			AccountStatus newAccount = AccountStatus.OPEN;

			Account expectedAccount = new Account.AccountBuilder(username, password,
					newAccount).buildAccount();

			addressBookRepo.openAccount(expectedAccount);
			LOGGER.info(expectedAccount.toString()
					+ " has been inserted into Account table");
			addressBookRepo.closeAccount(expectedAccount);
			Account actualAccount = addressBookRepo
					.findAccountByUsername(expectedAccount.getUserName());

			Assert.assertEquals(expectedAccount, actualAccount);
			Assert.assertEquals(AccountStatus.CLOSED, actualAccount.getAccountStatus());
			
		} catch (DuplicateKeyException dupEx) {
			LOGGER.error(dupEx.getMessage());
			throw new DuplicateKeyException(dupEx.getMessage());
		} finally {
			((AnnotationConfigApplicationContext) context).close();
		}
	}

}
