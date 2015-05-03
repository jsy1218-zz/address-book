package com.sijiang.addressbook.model;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public final class Person implements Comparable<Person> {
	private int personId;

	@NotNull(message = "There has to be a reference to the list of emails.")
	private final Collection<Email> emails; // data type
	@Range(min = 0, max = 150, message = "Valid age range: 0-150")
	private final int age;
	@NotEmpty(message = "You must provide the first name.")
	private final String firstName;
	@NotEmpty(message = "You must provide the last name.")
	private final String lastName;
	@NotNull(message = "There has to be a reference to the list of addresses.")
	private final Collection<Address> addresses; // data type
	@NotNull(message = "There has to be a reference to the list of phone numbers.")
	private final Collection<PhoneNumber> phoneNumbers; // data type

	@SuppressWarnings("unused")
	private Person() {
		throw new UnsupportedOperationException();
	}

	public Person(Collection<Email> emails, int age, String firstName,
			String lastName, Collection<Address> addresses,
			Collection<PhoneNumber> phoneNumbers) {
		this.emails = emails;
		this.age = age;
		this.firstName = firstName;
		this.lastName = lastName;
		this.addresses = addresses;
		this.phoneNumbers = phoneNumbers;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
	public Collection<Email> getEmails() {
		return emails;
	}

	public int getAge() {
		return age;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Collection<Address> getAddresses() {
		return addresses;
	}

	public Collection<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	@Override
	public String toString() {
		return "Person [emails=" + emails + ", age=" + age + ", firstName="
				+ firstName + ", lastName=" + lastName + ", addresses="
				+ addresses + ", phoneNumbers=" + phoneNumbers + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + personId;
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
		Person other = (Person) obj;
		if (personId != other.personId)
			return false;
		return true;
	}

	@Override
	public int compareTo(Person other) {
		// TODO Auto-generated method stub
		return personId - other.personId;
	}
}
