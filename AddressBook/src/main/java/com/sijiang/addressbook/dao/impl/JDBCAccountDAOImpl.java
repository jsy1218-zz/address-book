package com.sijiang.addressbook.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.sijiang.addressbook.dao.AccountDAO;
import com.sijiang.addressbook.model.Account;
import com.sijiang.addressbook.model.Account.AccountStatus;

@Component("com.sijiang.addressbook.dao.impl.JDBCAccountDAOImpl")
public class JDBCAccountDAOImpl implements AccountDAO {
	private static String OPEN_ACCOUNT = "INSERT INTO account (username, password, account_status) "
			+ " VALUES (?, ?, ?) ";	
	
	private JdbcTemplate JDBCTemplate;
			
	@Resource(name = "JDBCTemplate")
	public final void setJDBCTemplate(JdbcTemplate JDBCTemplate) {
		this.JDBCTemplate = JDBCTemplate;
	}

	@Override
	public void openAccount(Account newAccount) {
		JDBCTemplate.update(OPEN_ACCOUNT,
				new OpenAccountPreparedStatementSetter(newAccount.getUserName(), 
						newAccount.getPassWord(), newAccount.getAccountStatus()));
	}

	@Override
	public void closeAccount(Account closingAccount) {
		// TODO Auto-generated method stub

	}

	@Override
	public Account findAccountByUsername(String username) {
		return null;
		// TODO Auto-generated method stub

	}

	private class OpenAccountPreparedStatementSetter implements
			PreparedStatementSetter {
		private final String userName;
		private final String passWord;
		private final AccountStatus accountStatus;
		private Date createDate;
		private Date lastLoginDate;

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
}
