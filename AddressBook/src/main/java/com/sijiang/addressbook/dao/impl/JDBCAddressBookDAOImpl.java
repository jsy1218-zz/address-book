package com.sijiang.addressbook.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.sijiang.addressbook.dao.AddressBookDAO;
import com.sijiang.addressbook.model.Address;
import com.sijiang.addressbook.model.AddressBook;
import com.sijiang.addressbook.model.Email;
import com.sijiang.addressbook.model.Person;
import com.sijiang.addressbook.model.PhoneNumber;

@Component("com.sijiang.addressbook.dao.impl.JDBCAddressBookDAOImpl")
public class JDBCAddressBookDAOImpl implements AddressBookDAO {
	private static final String FIND_BY_ADDRESS_ID = "SELECT age "
			+ " first_name, last_name"
			+ " FROM person, address_book"
			+ " WHERE address_book.address_book_id = ? "
			+ " AND address_book.address_book_id = person.address_book_id ";
	
	private JdbcTemplate JDBCTemplate;

	@Resource(name = "JDBCTemplate")
	public final void setJDBCTemplate(JdbcTemplate JDBCTemplate) {
		this.JDBCTemplate = JDBCTemplate;
	}
	
	@Override
	public AddressBook findByAddressBookId(int addressBookId) {
		List<Person> personsByLastnameAndFirstname = this.JDBCTemplate
				.query(FIND_BY_ADDRESS_ID, 
						new Object[] { addressBookId }, 
						new FindAddressBookRowMapper());
		
		AddressBook resultAddressBook = new AddressBook(personsByLastnameAndFirstname);
	
		return resultAddressBook;
	}
	
	private class FindAddressBookRowMapper implements RowMapper<Person> {
		
		// TODO: Should use factory pattern to de-dup the code
		@Override
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
			int personId = rs.getInt("person_id");
			int age = rs.getInt("age");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			
			Collection<Email> emails = new ArrayList<Email>();
			Collection<Address> addresses = new ArrayList<Address>();
			Collection<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
			
			Person person = new Person(emails, age, firstName, lastName,
					addresses, phoneNumbers);
			person.setPersonId(personId);
			
			return person;
		}
	}
}
