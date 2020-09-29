package bank.dao;

public interface AccountDao {
	public String createAccount(String accountType, String accountname, double OpeningBalance) throws bank.exception.AcountTypeMismatchException,
	bank.exception.InsufficientFundsException ;
	
	public java.util.List<bank.model.Account> getAllAccounts();
	
	public bank.model.Account getAccountById(String accountId)throws bank.exception.AccountNotFound;
	
	public bank.model.Account updateAccount(String accountId, String accountName)throws bank.exception.AccountNotFound;
	
	public boolean deleteAccount(String accountId) throws bank.exception.AccountNotFound;
	
	public String generatePin();
	
}
