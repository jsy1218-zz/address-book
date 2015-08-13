package com.sijiang.addressbook.repo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.sijiang.addressbook.dao.AccountDAO;
import com.sijiang.addressbook.dao.AddressBookDAO;
import com.sijiang.addressbook.dao.PersonDAO;
import com.sijiang.addressbook.model.Account;
import com.sijiang.addressbook.model.Address;
import com.sijiang.addressbook.model.AddressBook;
import com.sijiang.addressbook.model.Email;
import com.sijiang.addressbook.model.Person;
import com.sijiang.addressbook.model.PhoneNumber;
import com.sijiang.addressbook.repo.AddressBookRepository;

@Repository("com.sijiang.addressbook.repo.impl.AddressBookRepositoryImpl")
public class AddressBookRepositoryImpl implements AddressBookRepository {
	private AddressBookDAO addressBookDAO;
	private PersonDAO personDAO;
	private AccountDAO accountDAO;
 
	@Autowired
	@Qualifier("com.sijiang.addressbook.dao.impl.JDBCAddressBookDAOImpl")
	public void setAddressBookDAO(AddressBookDAO addressBookDAO) {
		this.addressBookDAO = addressBookDAO;
	}
	
	@Autowired
	@Qualifier("com.sijiang.addressbook.dao.impl.JDBCPersonDAOImpl")
	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Autowired
	@Qualifier("com.sijiang.addressbook.dao.impl.JDBCAccountDAOImpl")
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	@Override
	public void closeAccount(Account closingAccount) {
		this.accountDAO.closeAccount(closingAccount);
	}

	@Override
	public void openAccount(Account newAccount) {
		this.accountDAO.openAccount(newAccount);
	}

	@Override 
	public Account findAccountByUsername(String username) {
		return this.accountDAO.findAccountByUsername(username);
	}
	
	@Override
	public AddressBook findByAddressBookId(int addressBookId) {
		AddressBook addressBook = this.addressBookDAO.findByAddressBookId(addressBookId);
		
	    for (Person person : addressBook.getPersons()) {
	    		person = findByPersonId(person.getPersonId());
	    }
	    
	    return addressBook;
	}

	@Override
	public Person findByPersonId(int personId) {
		return this.personDAO.findByPersonId(personId);
	} 
	
	@Override
	public List<Person> findPersonsByLastnameAndFirstname(String firstName,
			String lastName) {
		
		return this.personDAO.
				findPersonsByLastnameAndFirstname(firstName, lastName);
	}

	@Override
	public void addPersonIntoExistingAccount(Person defaultPerson, String username) {
		this.personDAO.addPersonIntoExistingAccount(defaultPerson, username);
	}

	@Override
	public void addEmailToPerson(Email defaultEmail, String firstName,
			String lastName) {
		this.personDAO.addEmailToPerson(defaultEmail, firstName, lastName);
	}

	@Override
	public void addPhoneNumberToPerson(PhoneNumber defaultPhoneNumber, String firstName,
			String lastName) {
		this.personDAO.addPhoneNumberToPerson(defaultPhoneNumber, firstName, lastName);
	}
	
	@Override
	public void addAddressToPerson(Address defaultAddress, String firstName,
			String lastName) {
		this.personDAO.addAddressToPerson(defaultAddress, firstName, lastName);
	}
}
