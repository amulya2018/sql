

import bank.exception.*;

public class Bank {
	static java.util.Scanner in = new java.util.Scanner(System.in);
	static bank.dao.AccountDao accountdao = new bank.dao.AccountDaoImp();

	public static void main(String[] agrs) {
		int choice = 0;
		while (true) {
			System.out.println(" 1: Create Account");
			System.out.println("2: Get all Accounts ");
			System.out.println("3: Get Account By Id ");
			System.out.println("4: Update Account ");
			System.out.println("5: delete Account ");
			System.out.println("0: Exit ");
			System.out.println("Choice ");

			choice = in.nextInt();
			switch (choice) {
			case 1:
				createAccount();
				break;
			case 2:
				getAllAccounts();
				break;
			case 3:getAccountById()
				;
				break;
			case 4:
				updateAccount();
				break;
			case 5:
				deleteAccount();
				
				break;
			case 6:
				System.exit(0);
			}
		}
	}

	private static void getAccountById() {
		System.out.println("Account id?");
		in.nextLine();
		String accountId = in.nextLine();
		try{
			bank.model.Account account = accountdao.getAccountById(accountId);
			System.out.println(account);
			
		}catch(AccountNotFound ar){
			
			System.err.println(ar.getMessage());
		}

	}

	private static void deleteAccount() {
		System.out.println("Account Id: ?");
		in.nextLine();
		String accountId = in.nextLine();
		try{
			if(accountdao.deleteAccount(accountId)){
				System.out.println("Account with Id : " +accountId + "Deleted!");
			}
		}catch(bank.exception.AccountNotFound ar){
			
			System.err.println(ar.getMessage());
	}
	}

	private static void updateAccount() {
		System.out.println("Account Id: ?");
		in.nextLine();
		String accountId = in.nextLine();
		System.out.println("Account Name : ?");
		String accountname = in.nextLine();
		try{
			bank.model.Account account = accountdao.updateAccount(accountId, accountname);
			System.out.println("Account updated "+account);
			
		}catch(AccountNotFound ar){
			
			System.err.println(ar.getMessage());
		}

	}

	private static void createAccount() {
		System.out.println("Account Type ?");
		in.nextLine();
		String accountType = in.nextLine();
		System.out.println("Account Name ?");
		String accountName = in.nextLine();
		System.out.println("Opening Balance ?");
		Double OpeningBalance = in.nextDouble();
		try {
			String accountId = accountdao.createAccount(accountType, accountName, OpeningBalance);
			String accountPin = accountdao.getAccountById(accountId).getPin();
			System.out.println("Account Created with Id:" + accountId);
			System.out.println("Your pin is:" +accountPin);
		} catch (bank.exception.AcountTypeMismatchException a) {
			System.err.println(a.getMessage());
		} catch (bank.exception.InsufficientFundsException b) {
			System.err.println(b.getMessage());
		}catch(bank.exception.AccountNotFound n){
			System.err.println(n.getMessage());
		}
	}

	private static void getAllAccounts() {
		
		java.util.List<bank.model.Account> accounts = accountdao.getAllAccounts();
		for(bank.model.Account account: accounts){
			System.out.println(account);
		}
	}
}
