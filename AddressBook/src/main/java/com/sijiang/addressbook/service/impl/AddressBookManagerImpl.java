package com.sijiang.addressbook.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijiang.addressbook.model.Account;
import com.sijiang.addressbook.model.Account.AccountStatus;
import com.sijiang.addressbook.model.Address.AddressType;
import com.sijiang.addressbook.model.Address;
import com.sijiang.addressbook.model.Email;
import com.sijiang.addressbook.model.Person;
import com.sijiang.addressbook.model.PhoneNumber;
import com.sijiang.addressbook.model.PhoneNumber.PhoneNumberBuilder;
import com.sijiang.addressbook.repo.AddressBookRepository;
import com.sijiang.addressbook.service.AddressBookManager;

@Service("com.sijiang.addressbook.service.impl.AddressBookManagerImpl")
public class AddressBookManagerImpl implements AddressBookManager {
	private AddressBookRepository addressBookRepo;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void openAccount(String username, String password) {
		AccountStatus accountStatus = AccountStatus.OPEN;
		
		Account newAccount = new Account.AccountBuilder(username, password,
				accountStatus).buildAccount();
		
		addressBookRepo.openAccount(newAccount);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void closeAccount(String username) {
		Account targetAccount = 
				addressBookRepo.findAccountByUsername(username);
		Account closingAccount =  new Account.
				AccountBuilder(targetAccount.getUserName(), targetAccount.getPassWord(),
						AccountStatus.CLOSED).CreateDate(targetAccount.getCreateDate())
						.LastLoginDate(targetAccount.getLastLoginDate()).AddressBooks(targetAccount.getAddressBooks())
						.buildAccount();
		
		addressBookRepo.closeAccount(closingAccount);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPerson(int age, String firstName, String lastName,
			String username) {
		Collection<Email> emails = new ArrayList<Email>();
		Collection<Address> addresses = new ArrayList<Address>();
		Collection<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();

		Person newPerson = new Person(emails, age, firstName, lastName, addresses, phoneNumbers);
		
		addressBookRepo.addPersonIntoExistingAccount(newPerson, username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Person> findPersonsByLastnameAndFirstname(String firstName,
			String lastName) {
		
		return this.addressBookRepo.
				findPersonsByLastnameAndFirstname(firstName, lastName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addEmailToPerson(int personId, String email) {
		Email defaultEmail = new Email(email);
		
		this.addressBookRepo.addEmailToPerson(defaultEmail, personId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPhoneNumberToPerson(int personId, int areaCode, int prefix,
			int lineNumber, int... countryCode) {
		PhoneNumberBuilder defaultPhoneNumberBuilder = new PhoneNumber.PhoneNumberBuilder(
				areaCode, prefix, lineNumber);
		
		if (countryCode.length == 1) {
			defaultPhoneNumberBuilder = defaultPhoneNumberBuilder.countryCode(countryCode[0]);
		}
		
		PhoneNumber defaultPhoneNumber = defaultPhoneNumberBuilder.buildPhoneNumber();
		
		this.addressBookRepo.addPhoneNumberToPerson(defaultPhoneNumber, personId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAddressToPerson(int personId, String streetName,
			String city, String country, String postalCode, String addressType) {
		Address defaultAddress = new Address(streetName,
				 city,  country,  postalCode, AddressType.valueOf(addressType));
		
		this.addressBookRepo.addAddressToPerson(defaultAddress, personId);
	}
	
	public AddressBookRepository getAddressBookRepo() {
		return addressBookRepo;
	}

	@Resource(name = "com.sijiang.addressbook.repo.impl.AddressBookRepositoryImpl")
	public void setAddressBookRepo(AddressBookRepository addressBookRepo) {
		this.addressBookRepo = addressBookRepo;
	}

	@Override
	public Account findAccountByUsername(String username) {
		Account targetAccount = 
				addressBookRepo.findAccountByUsername(username);	
		
		return targetAccount;
	}
}
