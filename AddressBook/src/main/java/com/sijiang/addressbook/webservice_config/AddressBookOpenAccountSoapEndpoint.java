package com.sijiang.addressbook.webservice_config;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.sijiang.addressbook.service.AddressBookManager;

@Endpoint
public class AddressBookOpenAccountSoapEndpoint {
	private static final Logger LOGGER = LoggerFactory.
			getLogger(AddressBookOpenAccountSoapEndpoint.class);
	
	private static final String OPEN_ACCOUNT_NAMESPACE_URI = "http://www.addressbook.com/open_account";

	private AddressBookManager addressBookManager;

	
	public AddressBookManager getAddressBookManager() {
		return addressBookManager;
	}

	@Resource(name = "com.sijiang.addressbook.service.impl.AddressBookManagerImpl")
	public void setAddressBookManager(AddressBookManager addressBookManager) {
		this.addressBookManager = addressBookManager;
	}

	@PayloadRoot(namespace = OPEN_ACCOUNT_NAMESPACE_URI, localPart = "OpenAccountRequest")
	@ResponsePayload
	public OpenAccountResponse getCountry(@RequestPayload OpenAccountRequest request) {
		String username = request.getUsername();
		String password = request.getPassord();
		
		String result = "success";
		
		try {
			addressBookManager.openAccount(username, password);
		} catch (Exception e) {
			result = "failure";
			LOGGER.error("Error when opening account. ", e);
		}
		
		OpenAccountResponse response = new OpenAccountResponse();
		response.setOpenAccountStatus(result);
		
		return response;
	}
}
