package software;

import domainapp.software.SoftwareFactory;
import domainapp.softwareimpl.DomSoftware;
import services.Address;
import services.account.model.Account;
import services.account.model.CreditAccount;
import services.account.model.LoanAccount;
import services.account.model.SavingAccount;
import services.account.model.SpendAccount;
import services.account.report.SavingAccountsByCustomerNameReport;
import services.branch.model.Branch;
import services.person.model.customer.Customer;
import services.person.model.employee.Employee;
import services.person.model.employee.Manager;
import services.person.model.employee.Teller;
import services.person.report.CustomersByNameReport;
import services.transactions.model.Transactions;
import services.transactions.model.creditTransactions.DeptRepayment;
import services.transactions.model.creditTransactions.Spending;
import services.transactions.model.loanTransactions.PeriodicRepayment;
import services.transactions.model.savingTransactions.Finalization;
import services.transactions.model.spendingTransactions.Deposit;
import services.transactions.model.spendingTransactions.Transfer;
import services.transactions.model.spendingTransactions.Withdraw;


/**
 * @overview 
 *  Create and run a UI-based {@link DomSoftware} for a pre-defined model.  
 *  
 * @author dmle
 */
public class Main {
  
  // 1. initialise the model
  static final Class[] model = {
		  Address.class,
		  Branch.class,
		  
		  Employee.class,
		  Manager.class,
		  Teller.class,
		  
		  Customer.class,
		  
		  Account.class,
		  SpendAccount.class,
		  CreditAccount.class,
		  SavingAccount.class,
		  LoanAccount.class,
		  
		  Transactions.class,
		  
		  Deposit.class,
		  Withdraw.class,   
		  Transfer.class,
		  
		  Spending.class,
		  DeptRepayment.class,
		  
		  Finalization.class,
		  
		  PeriodicRepayment.class,
		  
		  //reports
		  CustomersByNameReport.class,
		  SavingAccountsByCustomerNameReport.class,
		  

  };
  
  /**
   * @effects 
   *  create and run a UI-based {@link DomSoftware} for a pre-defined model. 
   */
  public static void main(String[] args){
    // 2. create UI software
    DomSoftware sw = SoftwareFactory.createUIDomSoftware();
    
    // 3. run
    // create in memory configuration
    System.setProperty("domainapp.setup.SerialiseConfiguration", "false");
    
    // 3. run it
    try {
      sw.run(model);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }   
  }

}