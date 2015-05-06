package com.sijiang.addressbook.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.sijiang.addressbook.dao.PersonDAO;
import com.sijiang.addressbook.model.Address;
import com.sijiang.addressbook.model.Address.AddressType;
import com.sijiang.addressbook.model.Email;
import com.sijiang.addressbook.model.Person;
import com.sijiang.addressbook.model.PhoneNumber;

@Component("com.sijiang.addressbook.dao.impl.JDBCPersonDAOImpl")
public class JDBCpersonDAOImpl implements PersonDAO {
	
	private static final String ADD_PERSON = "insert into person "
			+ " (address_book_id, age, first_name, last_name) "
			+ " select address_book_id, ?, ?, ? from address_book, "
			+ " account where username = ? ";

	private static final String ADD_EMAIL = "insert into email "
			+ " (person_id, email) " + " values (?, ?) ";

	private static final String ADD_PHONE_NUMBER = "insert into phone_number "
			+ " (person_id, country_code, area_code, prefix, line_number) "
			+ " values (?, ?, ?, ?, ?) ";
	
	private static final String ADD_ADDRESS = "insert into address "
			+ " (street_name, city, country, postal_code, address_type, person_id) "
			+ " values (?, ?, ?, ?, ?, ?) ";
	
	private static final String FIND_PERSONS_BY_LASTNAME_And_FIRSTNAME = " select "
			+ " per.person_id, per.age, per.first_name, per.last_name, "
			+ " e.email_id, e.person_id, e.email, "
			+ " a.address_id, a.person_id, a.street_name, a.city, a.country, a.postal_code, a.address_type, "
			+ " pn.phone_number_id, pn.person_id, pn.country_code, pn.area_code, pn.prefix, pn.line_number "
			+ " from person per, email e, address a, phone_number pn "
			+ " where per.first_name = ? and per.last_name = ? ";

	private static final String FIND_PERSON = "select age, first_name, "
			+ "	last_name " + " from person " + " where person_id = ? ";

	private static final String FIND_PHONE_NUMBER = "select country_code, area_code, "
			+ "	prefix, line_number "
			+ " from phone_number "
			+ " where person_id = ? ";

	private static final String FIND_EMAIL = "select email " + " from email "
			+ " where person_id = ? ";

	private static final String FIND_ADDRESS = "select street_name, city, country, "
			+ " postal_code, address_type "
			+ " from address "
			+ " where person_id = ? ";

	private JdbcTemplate JDBCTemplate;

	@Resource(name = "JDBCTemplate")
	public final void setJDBCTemplate(JdbcTemplate JDBCTemplate) {
		this.JDBCTemplate = JDBCTemplate;
	}

	@Override
	public List<Person> findPersonsByLastnameAndFirstname(String firstName,
			String lastName) {
		
		List<Person> personsByLastnameAndFirstname = this.JDBCTemplate
				.query(FIND_PERSONS_BY_LASTNAME_And_FIRSTNAME, new Object[] { firstName, lastName}, 
						new FindPersonByLastnameAndFirstnameRowMapper());
		
		Map<Integer, Person> personsByPersonId = new HashMap<Integer, Person>();
		
		// design deficit: should not use iterator to get the object instance
		// there must be wise ways
		for (Person personAsRowMapper : personsByLastnameAndFirstname) {
			int personId = personAsRowMapper.getPersonId();
			
			Collection<Email> emails = new ArrayList<Email>();
			Email email = personAsRowMapper.getEmails().iterator().next();
			emails.add(email);
			
			int age = personAsRowMapper.getAge();
		    firstName = personAsRowMapper.getFirstName();
			lastName = personAsRowMapper.getLastName();
					
			Collection<Address> addresses = new ArrayList<Address>();
			Address address = personAsRowMapper.getAddresses().iterator().next();
			addresses.add(address);
			
			Collection<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
			PhoneNumber phoneNumber = personAsRowMapper.getPhoneNumbers().iterator().next();
			phoneNumbers.add(phoneNumber);
			
			if (personsByPersonId.containsKey(personId)) {
				Person tempForStorage = personsByPersonId.get(personId);
				
				Collection<Address> obsoleteAddresses = tempForStorage.getAddresses();
				obsoleteAddresses.addAll(addresses);
				Collection<Address> dedupAddresses = new HashSet<Address>(obsoleteAddresses);
				tempForStorage.getAddresses().removeAll(obsoleteAddresses);
				tempForStorage.getAddresses().addAll(dedupAddresses);
				
				Collection<Email> obsoleteEmails = tempForStorage.getEmails();
				obsoleteEmails.addAll(emails);
				Collection<Email> dedupEmails = new HashSet<Email>(tempForStorage.getEmails());
				tempForStorage.getEmails().removeAll(obsoleteEmails);
				tempForStorage.getEmails().addAll(dedupEmails);
				
				Collection<PhoneNumber> obsoletePhoneNumbers = tempForStorage.getPhoneNumbers();
				obsoletePhoneNumbers.addAll(phoneNumbers);
				Collection<PhoneNumber> dedupPhoneNumbers = new HashSet<PhoneNumber>(tempForStorage.getPhoneNumbers());
				tempForStorage.getPhoneNumbers().removeAll(obsoletePhoneNumbers);
				tempForStorage.getPhoneNumbers().addAll(obsoletePhoneNumbers);
				
			} else {
				Person tempForStorage = new Person(emails, age, 
						firstName, lastName, addresses, phoneNumbers);

				personsByPersonId.put(personId, tempForStorage);
			}
		}
		
		return new ArrayList<Person>(personsByPersonId.values());
	}
	
	@Override
	public Person findByPersonId(int personId) {

		Person person = this.JDBCTemplate.
				queryForObject(FIND_PERSON, new Object[] {personId}, new FindPersonRowMapper());

		List<PhoneNumber> phoneNumbersForPerson = this.JDBCTemplate
				.query(FIND_PHONE_NUMBER, new Object[] { personId }, new FindPhoneNumberRowMapper());

		List<Email> emailsForPerson = this.JDBCTemplate.query(FIND_EMAIL,
				new Object[] { personId }, new FindEmailRowMapper());

		List<Address> addressesForPerson = this.JDBCTemplate.query(FIND_ADDRESS,
				new Object[] { personId }, new FindAddressRowMapper());
	
		person.getPhoneNumbers().addAll(phoneNumbersForPerson);
		person.getEmails().addAll(emailsForPerson);
		person.getAddresses().addAll(addressesForPerson);
		
		return person;
	}

	@Override
	public void addPersonIntoExistingAccount(Person newPerson, String username) {

		this.JDBCTemplate.update(ADD_PERSON,
				new AddPersonPreparedStatementSetter(newPerson, username));
	}

	@Override
	public void addEmailToPerson(Email defaultEmail, int personId) {

		this.JDBCTemplate.update(ADD_EMAIL,
				new AddEmailPreparedStatementSetter(defaultEmail, personId));
	}

	@Override
	public void addPhoneNumberToPerson(PhoneNumber defaultPhoneNumber,
			int personId) {

		this.JDBCTemplate.update(ADD_PHONE_NUMBER,
				new AddPhoneNumberPreparedStatementSetter(defaultPhoneNumber,
						personId));
	}

	// TODO: make a factory for the PreparedStatementSetter
	private static class AddPersonPreparedStatementSetter implements
			PreparedStatementSetter {
		private final int age;
		private final String firstName;
		private final String lastName;
		private final String username;

		@SuppressWarnings("unused")
		private AddPersonPreparedStatementSetter() {
			throw new UnsupportedOperationException();
		}

		public AddPersonPreparedStatementSetter(Person newPerson,
				String username) {
			this.age = newPerson.getAge();
			this.firstName = newPerson.getFirstName();
			this.lastName = newPerson.getLastName();
			this.username = username;
		}

		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.age);
			ps.setString(2, this.firstName);
			ps.setString(3, this.lastName);
			ps.setString(4, this.username);
		}
	}

	private static class AddEmailPreparedStatementSetter implements
			PreparedStatementSetter {
		private final int personId;
		private final String defaultEmail;

		public AddEmailPreparedStatementSetter(Email defaultEmail, int personId) {
			this.personId = personId;
			this.defaultEmail = defaultEmail.toString();
		}

		@SuppressWarnings("unused")
		private AddEmailPreparedStatementSetter() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.personId);
			ps.setString(2, this.defaultEmail);
		}
	}

	private static class AddPhoneNumberPreparedStatementSetter implements
			PreparedStatementSetter {
		private final int personId;
		private final int countryCode;
		private final int areaCode;
		private final int prefix;
		private final int lineNumber;

		@SuppressWarnings("unused")
		private AddPhoneNumberPreparedStatementSetter() {
			throw new UnsupportedOperationException();
		}

		public AddPhoneNumberPreparedStatementSetter(
				PhoneNumber defaultPhoneNumber, int personId) {
			this.personId = personId;
			this.countryCode = defaultPhoneNumber.getCountryCode();
			this.areaCode = defaultPhoneNumber.getAreaCode();
			this.prefix = defaultPhoneNumber.getPreFix();
			this.lineNumber = defaultPhoneNumber.getLineNumber();
		}

		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setInt(1, this.personId);
			ps.setInt(2, this.countryCode);
			ps.setInt(3, this.areaCode);
			ps.setInt(4, this.prefix);
			ps.setInt(5, this.lineNumber);
		}
	}
	
	@Override
	public void addAddressToPerson(Address defaultAddress, int personId) {
		this.JDBCTemplate
				.update(ADD_ADDRESS, new AddAddressPreparedStatementSetter(
						defaultAddress, personId));
	}

	private static class AddAddressPreparedStatementSetter implements
			PreparedStatementSetter {
		private final String streetName;
		private final String city;
		private final String country;
		private final String postalCode;
		private final String addressType;
		private final int personId;

		@SuppressWarnings("unused")
		private AddAddressPreparedStatementSetter() {
			throw new UnsupportedOperationException();
		}

		public AddAddressPreparedStatementSetter(Address defaultAddress, int personId) {
			this.streetName = defaultAddress.getStreetName();
			this.city = defaultAddress.getCity();
			this.country = defaultAddress.getCountry();
			this.postalCode = defaultAddress.getPostalCode();
			this.addressType = defaultAddress.getAddressType().toString();
			this.personId = personId;
		}

		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.streetName);
			ps.setString(2, this.city);
			ps.setString(3, this.country);
			ps.setString(4, this.postalCode);
			ps.setString(5, this.addressType);
			ps.setInt(6, this.personId);
		}
	}

	private class FindPersonByLastnameAndFirstnameRowMapper implements RowMapper<Person> {
		
		// TODO: Should use factory pattern to de-dup the code
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			int personId = rs.getInt("person_id");
			int age = rs.getInt("age");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			
			int emailId = rs.getInt("email_id");
			String emailAsString = rs.getString("email");
			
			int addressId = rs.getInt("address_id");
			String streetName = rs.getString("street_name");
			String city = rs.getString("city");
			String country = rs.getString("country");
			String postalCode = rs.getString("postal_code");
			AddressType addressType = AddressType.valueOf(rs.getString("address_type")); 

			int phoneNumberId = rs.getInt("phone_number_id");
			int areaCode = rs.getInt("area_code");
			int prefix = rs.getInt("prefix");
			int lineNumber = rs.getInt("line_number");
						
			Email email = new Email(emailAsString);
			email.setEmailId(emailId);
			Collection<Email> emails = new ArrayList<Email>();
			emails.add(email);
			
			Address address = new Address(streetName, city, country, postalCode, addressType);
			address.setAddressId(addressId);
			Collection<Address> addresses = new ArrayList<Address>();
			addresses.add(address);
			
			PhoneNumber phoneNumber = new PhoneNumber.
					PhoneNumberBuilder(areaCode, prefix, lineNumber).
					buildPhoneNumber();
			phoneNumber.setPhoneNumberId(phoneNumberId);
			Collection<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
			phoneNumbers.add(phoneNumber);
			
			Person person = new Person(emails, age, firstName, lastName,
					addresses, phoneNumbers);
			person.setPersonId(personId);
			
			return person;
		}
	}
	
	private class FindPersonRowMapper implements RowMapper<Person> {
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {

			int age = rs.getInt("age");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");

			Person person = new Person(new ArrayList<Email>(), age, firstName, lastName,
					new ArrayList<Address>(), new ArrayList<PhoneNumber>());

			return person;
		}
	}

	private class FindPhoneNumberRowMapper implements RowMapper<PhoneNumber> {
		@Override
		public PhoneNumber mapRow(ResultSet rs, int rowNum) throws SQLException {
			short countryCode = rs.getShort("country_code");
			short areaCode = rs.getShort("area_code");
			short prefix = rs.getShort("prefix");
			short lineNumber = rs.getShort("line_number");

			PhoneNumber phoneNumber = new PhoneNumber.PhoneNumberBuilder(areaCode,
					prefix, lineNumber).countryCode(countryCode).buildPhoneNumber();
			
			return phoneNumber;
		}
	}
	
	private class FindEmailRowMapper implements RowMapper<Email> {
		@Override
		public Email mapRow(ResultSet rs, int rowNum) throws SQLException {
			String email = rs.getString("email");

			return new Email(email);
		}
	}
	
	private class FindAddressRowMapper implements RowMapper<Address> {
		@Override
		public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
			String streetName = rs.getString("street_name");
			String city = rs.getString("city");
			String country = rs.getString("country");
			String postalCode = rs.getString("postal_code");
			String addressType = rs.getString("address_type");
			
			return new Address(streetName, city, country, postalCode, 
					Enum.valueOf(Address.AddressType.class, addressType));
		}
	}
}
