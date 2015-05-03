package com.sijiang.addressbook.webservice_config;

import javax.annotation.Resource;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.sijiang.addressbook.service.AddressBookManager;
import com.sijiang.webservice.soap.message_format.OpenAccountRequest;

@Endpoint
public class AddressBookOpenAccountSoapEndpoint {
	private static final String OPEN_ACCOUNT_NAMESPACE_URI = "http://www.addressbook.com/open_account";

	private AddressBookManager addressBookManager;

	
	public AddressBookManager getAddressBookManager() {
		return addressBookManager;
	}

	@Resource(name = "com.sijiang.addressbook.service.impl.AddressBookManagerImpl")
	public void setAddressBookManager(AddressBookManager addressBookManager) {
		this.addressBookManager = addressBookManager;
	}

}
