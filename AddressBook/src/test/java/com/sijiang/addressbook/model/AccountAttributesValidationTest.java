package com.sijiang.addressbook.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sijiang.addressbook.model.Account.AccountStatus;
import com.sijiang.addressbook.model.Address.AddressType;

public class AccountAttributesValidationTest {
	private static Validator validator;
	private static final String VALID_USERNAME = "123@gmail.com";
	private static final String VALID_PASSWORD = "aaaBBB";
	private static final String VALID_CITY = "Seattle";
	private static final String VALID_COUNTRY = "USA";
	private static final String VALID_STREET_NAME = "10th St";
	private static final int VALID_POSTAL_CODE = 123456;
	private static final String VALID_EMAIL = "123@gmail.com";
	private static final int VALID_COUNTRY_CODE = 01;
	private static final int VALID_AREA_CODE = 201;
	private static final int VALID_LINE_NUMBER = 2123;
	private static final int VALID_PREFIX = 231;
	private static final int VALID_AGE = 15;
	private static final String VALID_FIRST_NAME = "hen";
	private static final String VALID_LAST_NAME = "Joh";
	private static final String INVALID_USERNAME = null;

	// TODO: add exception handling in the annotation fashion
	// TODO: add tests to see if the expected exception gets thrown
	// TODO: add the equality contract test

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void createAccountWithNoAddressBookHappy() {
		Account acct = new Account.AccountBuilder(VALID_USERNAME,
				VALID_PASSWORD, AccountStatus.OPEN).buildAccount();

		Set<ConstraintViolation<Account>> constraintViolations = validator
				.validate(acct);

		assertEquals(0, constraintViolations.size());
		assertEquals(acct.getUserName(), VALID_USERNAME);
		assertEquals(acct.getPassWord(), VALID_PASSWORD);
		assertEquals(acct.getAccountStatus(), AccountStatus.OPEN);
	}

	@Test
	public void createAccountWithNoAddressBookInvalidUsername() {
		Account acct = new Account.AccountBuilder(INVALID_USERNAME,
				VALID_PASSWORD, AccountStatus.OPEN).buildAccount();
		Set<ConstraintViolation<Account>> constraintViolations = validator
				.validate(acct);
		assertEquals(1, constraintViolations.size());
	}

	@Test
	public void createAccountWithEmptyAddressBookListHappy() {
		Collection<AddressBook> addressBooks = new ArrayList<AddressBook>();
		Account acct = new Account.AccountBuilder(VALID_USERNAME,
				VALID_PASSWORD, AccountStatus.OPEN).AddressBooks(addressBooks)
				.buildAccount();

		Set<ConstraintViolation<Account>> constraintViolations = validator
				.validate(acct);

		assertEquals(0, constraintViolations.size());
		assertEquals(acct.getUserName(), VALID_USERNAME);
		assertEquals(acct.getPassWord(), VALID_PASSWORD);
		assertEquals(acct.getAccountStatus(), AccountStatus.OPEN);
		assertEquals(acct.getAddressBooks().isEmpty(), true);
	}

	@Test
	public void createAccountWithOneAddressBookEmptyPersonListHappy() {
		Collection<Person> persons = new ArrayList<Person>();
		AddressBook addressBook1 = new AddressBook(persons);
		Collection<AddressBook> addressBooks = new ArrayList<AddressBook>();
		addressBooks.add(addressBook1);

		Account acct = new Account.AccountBuilder(VALID_USERNAME,
				VALID_PASSWORD, AccountStatus.OPEN).AddressBooks(addressBooks)
				.buildAccount();

		Set<ConstraintViolation<Account>> constraintViolations = validator
				.validate(acct);

		assertEquals(0, constraintViolations.size());
		assertEquals(0, constraintViolations.size());
		assertEquals(acct.getUserName(), VALID_USERNAME);
		assertEquals(acct.getPassWord(), VALID_PASSWORD);
		assertEquals(acct.getAccountStatus(), AccountStatus.OPEN);
		assertEquals(acct.getAddressBooks().size(), 1);
		assertEquals((acct.getAddressBooks()).size(), 1);
		assertEquals(((ArrayList<AddressBook>) acct.getAddressBooks()).get(0)
				.getPersons().size(), 0);
	}

	@Test
	public void createAccountWithOneAddressBookOnePersonHappy() {
		Email email1 = new Email(VALID_EMAIL);
		Collection<Email> emails = new ArrayList<Email>();
		emails.add(email1);

		Address address1 = new Address(VALID_STREET_NAME, VALID_CITY,
				VALID_COUNTRY, VALID_POSTAL_CODE, AddressType.RESIDENTIAL);
		Collection<Address> addresses = new ArrayList<Address>();
		addresses.add(address1);

		PhoneNumber phoneNumber1 = new PhoneNumber.PhoneNumberBuilder(
				VALID_AREA_CODE, VALID_PREFIX, VALID_LINE_NUMBER).countryCode(
				VALID_COUNTRY_CODE).buildPhoneNumber();
		Collection<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
		phoneNumbers.add(phoneNumber1);

		Person person1 = new Person(emails, VALID_AGE, VALID_FIRST_NAME,
				VALID_LAST_NAME, addresses, phoneNumbers);
		Collection<Person> persons = new ArrayList<Person>();
		persons.add(person1);

		AddressBook addressBook1 = new AddressBook(persons);
		Collection<AddressBook> addressBooks = new ArrayList<AddressBook>();
		addressBooks.add(addressBook1);

		Account acct = new Account.AccountBuilder(VALID_USERNAME,
				VALID_PASSWORD, AccountStatus.OPEN).AddressBooks(addressBooks)
				.buildAccount();

		Set<ConstraintViolation<Account>> constraintViolations = validator
				.validate(acct);

		assertEquals(0, constraintViolations.size());
		assertEquals(0, constraintViolations.size());
		assertEquals(0, constraintViolations.size());
		assertEquals(acct.getUserName(), VALID_USERNAME);
		assertEquals(acct.getPassWord(), VALID_PASSWORD);
		assertEquals(acct.getAccountStatus(), AccountStatus.OPEN);
		assertEquals(acct.getAddressBooks().size(), 1);
		assertEquals((acct.getAddressBooks()).size(), 1);
		assertEquals(((ArrayList<AddressBook>) acct.getAddressBooks()).get(0)
				.getPersons().size(), 1);
		assertEquals(
				((ArrayList<Person>) ((ArrayList<AddressBook>) acct.getAddressBooks())
						.get(0).getPersons()).get(0).getAddresses().size(), 1);
		assertEquals(
				((ArrayList<Address>) ((ArrayList<Person>) ((ArrayList<AddressBook>) acct
						.getAddressBooks()).get(0).getPersons()).get(0)
						.getAddresses()).get(0).getCity(), VALID_CITY);
		assertEquals(
				((ArrayList<Address>) ((ArrayList<Person>) ((ArrayList<AddressBook>) acct
						.getAddressBooks()).get(0).getPersons()).get(0)
						.getAddresses()).get(0).getCountry(), VALID_COUNTRY);
		assertEquals(
				((ArrayList<Address>) ((ArrayList<Person>) ((ArrayList<AddressBook>) acct
						.getAddressBooks()).get(0).getPersons()).get(0)
						.getAddresses()).get(0).getPostalCode(),
				VALID_POSTAL_CODE);
		assertEquals(
				((ArrayList<Address>) ((ArrayList<Person>) ((ArrayList<AddressBook>) acct
						.getAddressBooks()).get(0).getPersons()).get(0)
						.getAddresses()).get(0).getStreetName(),
				VALID_STREET_NAME);
		assertEquals(
				((ArrayList<Address>) ((ArrayList<Person>) ((ArrayList<AddressBook>) acct
						.getAddressBooks()).get(0).getPersons()).get(0)
						.getAddresses()).get(0).getAddressType(),
				AddressType.RESIDENTIAL);

		assertEquals(
				((ArrayList<Person>) ((ArrayList<AddressBook>) acct
						.getAddressBooks()).get(0).getPersons()).get(0)
						.getPhoneNumbers().size(),
				1);
		assertEquals(
				((ArrayList<PhoneNumber>) ((ArrayList<Person>) ((ArrayList<AddressBook>) acct
						.getAddressBooks()).get(0).getPersons()).get(0)
						.getPhoneNumbers()).get(0).getAreaCode(),
				VALID_AREA_CODE);
		assertEquals(
				((ArrayList<PhoneNumber>) ((ArrayList<Person>) ((ArrayList<AddressBook>) acct
						.getAddressBooks()).get(0).getPersons()).get(0)
						.getPhoneNumbers()).get(0).getCountryCode(),
				VALID_COUNTRY_CODE);
		assertEquals(
				((ArrayList<PhoneNumber>) ((ArrayList<Person>) ((ArrayList<AddressBook>) acct
						.getAddressBooks()).get(0).getPersons()).get(0)
						.getPhoneNumbers()).get(0).getLineNumber(),
				VALID_LINE_NUMBER);
		assertEquals(
				((ArrayList<PhoneNumber>) ((ArrayList<Person>) ((ArrayList<AddressBook>) acct
						.getAddressBooks()).get(0).getPersons()).get(0)
						.getPhoneNumbers()).get(0).getPreFix(), VALID_PREFIX);

		assertEquals(
				((ArrayList<Person>) ((ArrayList<AddressBook>) acct.getAddressBooks())
						.get(0).getPersons()).get(0).getEmails().size(), 1);
		assertEquals(
				((ArrayList<Email>) ((ArrayList<Person>) ((ArrayList<AddressBook>) acct
						.getAddressBooks()).get(0).getPersons()).get(0)
						.getEmails()).get(0).toString(), VALID_EMAIL);
	}
}
