package com.sijiang.addressbook.model;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public final class Email implements Comparable<Email> {
	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private int emailId;
	
	@NotEmpty(message = "You must provide an email.")
	@Pattern(regexp = EMAIL_REGEX, message="You must provide a valid email address.") 
	private final String email;

	@SuppressWarnings("unused")
	private Email() {
		throw new UnsupportedOperationException();
	}

	public Email(String email) {
		this.email = email;
	}

	public int getEmailId() {
		return emailId;
	}

	public void setEmailId(int emailId) {
		this.emailId = emailId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + emailId;
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
		Email other = (Email) obj;
		if (emailId != other.emailId)
			return false;
		return true;
	}

	@Override 
	public String toString() {
		return this.email;
	}

	@Override
	public int compareTo(Email other) {
		return this.emailId - other.emailId;
	}
}
