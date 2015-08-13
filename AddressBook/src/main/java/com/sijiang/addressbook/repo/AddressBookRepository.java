package com.sijiang.addressbook.repo;

import java.util.List;

import com.sijiang.addressbook.model.Account;
import com.sijiang.addressbook.model.Address;
import com.sijiang.addressbook.model.AddressBook;
import com.sijiang.addressbook.model.Email;
import com.sijiang.addressbook.model.Person;
import com.sijiang.addressbook.model.PhoneNumber;

public interface AddressBookRepository {

	void closeAccount(Account closingAccount);

	void addAddressToPerson(Address defaultAddress, String firstName,
			String lastName);

	void addPhoneNumberToPerson(PhoneNumber defaultPhoneNumber, String firstName,
			String lastName);

	void addEmailToPerson(Email defaultEmail, String firstName,
			String lastName);

	void addPersonIntoExistingAccount(Person defaultPerson, String username);

	List<Person> findPersonsByLastnameAndFirstname(String firstName,
			String lastName);

	Person findByPersonId(int personId);

	AddressBook findByAddressBookId(int addressBookId);

	Account findAccountByUsername(String username);

	void openAccount(Account newAccount);

}
