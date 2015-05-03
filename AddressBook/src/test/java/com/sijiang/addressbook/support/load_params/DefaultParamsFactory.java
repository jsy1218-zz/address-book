package com.sijiang.addressbook.support.load_params;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import com.sijiang.addressbook.model.Account;
import com.sijiang.addressbook.model.Account.AccountStatus;
import com.sijiang.addressbook.model.Address;
import com.sijiang.addressbook.model.Address.AddressType;
import com.sijiang.addressbook.model.Email;
import com.sijiang.addressbook.model.Person;
import com.sijiang.addressbook.model.PhoneNumber;

@PropertySource("classpath:default_params.properties")
public final class DefaultParamsFactory {

	@Value("${account.username}")
	public String DEFAULT_USERNAME;
	@Value("${account.password}")
	public String DEFAULT_PASSWORD;
	@Value("${account.status}")
	public AccountStatus DEFAULT_STATUS;
	@Value("${person.age}")
	public int DEFAULT_AGE;
	@Value("${person.first_name}")
	public String DEFAULT_FIRST_NAME;
	@Value("${person.last_name}")
	public String DEFAULT_LAST_NAME;
	@Value("${address.street_name}")
	public String DEFAULT_STREET_NAME;
	@Value("${address.city}")
	public String DEFAULT_CITY;
	@Value("${address.country}")
	public String DEFAULT_COUNTRY;
	@Value("${address.postal_code}")
	public String DEFAULT_POSTAL_CODE;
	@Value("${address.address_type}")
	public AddressType DEFAULT_ADDRESS_TYPE;
	@Value("${person.email}")
	public String DEFAULT_EMAIL_AS_STRING;
	@Value("${phone_number.area_code}")
	public int DEFAULT_AREA_CODE;
	@Value("${phone_number.prefix}")
	public int DEFAULT_PREFIX;
	@Value("${phone_number.line_number}")
	public int DEFAULT_LINE_NUMBER;

	public static Person DEFAULT_PERSON;
	public static Email DEFAULT_EMAIL;
	public static Address DEFAULT_ADDRESS;
	public static PhoneNumber DEFAULT_PHONE_NUMBER;
	public static Account DEFAULT_ACCOUNT;

	private DefaultParamsFactory() {
		throw new UnsupportedOperationException();
	}

	public void init() {
		DEFAULT_EMAIL = new Email(DEFAULT_EMAIL_AS_STRING);
		Collection<Email> defaultEmails = new ArrayList<Email>();
		defaultEmails.add(DEFAULT_EMAIL);

		DEFAULT_ADDRESS = new Address(DEFAULT_STREET_NAME, DEFAULT_CITY,
				DEFAULT_COUNTRY, DEFAULT_POSTAL_CODE, DEFAULT_ADDRESS_TYPE);
		Collection<Address> defaultAddresses = new ArrayList<Address>();
		defaultAddresses.add(DEFAULT_ADDRESS);

		DEFAULT_PHONE_NUMBER = new PhoneNumber.PhoneNumberBuilder(
				DEFAULT_AREA_CODE, DEFAULT_PREFIX, DEFAULT_LINE_NUMBER)
				.buildPhoneNumber();
		Collection<PhoneNumber> defaultPhoneNumbers = new ArrayList<PhoneNumber>();
		defaultPhoneNumbers.add(DEFAULT_PHONE_NUMBER);

		DEFAULT_PERSON = new Person(defaultEmails, DEFAULT_AGE,
				DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, defaultAddresses,
				defaultPhoneNumbers);

		DEFAULT_ACCOUNT = new Account.AccountBuilder(DEFAULT_USERNAME,
				DEFAULT_PASSWORD, DEFAULT_STATUS).buildAccount();
	}
}
