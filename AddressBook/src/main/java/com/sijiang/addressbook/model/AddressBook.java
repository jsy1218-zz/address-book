package com.sijiang.addressbook.model;

import java.util.Collection;

import javax.validation.constraints.NotNull;

public final class AddressBook {
	private int addressBookId;
	
	@NotNull(message = "There has to be a reference to the list of persons.")
	private final Collection<Person> persons;
	
	@SuppressWarnings("unused")
	private AddressBook() {
		throw new UnsupportedOperationException();
	}

	public AddressBook(Collection<Person> persons) {
		this.persons = persons;
	}

	// Collection over List, getContacts() (plural)
	public Collection<Person> getPersons() {
		return persons;
	}

	public int getAddressBookId() {
		return addressBookId;
	}

	public void setAddressBookId(int addressBookId) {
		this.addressBookId = addressBookId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + addressBookId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressBook other = (AddressBook) obj;
		if (addressBookId != other.addressBookId)
			return false;
		return true;
	}
}
