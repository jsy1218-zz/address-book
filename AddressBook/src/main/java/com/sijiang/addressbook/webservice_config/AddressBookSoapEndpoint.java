package com.sijiang.addressbook.webservice_config;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.sijiang.addressbook.model.Account;
import com.sijiang.addressbook.model.Address;
import com.sijiang.addressbook.model.Person;
import com.sijiang.addressbook.model.PhoneNumber;
import com.sijiang.addressbook.service.AddressBookManager;

@Endpoint
public class AddressBookSoapEndpoint {
	private static final Logger LOGGER = LoggerFactory.
			getLogger(AddressBookSoapEndpoint.class);
	
	private static final String OPEN_ACCOUNT_NAMESPACE_URI = "http://addressbook.com/open_account";

	private static final String CLOSE_ACCOUNT_NAMESPACE_URI = "http://addressbook.com/close_account";

	private static final String ADD_PERSON_NAMESPACE_URI = "http://addressbook.com/add_person";
	
	private static final String FIND_PERSON_NAMESPACE_URI = "http://addressbook.com/find_person_by_lastname_and_firstname";
	
	private static final String ADD_EMAIL_NAMESPACE_URI = "http://addressbook.com/add_email";

	private static final String ADD_ADDRESS_NAMESPACE_URI = "http://addressbook.com/add_address";
	
	private static final String ADD_PHONE_NUMBER_NAMESPACE_URI = "http://addressbook.com/add_phone_number";

	private static final String FIND_ACCOUNT_NAMESPACE_URI = "http://addressbook.com/find_account";
	
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
	public OpenAccountResponse openAccount(@RequestPayload OpenAccountRequest request) {
		String username = request.getUsername();
		String password = request.getPassword();
		
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
	
	@PayloadRoot(namespace = CLOSE_ACCOUNT_NAMESPACE_URI, localPart = "CloseAccountRequest")
	@ResponsePayload
	public CloseAccountResponse closeAccount(@RequestPayload CloseAccountRequest request) {
		String username = request.getUsername();
		
		String result = "success";
		
		try {
			addressBookManager.closeAccount(username);
		} catch (Exception e) {
			result = "failure";
			LOGGER.error("Error when closing account. ", e);
		}
		
		CloseAccountResponse response = new CloseAccountResponse();
		response.setCloseAccountStatus(result);
		
		return response;
	}
	
	@PayloadRoot(namespace = ADD_PERSON_NAMESPACE_URI, localPart = "AddPersonRequest") 
	@ResponsePayload
	public AddPersonResponse addPerson(@RequestPayload AddPersonRequest request) {
		int age = request.getAge();
		String firstName = request.getFirstname();
		String lastName = request.getLastname();
		String username = request.getUsername();
		
		String result = "success";
		
		try {
			addressBookManager.addPerson(age, firstName, lastName, username);
		} catch (Exception e) {
			result = "failure";
			LOGGER.error("Error when adding person to the account. ", e);
		}
		
		AddPersonResponse response = new AddPersonResponse();
		response.setAddPersonStatus(result);
		
		return response;
	}
	
	@PayloadRoot(namespace = FIND_PERSON_NAMESPACE_URI, localPart = "FindPersonRequest")
	@ResponsePayload
	public FindPersonResponse findPerson(@RequestPayload FindPersonRequest request) {
		String firstName = request.getFirstname();
		String lastName = request.getLastname();

		String result = "success";
		
		FindPersonResponse response = new FindPersonResponse();
		response.setFindPersonStatus(result);

		try {		
			List<Person> persons = new ArrayList<Person>();
			
			persons = addressBookManager.findPersonsByLastnameAndFirstname(firstName, lastName);
			
			List<FindPersonResponse.Person> personsInXml = new ArrayList<FindPersonResponse.Person>();
			
			for (Person person : persons) {
				FindPersonResponse.Person personInXml = new FindPersonResponse.Person();
				
				personInXml.setAge(person.getAge());
				personInXml.setFirstname(person.getFirstName());
				personInXml.setLastname(person.getLastName());
				
				List<FindPersonResponse.Person.Address> addressesInXml = new ArrayList<FindPersonResponse.Person.Address>();
				
				for (Address address : person.getAddresses()) { 
					FindPersonResponse.Person.Address addressInXml = new FindPersonResponse.Person.Address();
					
					addressInXml.setCity(address.getCity());
					addressInXml.setCountry(address.getCountry());
					addressInXml.setPostalcode(address.getPostalCode());
					addressInXml.setStreetname(address.getStreetName());
					addressInXml.setAddresstype(address.getAddressType().name());
					
					addressesInXml.add(addressInXml);
				}
				
				List<FindPersonResponse.Person.PhoneNumber> phoneNumbersInXml = new ArrayList<FindPersonResponse.Person.PhoneNumber>();
				
				for (PhoneNumber phoneNumber : person.getPhoneNumbers()) {
					FindPersonResponse.Person.PhoneNumber phoneNumberInXml = new FindPersonResponse.Person.PhoneNumber();

					phoneNumberInXml.setAreacode(phoneNumber.getAreaCode());
					phoneNumberInXml.setCountrycode(phoneNumber.getCountryCode());
					phoneNumberInXml.setLinenumber(phoneNumber.getLineNumber());
					phoneNumberInXml.setPrefix(phoneNumber.getPreFix());
					
					phoneNumbersInXml.add(phoneNumberInXml);
					
					
				}
			
				for (com.sijiang.addressbook.model.Email email : person.getEmails()) {
					personInXml.getEmail().add(email.toString());
				}
				
				personsInXml.add(personInXml);
			}
			
			response.getPerson().addAll(personsInXml);
		} catch (Exception e) {
			result = "failure";
			LOGGER.error("Error when finding person by first name and last name. ", e);
		}
				
		return response;
	}
	
	@PayloadRoot(namespace = ADD_ADDRESS_NAMESPACE_URI, localPart = "AddAddressRequest")
	@ResponsePayload
	public AddAddressResponse addAddress(@RequestPayload AddAddressRequest request) {
		String addressType = request.getAddresstype();
		String city = request.getCity();
		String streetName = request.getStreetname();
		String country = request.getCountry(); 
		String firstName = request.getFirstname();
		String lastName = request.getLastname();
		int postalCode = request.getPostalcode();
		
		String result = "success";

		try {
			addressBookManager.addAddressToPerson(firstName, lastName, streetName, city, country, postalCode, addressType);
		} catch (Exception e) {
			result = "failure";
			LOGGER.error("Error when adding address to the person. ", e);
		}
		
		AddAddressResponse response = new AddAddressResponse();
		response.setAddAddressStatus(result);
		
		return response;
	}
	
	@PayloadRoot(namespace = ADD_EMAIL_NAMESPACE_URI, localPart="AddEmailRequest")
	@ResponsePayload
	public AddEmailResponse addEmail(@RequestPayload AddEmailRequest request) {
		String firstName = request.getFirstname();
		String lastName = request.getLastname();
		String email = request.getEmail();
		
		String result = "success";

		try {
			addressBookManager.addEmailToPerson(firstName, lastName, email);
		} catch (Exception e) {
			result = "failure";
			LOGGER.error("Error when adding email to the person. ", e);
		}
		
		AddEmailResponse response = new AddEmailResponse();
		response.setAddEmailStatus(result);
		
		return response;
	}
	
	@PayloadRoot(namespace = ADD_PHONE_NUMBER_NAMESPACE_URI, localPart="AddPhoneNumberRequest")
	@ResponsePayload
	public AddPhoneNumberResponse addPhoneNumber(@RequestPayload AddPhoneNumberRequest request) {
		int areaCode = request.getAreacode();
		String firstName = request.getFirstname();
		String lastName = request.getLastname();
		int lineNumber = request.getLinenumber();
		int prefix = request.getPrefix();
		Integer countryCode = request.getCountryCode();
		
		String result = "success";

		try {
			if (countryCode == null) { 
				addressBookManager.addPhoneNumberToPerson(firstName, lastName, areaCode, prefix, lineNumber);
			} else {
				addressBookManager.addPhoneNumberToPerson(firstName, lastName, areaCode, prefix, lineNumber, countryCode);	
			}
		} catch (Exception e) {
			result = "failure";
			LOGGER.error("Error when adding email to the person. ", e);
		}
		
		AddPhoneNumberResponse response = new AddPhoneNumberResponse();
		response.setAddPhoneNumberStatus(result);
		
		return response;
	}
	
	@PayloadRoot(namespace = FIND_ACCOUNT_NAMESPACE_URI, localPart="FindAccountRequest")
	@ResponsePayload
	public FindAccountResponse findAccount(@RequestPayload FindAccountRequest request) {
		String username = request.getUsername();
		
		String result = "success";
		Account account = null;
		
		try {
			account = addressBookManager.findAccountByUsername(username);
		} catch (Exception e) {
			result = "failure";
			LOGGER.error("Error when adding email to the person. ", e);
		}
		
		FindAccountResponse response = new FindAccountResponse();
		response.setFindAccountStatus(result);
		response.setUsername(account.getUserName());
		response.setLastlogindate(toXMLGregorianCalendar(account.getLastLoginDate()));
		response.setCreatedate(toXMLGregorianCalendar(account.getCreateDate()));
		
		return response;
	}
	
    /*
     * Converts java.util.Date to javax.xml.datatype.XMLGregorianCalendar
     */
    public static XMLGregorianCalendar toXMLGregorianCalendar(Date date){
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        XMLGregorianCalendar xmlCalendar = null;
        try {
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        } catch (DatatypeConfigurationException ex) {
        		LOGGER.error(ex.getMessage());
        }
        return xmlCalendar;
    }
}
