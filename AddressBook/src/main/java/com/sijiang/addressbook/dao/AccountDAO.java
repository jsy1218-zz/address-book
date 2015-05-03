package com.sijiang.addressbook.dao;

import com.sijiang.addressbook.model.Account;

public interface AccountDAO {
	void openAccount(Account newAccount);
	
	void closeAccount(Account closingAccount);
	
	Account findAccountByUsername(String username);
}

