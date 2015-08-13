package com.sijiang.addressbook.dao;

import java.util.List;

import com.sijiang.addressbook.model.Address;
import com.sijiang.addressbook.model.Email;
import com.sijiang.addressbook.model.Person;
import com.sijiang.addressbook.model.PhoneNumber;

public interface PersonDAO {
	 Person findByPersonId(int personId);

	void addPersonIntoExistingAccount(Person newPerson, String username);

	void addEmailToPerson(Email defaultEmail, String firstName, String lastName);
	
	void addAddressToPerson(Address defaultAddress, String firstName, String lastName);

	void addPhoneNumberToPerson(PhoneNumber defaultPhoneNumber, String firstName, String lastName);

	List<Person> findPersonsByLastnameAndFirstname(String firstName,
			String lastName);
}
