package com.sijiang.addressbook.dao.impl;

import org.springframework.stereotype.Component;

import com.sijiang.addressbook.dao.AddressBookDAO;
import com.sijiang.addressbook.model.AddressBook;

@Component("com.sijiang.addressbook.dao.impl.JDBCAddressBookDAOImpl")
public class JDBCAddressBookDAOImpl implements AddressBookDAO {

	@Override
	public AddressBook findByAddressBookId(int addressBookId) {
		// TODO Auto-generated method stub
		return null;
	}

}
