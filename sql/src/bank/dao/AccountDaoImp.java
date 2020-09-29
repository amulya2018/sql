package bank.dao;

import java.sql.Connection;

import bank.exception.AccountNotFound;

public class AccountDaoImp implements AccountDao {
	
	public String createAccount( String accountType, String accountName, double OpeningBalance) 
	throws bank.exception.AcountTypeMismatchException,bank.exception.InsufficientFundsException{
		
		String accountId="";
		try{
			if(!(accountType.equalsIgnoreCase("SB")||accountType.equalsIgnoreCase("CC"))){
				throw new bank.exception.AcountTypeMismatchException("Account is neither saving nor current");
			}
		
			if(accountType.equalsIgnoreCase("SB")&& OpeningBalance <1000){
				throw new bank.exception.InsufficientFundsException("Insufficient opening balance for savings"
						+ "Minimum balance is 1K");
			}
			if(accountType.equalsIgnoreCase("CC") && OpeningBalance <5000){
				throw new bank.exception.InsufficientFundsException("Insufficient opening balance for savings."
						+ "Minimum balance is 5K");
				}
				
			Connection connection= bank.util.DBUtil.getConnection();
			
				String sql =" SELECT MAX(account_id)FROM Accnt11 where account_type =?" ;
					
				java.sql.PreparedStatement stm= connection.prepareStatement(sql);
				stm.setString(1,accountType);
				java.sql.ResultSet result = stm.executeQuery();
				if(result.next()) {
					String maxAccountId = result.getString(1);
					maxAccountId =(maxAccountId!=null)? maxAccountId.substring(2):"0";
					
					int newaccountId = 1+Integer.parseInt(maxAccountId);
					
					accountId = accountType + String.format("%014d",newaccountId);
				}
				String accountPin = generatePin();
				
			
			sql = "INSERT INTO Accnt11 VALUES (?,?,?,?,?)";
			stm = connection.prepareStatement(sql);
			
			stm.setString(1, accountId);
			stm.setString(2, accountType);
			stm.setString(3, accountName);
			stm.setDouble(4, OpeningBalance);
			stm.setString(5, accountPin);
			stm.executeUpdate();
			
			//connection.commit();
			connection.close();
			
		}catch(ClassNotFoundException e1)
	{
		System.err.println(e1.getMessage());
	}catch(
	java.sql.SQLException e2)
	{
		System.err.println(e2.getMessage());
	}
	return accountId ;
}

	public java.util.List<bank.model.Account> getAllAccounts() {
		java.util.List<bank.model.Account> accounts = new java.util.ArrayList<>();
	
		try{
			java.sql.Connection con = bank.util.DBUtil.getConnection();
			String sql = "SELECT * FROM Accnt11";
			java.sql.Statement stm1 = con.createStatement();
			java.sql.ResultSet result = stm1.executeQuery(sql);
			
			while(result.next()){
				String accountId = result.getString(1);
				String accountType = result.getString(2);
				String accountName = result.getString(3);
				double accountBalance = result.getDouble(4);
				String accountpin =  result.getString(5);
				
				bank.model.Account account = null;
				if(accountType.equalsIgnoreCase("SB")){
					account = new bank.model.Saving(accountId,accountType,accountName,accountBalance,accountpin);
				}
				else{
					account = new bank.model.Current(accountId,accountType,accountName,accountBalance,accountpin);
				}
				accounts.add(account);
			}
				con.close();
				
		}catch(ClassNotFoundException e1)
		{
			System.err.println(e1.getMessage());
		}catch(
		java.sql.SQLException e2)
		{
			System.err.println(e2.getMessage());
		}
		
		return accounts;
	}

	public bank.model.Account getAccountById(String accountId) throws bank.exception.AccountNotFound {
		
		bank.model.Account account = null;
		try{
			java.sql.Connection conn = bank.util.DBUtil.getConnection();
			String sql = "SELECT * FROM Accnt11 WHERE account_id = ?";
			java.sql.PreparedStatement stm = conn.prepareStatement(sql);
			stm.setString(1, accountId);
			java.sql.ResultSet result = stm.executeQuery();
			if(result.next()){
				String accountType = result.getString(2);
				String accountName = result.getString(3);
				double accountBalance = result.getDouble(4);
				String accountPin = result.getString(5);
				
				if(accountType.equalsIgnoreCase("SB")) {
					account = new bank.model.Saving(accountId,accountType,accountName,accountBalance,accountPin);
				}
				else {
					account = new bank.model.Current(accountId,accountType,accountName,accountBalance,accountPin);
				}
			} else{
				throw new bank.exception.AccountNotFound("Account with id :" + accountId +" not Found!");
			}
			conn.close();
		}
	catch(ClassNotFoundException e1)
	{
		System.err.println(e1.getMessage());
	}catch(
	java.sql.SQLException e2)
	{
		System.err.println(e2.getMessage());
	}
	
	return account;
	}

	public bank.model.Account updateAccount(String accountId, String accountName)
			throws bank.exception.AccountNotFound {
		
		try{
			java.sql.Connection connection = bank.util.DBUtil.getConnection();
			bank.model.Account account = getAccountById(accountId);
			if(account==null){
				throw new AccountNotFound("Account with ID: " +accountId + "not found!");
			}
			String sql = "UPDATE Accnt11 SET account_name = ? where account_id = ?";
			java.sql.PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1,accountName);
			statement.setString(2,accountId);
			statement.executeUpdate();
//			connection.commit();
			connection.close();
		} catch(ClassNotFoundException d) {
			System.err.println(d.getMessage());
		}catch(
		java.sql.SQLException e2)
		{
			System.err.println(e2.getMessage());
			
		}
		return getAccountById(accountId);

	}

	public boolean deleteAccount(String accountId) throws bank.exception.AccountNotFound {
		boolean status = false;
		try{
		java.sql.Connection connection = bank.util.DBUtil.getConnection();
		bank.model.Account account = getAccountById(accountId);
		if(account == null){
			throw new bank.exception.AccountNotFound("Account with Id : " +accountId + " not found!");
			
		}
		 String sql = "DELETE FROM Accnt11 WHERE account_id = ?";
		 java.sql.PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1,accountId);
			statement.executeUpdate();
			//connection.commit();
			connection.close();
			status = true;
		}catch(ClassNotFoundException d) {
			System.err.println(d.getMessage());
		}catch(
		java.sql.SQLException e2)
		{
			System.err.println(e2.getMessage());
		}
		
		return status;
	}
	public String generatePin(){
		String accpin = "";
		java.util.Random random = new java.util.Random();
		// random gives any values between 0 and 1
		// now it gives btw 0 to 9
		int firstNumber =(int)(random.nextDouble() *10);
		int secondNumber =(int)(random.nextDouble() *10);
		int thirdNumber =(int)(random.nextDouble() *10);
		int fourthNumber =(int)(random.nextDouble() *10);
		return ""+firstNumber+secondNumber+thirdNumber+fourthNumber;
	}
	
}


