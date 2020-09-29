package bank.model;

public abstract class Account implements bank.dao.AccountDao {

	private String accountid;
	private String accountType;
	private String accountName;
	private double accountBalance;
	private String accountPin;

	public Account(String accountid, String accountType, String accountName, double accountBalance,String accountPin) {
		super();
		this.accountid = accountid;
		this.accountType = accountType;
		this.accountName = accountName;
		this.accountBalance = accountBalance;
		this.accountPin= accountPin;
		}

	
	public String getPin() {
		return accountPin;
	}


	public void setPin(String accountPin) {
		this.accountPin = accountPin;
	}


	public String getAccountid() {
		return accountid;
	}


	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}


	public String getAccountType() {
		return accountType;
	}


	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public double getAccountBalance() {
		return accountBalance;
	}


	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}


	@Override
	public String toString() {
		return "Account [accountid=" + accountid + ", accountType=" + accountType + ", accountName=" + accountName
				+ ", accountBalance=" + accountBalance + ", pin=" +accountPin+ "]";
	}

}