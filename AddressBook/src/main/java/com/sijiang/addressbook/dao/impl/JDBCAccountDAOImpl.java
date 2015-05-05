package com.sijiang.addressbook.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.sijiang.addressbook.dao.AccountDAO;
import com.sijiang.addressbook.model.Account;
import com.sijiang.addressbook.model.Account.AccountStatus;

@Component("com.sijiang.addressbook.dao.impl.JDBCAccountDAOImpl")
public class JDBCAccountDAOImpl implements AccountDAO {
	private static final String OPEN_ACCOUNT = "INSERT INTO account (username, password, account_status) "
			+ " VALUES (?, ?, ?) ";

	private static final String CLOSE_ACCOUNT = "UPDATE account "
			+ " SET account_status = ? " + " WHERE username = ? ";

	private static final String FIND_ACCOUNT_BY_USERNAME = "SELECT username, password, "
			+ " create_date, last_login_time, account_status "
			+ " FROM account " + " WHERE username = ? ";

	private JdbcTemplate JDBCTemplate;

	@Resource(name = "JDBCTemplate")
	public final void setJDBCTemplate(JdbcTemplate JDBCTemplate) {
		this.JDBCTemplate = JDBCTemplate;
	}

	@Override
	public void openAccount(Account newAccount) {
		JDBCTemplate.update(OPEN_ACCOUNT,
				new OpenAccountPreparedStatementSetter(
						newAccount.getUserName(), newAccount.getPassWord(),
						newAccount.getAccountStatus()));
	}

	@Override
	public void closeAccount(Account closingAccount) {
		JDBCTemplate.update(CLOSE_ACCOUNT,
				new CloseAccountPreparedStatementSetter(
				AccountStatus.CLOSED.toString()
				, closingAccount.getUserName()));

	}

	@Override
	public Account findAccountByUsername(String username) {
		Account searchedAccount = JDBCTemplate.queryForObject(
				FIND_ACCOUNT_BY_USERNAME, new Object[] { username },
				new int[] { Types.VARCHAR },
				new FindAccountByUsernameRowMapper());

		return searchedAccount;
	}

	private class FindAccountByUsernameRowMapper implements RowMapper<Account> {

		@Override
		public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
			String username = rs.getString("username");
			String password = rs.getString("password");
			Date createDate = rs.getDate("create_date");
			Date lastLoginDate = rs.getDate("last_login_time");
			AccountStatus accountStatus = AccountStatus.valueOf(
					AccountStatus.class, rs.getString("account_status"));

			Account searchedAccountByUsername = new Account.AccountBuilder(
					username, password, accountStatus).CreateDate(createDate)
					.LastLoginDate(lastLoginDate).buildAccount();

			return searchedAccountByUsername;
		}
	}

	private class OpenAccountPreparedStatementSetter implements
			PreparedStatementSetter {
		private final String userName;
		private final String passWord;
		private final AccountStatus accountStatus;

		@SuppressWarnings("unused")
		private OpenAccountPreparedStatementSetter() {
			throw new UnsupportedOperationException();
		}

		public OpenAccountPreparedStatementSetter(final String username,
				final String password, final AccountStatus accountStatus) {
			this.userName = username;
			this.passWord = password;
			this.accountStatus = accountStatus;
		}

		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, userName);
			ps.setString(2, passWord);
			ps.setString(3, accountStatus.toString());
		}
	}

	private class CloseAccountPreparedStatementSetter implements
			PreparedStatementSetter {
		private final String accountStatusAsString;
		private final String userName;

		@SuppressWarnings("unused")
		private CloseAccountPreparedStatementSetter() {
			throw new UnsupportedOperationException();
		}

		public CloseAccountPreparedStatementSetter(final String accountStatusAsString,
				final String userName) {
			this.accountStatusAsString = accountStatusAsString;
			this.userName = userName;
		}

		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, this.accountStatusAsString);
			ps.setString(2, this.userName);
		}
	}
}
