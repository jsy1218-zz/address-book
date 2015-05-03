package com.sijiang.addressbook.model;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

public final class PhoneNumber implements Comparable<PhoneNumber> {
	private int phoneNumberId;

	@Range(min = 0)
	private final short countryCode;
	@Pattern(regexp = "\\d{3}")
	private final short areaCode;
	@Pattern(regexp = "\\d{3}")
	private final short preFix;
	@Pattern(regexp = "\\d{4}")
	private final short lineNumber;

	@SuppressWarnings("unused")
	private PhoneNumber() {
		throw new UnsupportedOperationException();
	}

	public PhoneNumber(PhoneNumberBuilder phoneNumberBuilder) {
		this.countryCode = phoneNumberBuilder.countryCode;
		this.areaCode = phoneNumberBuilder.areaCode;
		this.preFix = phoneNumberBuilder.preFix;
		this.lineNumber = phoneNumberBuilder.lineNumber;
	}

	public static class PhoneNumberBuilder {
		private short countryCode;
		private final short areaCode;
		private final short preFix;
		private final short lineNumber;

		public PhoneNumberBuilder(int areaCode, int preFix, int lineNumber) {
			this.areaCode = (short) areaCode;
			this.preFix = (short) preFix;
			this.lineNumber = (short) lineNumber;
		}

		public PhoneNumberBuilder countryCode(int countryCode) {
			this.countryCode = (short) countryCode;
			return this;
		}
		
		public PhoneNumber buildPhoneNumber() {
			return new PhoneNumber(this);
		}
	}

	public int getPhoneNumberId() {
		return phoneNumberId;
	}

	public void setPhoneNumberId(int phoneNumberId) {
		this.phoneNumberId = phoneNumberId;
	}
	
	public int getCountryCode() {
		return countryCode;
	}

	public int getAreaCode() {
		return areaCode;
	}

	public int getPreFix() {
		return preFix;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	@Override
	public String toString() {
		return "PhoneNumber" + this.phoneNumberId + "countryCode=" + countryCode + ", areaCode="
				+ areaCode + ", preFix=" + preFix + ", lineNumber="
				+ lineNumber + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + phoneNumberId;
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
		PhoneNumber other = (PhoneNumber) obj;
		if (phoneNumberId != other.phoneNumberId)
			return false;
		return true;
	}

	@Override
	public int compareTo(PhoneNumber other) {
		return phoneNumberId - other.phoneNumberId;
	}
}
