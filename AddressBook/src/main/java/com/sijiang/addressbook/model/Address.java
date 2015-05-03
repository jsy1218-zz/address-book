package com.sijiang.addressbook.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public final class Address {
	private int addressId;

	// TODO: error message static final Stringize
	@NotEmpty(message = "You must provider a street name.")
	private final String streetName;
	@NotEmpty(message = "You must provide a city.")
	private final String city;
	@NotEmpty(message = "You must provide a country.")
	private final String country;
	@NotEmpty(message = "You must provide a postal code.")
	private final String postalCode;
	@NotNull(message = "You must provide an address type.")
	private final AddressType addressType;

	public static enum AddressType {
		RESIDENTIAL, COMMERCIAL
	}

	@SuppressWarnings("unused")
	private Address() {
		throw new UnsupportedOperationException();
	}

	public Address(String streetName, String city, String country,
			String postalCode, AddressType addressType) {
		this.streetName = streetName;
		this.city = city;
		this.country = country;
		this.postalCode = postalCode;
		this.addressType = addressType;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	
	public String getStreetName() {
		return streetName;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + addressId;
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
		Address other = (Address) obj;
		if (addressId != other.addressId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Address [streetName=" + streetName + ", city=" + city
				+ ", country=" + country + ", postalCode=" + postalCode
				+ ", addressType=" + addressType + "]";
	}
}
