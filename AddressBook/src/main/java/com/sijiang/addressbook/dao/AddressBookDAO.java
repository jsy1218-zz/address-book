package com.sijiang.addressbook.dao;

import com.sijiang.addressbook.model.AddressBook;

public interface AddressBookDAO {
	public AddressBook findByAddressBookId(int addressBookId);
}
