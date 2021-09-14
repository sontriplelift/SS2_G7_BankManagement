//package services.person.employee;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//
//import services.branch.Branch;
//import services.person.Address;
//import services.person.Gender;
////import services.transaction.Transaction;
//import domainapp.basics.exceptions.ConstraintViolationException;
//import domainapp.basics.model.meta.AttrRef;
//import domainapp.basics.model.meta.DAssoc;
//import domainapp.basics.model.meta.DAttr;
//import domainapp.basics.model.meta.DClass;
//import domainapp.basics.model.meta.DOpt;
//import domainapp.basics.model.meta.Select;
//import domainapp.basics.model.meta.DAssoc.AssocEndType;
//import domainapp.basics.model.meta.DAssoc.AssocType;
//import domainapp.basics.model.meta.DAssoc.Associate;
//import domainapp.basics.model.meta.DAttr.Type;
//import domainapp.basics.model.meta.MetaConstants;
//
//@DClass(schema = "banksystem")
//public class Teller extends Employee {
//
//	public static final String A_branch = "branch";
//
////	@DAttr(name = "transactions", type = Type.Collection, serialisable = false, optional = false, filter = @Select(clazz = Transaction.class))
////	@DAssoc(ascName = "teller-has-transactions", role = "teller", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = Transaction.class, cardMin = 0, cardMax = MetaConstants.CARD_MORE))
////	private Collection<Transaction> transactions;
////
////	// derived attributes
////	private int transactionsCount;
//
////	@DOpt(type = DOpt.Type.ObjectFormConstructor)
////	@DOpt(type = DOpt.Type.RequiredConstructor)
////	public Teller(@AttrRef("name") String name, @AttrRef("gender") Gender gender, @AttrRef("dob") Date dob,
////			@AttrRef("address") Address address, @AttrRef("email") String email, @AttrRef("phone") String phone,
////			@AttrRef("salary") Double salary) {
////		this(null,name, gender, dob, address, email, phone, salary, null);
////	}
//	
//	@DOpt(type = DOpt.Type.RequiredConstructor)
//	@DOpt(type = DOpt.Type.ObjectFormConstructor)
//	public Teller(@AttrRef("name") String name, @AttrRef("gender") Gender gender, @AttrRef("dob") Date dob,
//			@AttrRef("address") Address address, @AttrRef("email") String email, @AttrRef("phone") String phone,
//			@AttrRef("salary") Double salary, @AttrRef("branch") Branch branch) {
//		this(null, name, gender, dob, address, email, phone, salary, branch);
//	}
//	
//	@DOpt(type = DOpt.Type.ObjectFormConstructor)
//	public Teller(@AttrRef("id") String id, @AttrRef("name") String name, @AttrRef("gender") Gender gender, @AttrRef("dob") Date dob,
//			@AttrRef("address") Address address, @AttrRef("email") String email, @AttrRef("phone") String phone,
//			@AttrRef("salary") Double salary) {
//		this(null, name, gender, dob, address, email, phone, salary, null);
//	}
//
//	@DOpt(type = DOpt.Type.DataSourceConstructor)
//	public Teller(@AttrRef("id") String id, @AttrRef("dob") String name, @AttrRef("gender") Gender gender,
//			@AttrRef("dob") Date dob, @AttrRef("address") Address address, @AttrRef("email") String email,
//			@AttrRef("phone") String phone, @AttrRef("salary") Double salary, @AttrRef("branch") Branch branch)
//			throws ConstraintViolationException {
//		// generate an id
//		super(id, name, gender, dob, address, email, phone, salary, branch);
////		transactions = new ArrayList<>();
////		transactionsCount = 0;
//
//	}
//	
//	
////	public Teller(String id, String name, Gender gender,
////			 Date dob,  Address address,  String email,
////			 String phone, Double salary, Branch branch) {
////		super(id, name, gender, dob, address, email, phone, salary, branch);
////	}
//	
//	
//	@Override
////	@DAttr(name = "branch", type = Type.Domain, length = 14, optional = false)
////	@DAssoc(ascName = "branch-has-tellers", role = "tellers", ascType = AssocType.One2Many, endType = AssocEndType.Many
////	, associate = @Associate(type = Branch.class, cardMin = 1, cardMax = 1))
//	public Branch getBranch() {
//		return super.branch;
//	}
//
////	@DOpt(type = DOpt.Type.LinkAdder)
////	// only need to do this for reflexive association:
////	// @MemberRef(name="transactions")
////	public boolean addTransaction(Transaction s) {
////		if (!this.transactions.contains(s)) {
////			transactions.add(s);
////		}
////
////		// no other attributes changed
////		return false;
////	}
////
////	@DOpt(type = DOpt.Type.LinkAdderNew)
////	public boolean addNewTransaction(Transaction s) {
////		transactions.add(s);
////		transactionsCount++;
////
////		// no other attributes changed
////		return false;
////	}
////
////	@DOpt(type = DOpt.Type.LinkAdder)
////	public boolean addTransaction(Collection<Transaction> transactions) {
////		for (Transaction s : transactions) {
////			if (!this.transactions.contains(s)) {
////				this.transactions.add(s);
////			}
////		}
////
////		// no other attributes changed
////		return false;
////	}
////
////	@DOpt(type = DOpt.Type.LinkAdderNew)
////	public boolean addNewTransaction(Collection<Transaction> transactions) {
////		this.transactions.addAll(transactions);
////		transactionsCount += transactions.size();
////
////		// no other attributes changed
////		return false;
////	}
////
////	@DOpt(type = DOpt.Type.LinkRemover)
////	// only need to do this for reflexive association:
////	// @MemberRef(name="transactions")
////	public boolean removeTransaction(Transaction s) {
////		boolean removed = transactions.remove(s);
////
////		if (removed) {
////			transactionsCount--;
////		}
////
////		// no other attributes changed
////		return false;
////	}
////
////	@DOpt(type = DOpt.Type.Setter)
////	public void setTransactions(Collection<Transaction> transactions) {
////		this.transactions = transactions;
////
////		transactionsCount = transactions.size();
////	}
////
////	/**
////	 * @effects return <tt>transactionsCount</tt>
////	 */
////	@DOpt(type = DOpt.Type.LinkCountGetter)
////	public Integer getTransactionsCount() {
////		return transactionsCount;
////	}
////
////	@DOpt(type = DOpt.Type.LinkCountSetter)
////	public void setTransactionsCount(int count) {
////		transactionsCount = count;
////	}
////
////	@DOpt(type = DOpt.Type.Getter)
////	public Collection<Transaction> getTransactions() {
////		return transactions;
////	}
//
//}

package services.person.model.employee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import services.Address;
import services.branch.model.Branch;
import services.person.model.Gender;
import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.Select;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.model.meta.MetaConstants;

@DClass(schema = "banksystem")
public class Teller extends Employee {

	public static final String A_branch = "branch";
	
	@DAttr(name = "branch", type = Type.Domain, optional = false)
	@DAssoc(ascName = "branch-has-teller", role = "teller", ascType = AssocType.One2Many, endType = AssocEndType.Many
	, associate = @Associate(type = Branch.class, cardMin = 1, cardMax = 1))
	private Branch branch;

//	@DAttr(name = "transactions", type = Type.Collection, serialisable = false, optional = false, filter = @Select(clazz = Transaction.class))
//	@DAssoc(ascName = "teller-has-transaction", role = "teller", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = Transaction.class, cardMin = 0, cardMax = MetaConstants.CARD_MORE))
//	private Collection<Transaction> transactions;

	// derived attributes
//	private int transactionsCount;

//	@DOpt(type = DOpt.Type.ObjectFormConstructor)
//	@DOpt(type = DOpt.Type.RequiredConstructor)
//	public Teller(@AttrRef("name") String name, @AttrRef("gender") Gender gender, @AttrRef("dob") Date dob,
//			@AttrRef("address") Address address, @AttrRef("email") String email, @AttrRef("phone") String phone,
//			@AttrRef("salary") Double salary) {
//		this(null,name, gender, dob, address, email, phone, salary, null);
//	}
	
	@DOpt(type = DOpt.Type.RequiredConstructor)
	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	public Teller(@AttrRef("name") String name, @AttrRef("gender") Gender gender, @AttrRef("dob") Date dob,
			@AttrRef("address") Address address, @AttrRef("email") String email, @AttrRef("phone") String phone,
			@AttrRef("salary") Long salary, @AttrRef("branch") Branch branch) {
		this(null, name, gender, dob, address, email, phone, salary, branch);
	}
	
//	@DOpt(type = DOpt.Type.ObjectFormConstructor)
//	public Teller( @AttrRef("name") String name, @AttrRef("gender") Gender gender, @AttrRef("dob") Date dob,
//			@AttrRef("address") Address address, @AttrRef("email") String email, @AttrRef("phone") String phone,
//			@AttrRef("salary") Double salary) {
//		this(null, name, gender, dob, address, email, phone, salary, null);
//	}

	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public Teller(@AttrRef("id") String id, @AttrRef("dob") String name, @AttrRef("gender") Gender gender,
			@AttrRef("dob") Date dob, @AttrRef("address") Address address, @AttrRef("email") String email,
			@AttrRef("phone") String phone, @AttrRef("salary") Long salary, @AttrRef("branch") Branch branch)
			throws ConstraintViolationException {
		// generate an id
		super(id, name, gender, dob, address, email, phone, salary);
		this.branch = branch;
//		transactions = new ArrayList<>();
//		transactionsCount = 0;

	}
	
	
//	public Teller(String id, String name, Gender gender,
//			 Date dob,  Address address,  String email,
//			 String phone, Double salary, Branch branch) {
//		super(id, name, gender, dob, address, email, phone, salary, branch);
//	}
	
	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Branch getBranch() {
		return branch;
	}

//	@DOpt(type = DOpt.Type.LinkAdder)
//	// only need to do this for reflexive association:
//	// @MemberRef(name="transactions")
//	public boolean addTransaction(Transaction s) {
//		if (!this.transactions.contains(s)) {
//			transactions.add(s);
//		}
//
//		// no other attributes changed
//		return false;
//	}
//
//	@DOpt(type = DOpt.Type.LinkAdderNew)
//	public boolean addNewTransaction(Transaction s) {
//		transactions.add(s);
//		transactionsCount++;
//
//		// no other attributes changed
//		return false;
//	}
//
//	@DOpt(type = DOpt.Type.LinkAdder)
//	public boolean addTransaction(Collection<Transaction> transactions) {
//		for (Transaction s : transactions) {
//			if (!this.transactions.contains(s)) {
//				this.transactions.add(s);
//			}
//		}
//
//		// no other attributes changed
//		return false;
//	}
//
//	@DOpt(type = DOpt.Type.LinkAdderNew)
//	public boolean addNewTransaction(Collection<Transaction> transactions) {
//		this.transactions.addAll(transactions);
//		transactionsCount += transactions.size();
//
//		// no other attributes changed
//		return false;
//	}
//
//	@DOpt(type = DOpt.Type.LinkRemover)
//	// only need to do this for reflexive association:
//	// @MemberRef(name="transactions")
//	public boolean removeTransaction(Transaction s) {
//		boolean removed = transactions.remove(s);
//
//		if (removed) {
//			transactionsCount--;
//		}
//
//		// no other attributes changed
//		return false;
//	}
//
//	@DOpt(type = DOpt.Type.Setter)
//	public void setTransactions(Collection<Transaction> transactions) {
//		this.transactions = transactions;
//
//		transactionsCount = transactions.size();
//	}
//
//	/**
//	 * @effects return <tt>transactionsCount</tt>
//	 */
//	@DOpt(type = DOpt.Type.LinkCountGetter)
//	public Integer getTransactionsCount() {
//		return transactionsCount;
//	}
//
//	@DOpt(type = DOpt.Type.LinkCountSetter)
//	public void setTransactionsCount(int count) {
//		transactionsCount = count;
//	}
//
//	@DOpt(type = DOpt.Type.Getter)
//	public Collection<Transaction> getTransactions() {
//		return transactions;
//	}

}

