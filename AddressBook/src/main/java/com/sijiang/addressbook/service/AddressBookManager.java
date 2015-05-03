package com.sijiang.addressbook.service;

import java.util.List;

import com.sijiang.addressbook.model.Account;
import com.sijiang.addressbook.model.Person;

public interface AddressBookManager {

	void openAccount(String username, String password);

	void closeAccount(String username);

	void addPerson(int age, String firstName, String lastName, String username);

	List<Person> findPersonsByLastnameAndFirstname(String firstName,
			String lastName);

	void addEmailToPerson(int personId, String email);

	void addPhoneNumberToPerson(int personId, int areaCode, int prefix,
			int lineNumber, int... countryCode);

	void addAddressToPerson(int personId, String streetName, String city,
			String country, String postalCode, String addressType);

	Account findAccountByUsername(String username);

}
