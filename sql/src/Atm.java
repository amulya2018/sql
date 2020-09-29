import bank.dao.AccountDaoImp;
import bank.exception.InsufficientFundsException;

public class Atm {
	public static java.util.Scanner in = new java.util.Scanner(System.in);
	public static atm.transaction.Transaction transaction = new atm.transaction.TransactionImpl();
	public static String accountId;
	
	public static void main(String[] args) {
		
	sop("Account Id? \n");
	accountId = in.nextLine();
	sop("Account Pin? \n");
	String accountPin = in.nextLine();
	
	boolean success = false;
	
	try{
		
		success = transaction.authenticate(accountId, accountPin);
		}catch(bank.exception.AccountNotFound e){
			System.err.println(e.getMessage());
		}catch(bank.exception.InvalidPinException q){
			System.err.println(q.getMessage());
		}
	while(success){
		sop("1. Deposit Amount\n");
		sop("2. Withdraw Amount\n");
		sop("3. Change PIN\n");
		sop("4. Amount Balance\n");
		sop("0. EXIT\n");
		sop("Choice ?");
		int choice = in.nextInt();
		switch(choice) {
		case 1:
			depositAmount();
			break;
		case 2:
			withdrawAmount();
			break;
		case 3:
			changePin();
			break;
		case 4:
			accountBalance();
			break;
		case 0:
			System.exit(0);
			default :sop("Invalid Choise!\n");
		}
	}
	}
	private static void withdrawAmount() {
		sop("Amount ?");
		double amount= in.nextDouble();
		try{
			
		double accountBalance = transaction.withdraw( accountId, amount);
		sop("Amount withdrawn, Current Balance is: " + accountBalance+"\n");
		
	}catch(InsufficientFundsException v){
		System.err.println(v.getMessage());
	}
	
	}
	private static void depositAmount() {
		sop("Amount");
		double amount= in.nextDouble();
		double accountBalance = transaction.deposit(accountId, amount);
		sop("Amount Deposited, Current Balance is: " + accountBalance+"\n");
		
	}
	private static void accountBalance() {
	
		try{
			sop("Account balance :"+ new AccountDaoImp().getAccountById(accountId).getAccountBalance()+"\n");
		}catch(bank.exception.AccountNotFound g){
			System.err.println(g.getMessage());
		}
	}
	private static void changePin() {
		sop("Old pin ?\n");
		in.nextLine();
		String oldPin = in.nextLine();
		sop("New Pin ?\n");
		String newPin = in.nextLine();
		try{
			
		if(transaction.changePin(accountId, oldPin, newPin))
		{
			sop("pin changed successfully!\n");
		}}catch(bank.exception.InvalidPinException v){
			System.err.println(v.getMessage());
	}}
	public static void sop(String message){
		System.out.print(message);
	}
}