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

	void addEmailToPerson(String firstName,
			String lastName, String email);

	void addPhoneNumberToPerson(String firstName,
			String lastName, int areaCode, int prefix,
			int lineNumber, int... countryCode);

	void addAddressToPerson(String firstName,
			String lastName, String streetName, String city,
			String country, int postalCode, String addressType);

	Account findAccountByUsername(String username);

}
