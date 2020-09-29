package bank.model;

import java.util.List;

import bank.exception.AccountNotFound;
import bank.exception.AcountTypeMismatchException;
import bank.exception.InsufficientFundsException;

public  class Current extends Account{
	public Current(String accountid, String accountType, String accountName, double accountBalance,String accountPin)
	{
		super(accountid, accountType,  accountName,  accountBalance , accountPin);
}

	public String createAccount(String accountType, String accountname, double OpeningBalance ,String accountPin)
			throws AcountTypeMismatchException, InsufficientFundsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account getAccountById(String accountId) throws AccountNotFound {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account updateAccount(String accountId, String accountName) throws AccountNotFound {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteAccount(String accountId) throws AccountNotFound {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String createAccount(String accountType, String accountname, double OpeningBalance)
			throws AcountTypeMismatchException, InsufficientFundsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generatePin() {
		// TODO Auto-generated method stub
		return null;
	}
}