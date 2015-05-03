package com.sijiang.addressbook.dao;

import java.util.List;

import com.sijiang.addressbook.model.Address;
import com.sijiang.addressbook.model.Email;
import com.sijiang.addressbook.model.Person;
import com.sijiang.addressbook.model.PhoneNumber;

public interface PersonDAO {
	 Person findByPersonId(int personId);

	void addPersonIntoExistingAccount(Person newPerson, String username);

	void addEmailToPerson(Email defaultEmail, int personId);
	
	void addAddressToPerson(Address defaultAddress, int personId);

	void addPhoneNumberToPerson(PhoneNumber defaultPhoneNumber, int personId);

	List<Person> findPersonsByLastnameAndFirstname(String firstName,
			String lastName);
}
