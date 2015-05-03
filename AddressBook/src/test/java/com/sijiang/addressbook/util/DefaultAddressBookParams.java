package com.sijiang.addressbook.util;

import com.sijiang.addressbook.model.Account.AccountStatus;
import com.sijiang.addressbook.model.Address.AddressType;

public interface DefaultAddressBookParams {
	public static final String DEFAULT_USERNAME = "123@email.com";
	public static final String DEFAULT_PASSWORD = "aaaBBB";
	public static final AccountStatus DEFAULT_STATUS = AccountStatus.OPEN;
	
	public static final int DEFAULT_AGE = 20;
	public static final String DEFAULT_FIRST_NAME = "Siyu";
	public static final String DEFAULT_LAST_NAME = "Jiang";
	public static final String DEFAULT_STREET_NAME = "10th St";
	public static final String DEFAULT_CITY = "Seattle";
	public static final String DEFAULT_COUNTRY = "USA";
	public static final String DEFAULT_POSTAL_CODE = "98000";
	public static final AddressType DEFAULT_ADDRESS_TYPE = AddressType.RESIDENTIAL;
	
	public static final String DEFAULT_EMAIL_AS_STRING = "123@email.com";
	public static final int DEFAULT_AREA_CODE = 206;
	public static final int DEFAULT_PREFIX = 000;
	public static final int DEFAULT_LINE_NUMBER = 0000;
	
	public static final int DEFAULT_PERSON_ID = 1;
	public static final int DEFAULT_ADDRESS_BOOK_ID = 1;
}
