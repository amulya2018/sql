package atm.transaction;

public interface Transaction {
	public boolean authenticate(String accountId,String accountPin)
	throws bank.exception.AccountNotFound,bank.exception.InvalidPinException;
	
	public double deposit(String accountId,double amount);
	public double withdraw(String accountId,double amount)throws bank.exception.InsufficientFundsException;

	
	public boolean changePin(String accountId,String oldpin, String newPin)throws bank.exception.InvalidPinException;
	
}
