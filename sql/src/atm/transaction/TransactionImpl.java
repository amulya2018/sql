package atm.transaction;

import java.sql.PreparedStatement;

import bank.dao.AccountDao;
import bank.exception.AccountNotFound;
import bank.exception.InsufficientFundsException;
import bank.exception.InvalidPinException;
import bank.model.Account;

public class TransactionImpl implements Transaction{
public static bank.dao.AccountDao accdao =  new bank.dao.AccountDaoImp();

	@Override
	public boolean authenticate(String accountId, String accountPin) throws
	AccountNotFound, InvalidPinException {

			bank.model.Account account = accdao.getAccountById(accountId);
			if(!account.getPin().equals(accountPin)){
				throw new bank.exception.InvalidPinException("Invalid pin");
		}
		return true;
	}

	@Override
	public double deposit(String accountId, double amount) {
		double accountBalance=0;
		try {
			bank.model.Account account = accdao.getAccountById(accountId);
			java.sql.Connection con = bank.util.DBUtil.getConnection();
			String sql = "UPDATE Accnt11 SET account_balance=? WHERE account_id = ?";
			 accountBalance = account.getAccountBalance() +amount;
			java.sql.PreparedStatement stm = con.prepareStatement(sql);
			stm.setDouble(1, accountBalance);
			stm.setString(2,accountId);
			stm.executeUpdate();
			
			con.close();
		}catch(ClassNotFoundException f){
			System.err.println(f.getMessage());
		}catch(java.sql.SQLException s){
			System.err.println(s.getMessage());
		}catch(bank.exception.AccountNotFound h){
			System.err.println(h.getMessage());
		}
		return accountBalance;
	}

	@Override
	public double withdraw(String accountId, double amount) throws InsufficientFundsException {
	double accountBalance = 0;
	try{
		accountBalance=accdao.getAccountById(accountId).getAccountBalance()-amount;
		
		
		if(accountBalance-amount<0) {
			throw new bank.exception.InsufficientFundsException("Insufficient fund!");
		}
		java.sql.Connection con = bank.util.DBUtil.getConnection();
		String sql = "UPDATE Accnt11 SET account_balance = ? WHERE account_id = ? ";
		java.sql.PreparedStatement stm = con.prepareStatement(sql);
		stm.setDouble(1, accountBalance);
		stm.setString(2, accountId);
		stm.executeUpdate();
		con.close();
	}catch(ClassNotFoundException f){
		System.err.println(f.getMessage());
	}catch(java.sql.SQLException s){
		System.err.println(s.getMessage());
	}catch(bank.exception.AccountNotFound h){
		System.err.println(h.getMessage());
	}
		
		return accountBalance;
	}

//	@Override
//	public double getBalance(String accountId) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	@Override
	public boolean changePin(String accountId, String oldpin, String newPin) 
			throws InvalidPinException {
		boolean success=false;
		try{
			bank.model.Account account = accdao.getAccountById(accountId);
			String ap = account.getPin();
			if(!ap.equals(oldpin)){
				throw new bank.exception.InvalidPinException("old pin doesnot match!");
			}
			if(oldpin.equals(newPin)) {
				throw new bank.exception.InvalidPinException("Old pin and new pin are same ");
			}
			if(newPin.length()!=4) {
				throw new bank.exception.InvalidPinException("New Pin should consist of 4 Numbers");
				}
			if((new StringBuilder(newPin).reverse().toString()).equals(newPin)){
				throw new bank.exception.InvalidPinException("New Pin Cannot be a palindrome");
			}
			java.sql.Connection con= bank.util.DBUtil.getConnection();
			String sql = "UPDATE Accnt11 SET account_pin = ? WHERE account_id = ? ";
			java.sql.PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, newPin);
			stm.setString(2, accountId);
			stm.executeUpdate();
			con.close();
			
			success=true;
		
		}catch(bank.exception.AccountNotFound s){
			System.err.println(s.getMessage());
		}catch(ClassNotFoundException h){
			System.err.println(h.getMessage());
		}catch (java.sql.SQLException g){
			System.err.println(g.getMessage());
		}
		return success;
	}
	
}
