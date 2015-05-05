package com.sijiang.addressbook.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Account {
	@NotNull
	private final String userName;
	private final String passWord;
	private final AccountStatus accountStatus;
	private final Date createDate;
	private final Date lastLoginDate;
	private final Collection<AddressBook> addressBooks;

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public static enum AccountStatus {
		OPEN, CLOSED;
	}

	public Account(AccountBuilder builder) {
		this.userName = builder.userName;
		this.passWord = builder.passWord;
		this.accountStatus = builder.accountStatus;
		this.createDate = builder.createDate;
		this.lastLoginDate = builder.lastLoginDate;
		this.addressBooks = builder.addressBooks;
	}

	public static class AccountBuilder {
		private final String userName;
		private final String passWord;
		private final AccountStatus accountStatus;
		private Date createDate;
		private Date lastLoginDate;
		private Collection<AddressBook> addressBooks;

		public AccountBuilder(final String username, final String password,
				final AccountStatus accountStatus) {
			this.userName = username;
			this.passWord = password;
			this.accountStatus = accountStatus;
		}

		public AccountBuilder CreateDate(Date createDate) {
			this.createDate = createDate;
			return this;
		}

		public AccountBuilder LastLoginDate(Date lastLoginDate) {
			this.lastLoginDate = lastLoginDate;
			return this;
		}

		public AccountBuilder AddressBooks(Collection<AddressBook> addressBooks) {
			this.addressBooks = new ArrayList<AddressBook>();
			
			this.addressBooks.addAll(addressBooks);
			return this;
		}

		public String getUserName() {
			return userName;
		}

		public String getPassWord() {
			return passWord;
		}

		public AccountStatus getAccountStatus() {
			return accountStatus;
		}

		public Account buildAccount() {
			return new Account(this);
		}
	}

	public String getUserName() {
		return userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public Collection<AddressBook> getAddressBooks() {
		return addressBooks;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(userName)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Account)) {
			return false;
		}
		Account rhs = (Account) obj;
		return new EqualsBuilder()
				.append(userName, rhs.userName)
				.isEquals();
	}

	@Override
	public String toString() {
		return "Account [userName=" + userName + ", passWord=" + passWord
				+ ", accountStatus=" + accountStatus + ", createDate="
				+ createDate + ", lastLoginDate=" + lastLoginDate
				+ ", addressBooks=" + addressBooks + "]";
	}
}
