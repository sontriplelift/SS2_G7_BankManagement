//package bank.it2.model.person.customer;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collection;
//import java.util.Date;
//
////import bank.it2.model.transaction.Transaction;
//import domainapp.basics.exceptions.ConstraintViolationException;
//import domainapp.basics.model.meta.AttrRef;
//import domainapp.basics.model.meta.DAssoc;
//import domainapp.basics.model.meta.DAssoc.AssocEndType;
//import domainapp.basics.model.meta.DAssoc.AssocType;
//import domainapp.basics.model.meta.DAssoc.Associate;
//import domainapp.basics.model.meta.DAttr;
//import domainapp.basics.model.meta.DAttr.Type;
//import domainapp.basics.model.meta.DClass;
//import domainapp.basics.model.meta.DOpt;
//import domainapp.basics.model.meta.MetaConstants;
//import domainapp.basics.model.meta.Select;
//import domainapp.basics.util.Tuple;
//import exceptions.DExCode;
//import utils.DToolkit;
//
///**
// * Represents a account. The account ID is auto-incremented from the current
// * year.
// * 
// * @author dmle
// * @version 2.0
// */
//@DClass(schema = "bank")
//public class Account {
//	public static final String A_username = "username";
//	public static final String A_balance = "balance";
//	public static final String A_id = "id";
//	public static final String A_creationDate = "creationDate";
//	public static final String A_customer = "customer";
//	public static final String A_transactions = "transactions";
//
//	// attributes of accounts
//	@DAttr(name = A_id, id = true, type = Type.String, auto = true, length = 9, mutable = false, optional = false)
//	private String id;
//	// static variable to keep track of account id
//	private static int idCounter = 0;
//
//	@DAttr(name = A_username, type = Type.String, length = 14, optional = false, cid = true)
//	private String username;
//
//	@DAttr(name = A_balance, type = Type.Long, length = 20, optional = false, min = 100000)
//	private Long balance;
//
//	@DAttr(name = A_creationDate, type = Type.Date, length = 15, optional = false)
//	private Date creationDate;
//
////  @DAttr(name = A_customer, type = Type.Domain, length = 20, optional = true)
////  @DAssoc(ascName="account-has-customer",role="account",
////      ascType=AssocType.One2One, endType=AssocEndType.One,
////  associate=@Associate(type=Customer.class,cardMin=1,cardMax=1))
////  private Customer customer;
//
//	@DAttr(name = A_customer, type = Type.Domain, length = 20, optional = false)
//	@DAssoc(ascName = "customer-has-account", role = "account", ascType = AssocType.One2One, endType = AssocEndType.One, associate = @Associate(type = Customer.class, cardMin = 1, cardMax = 1))
//	private Customer customer;
//
////	@DAttr(name = "transactions", type = Type.Collection, optional = false, serialisable = false, filter = @Select(clazz = Transaction.class))
////	@DAssoc(ascName = "account-has-transactions", role = "account", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = Transaction.class, cardMin = 0, cardMax = MetaConstants.CARD_MORE))
////	private Collection<Transaction> transactions;
////
////	// derived
////	private int transactionsCount;
//
//	// constructor methods
//	// for creating in the application
//	// without SClass
//	@DOpt(type = DOpt.Type.ObjectFormConstructor)
//	@DOpt(type = DOpt.Type.RequiredConstructor)
//	public Account(@AttrRef("customer") Customer customer, @AttrRef("username") String username,
//			@AttrRef("balance") Long balance, @AttrRef("creationDate") Date creationDate) {
//		this(null, customer, username, balance, creationDate);
//	}
//
//	// a shared constructor that is invoked by other constructors
//	@DOpt(type = DOpt.Type.DataSourceConstructor)
//	public Account(@AttrRef("id") String id, @AttrRef("customer") Customer customer,
//			@AttrRef("username") String username, @AttrRef("balance") Long balance,
//			@AttrRef("creationDate") Date creationDate) throws ConstraintViolationException {
//		// generate an id
//		this.id = nextID(id);
//
//		// assign other values
//		this.username = username;
//		this.balance = balance;
//		this.creationDate = creationDate;
//		this.customer = customer;
//
////		transactions = new ArrayList<>();
////		transactionsCount = 0;
//	}
//
//	// setter methods
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public void setCreationDate(Date creationDate) throws ConstraintViolationException {
//		// additional validation on creationDate
//		if (creationDate.before(DToolkit.MIN_DOB)) {
//			throw new ConstraintViolationException(DExCode.INVALID_DOB, creationDate);
//		}
//
//		this.creationDate = creationDate;
//	}
//
//	public void setBalance(Long balance) {
//		this.balance = balance;
//	}
//
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}
//
//	// v2.7.3
//	public void setNewCustomer(Customer customer) {
//		// change this invocation if need to perform other tasks (e.g. updating value of
//		// a derived attribtes)
//		setCustomer(customer);
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
//	// getter methods
//	public String getId() {
//		return id;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public Long getBalance() {
//		return balance;
//	}
//
//	public Date getCreationDate() {
//		return creationDate;
//	}
//
//	public Customer getCustomer() {
//		return customer;
//	}
//
////	public Collection<Transaction> getTransactions() {
////		return transactions;
////	}
////
////	@DOpt(type = DOpt.Type.LinkCountGetter)
////	public Integer getTransactionsCount() {
////		return transactionCount;
////		// return transactions.size();
////	}
////
////	@DOpt(type = DOpt.Type.LinkCountSetter)
////	public void setTransactionsCount(int count) {
////		transactionCount = count;
////	}
//
//	// override toString
//	/**
//	 * @effects returns <code>this.id</code>
//	 */
//	@Override
//	public String toString() {
//		return toString(true);
//	}
//
//	/**
//	 * @effects returns <code>Account(id,name,creationDate,customer,email)</code>.
//	 */
//	public String toString(boolean full) {
//		if (full)
//			return "Account(" + id + ", " + creationDate + "," + customer + "," + ")";
//		else
//			return "Account(" + id + ")";
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Account other = (Account) obj;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		return true;
//	}
//
//	// automatically generate the next account id
//	private String nextID(String id) throws ConstraintViolationException {
//		if (id == null) { // generate a new id
////			if (idCounter == 0) {
////				idCounter = Calendar.getInstance().get(Calendar.YEAR);
////			} else {
////				idCounter++;
////			}
//			idCounter++;
//			String stringIdCounter = String.format("%06d", idCounter);
//			return "ACC" + stringIdCounter;
//		} else {
//			// update id
//			int num;
//			try {
//				num = Integer.parseInt(id.substring(1));
//			} catch (RuntimeException e) {
//				throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
//						new Object[] { id });
//			}
//
//			if (num > idCounter) {
//				idCounter = num;
//			}
//
//			return id;
//		}
//	}
//
//	/**
//	 * @requires minVal != null /\ maxVal != null
//	 * @effects update the auto-generated value of attribute <tt>attrib</tt>,
//	 *          specified for <tt>derivingValue</tt>, using <tt>minVal, maxVal</tt>
//	 */
//	@DOpt(type = DOpt.Type.AutoAttributeValueSynchroniser)
//	public static void updateAutoGeneratedValue(DAttr attrib, Tuple derivingValue, Object minVal, Object maxVal)
//			throws ConstraintViolationException {
//
//		if (minVal != null && maxVal != null) {
//			// TODO: update this for the correct attribute if there are more than one auto
//			// attributes of this class
//
//			String maxId = (String) maxVal;
//
//			try {
//				int maxIdNum = Integer.parseInt(maxId.substring(1));
//
//				if (maxIdNum > idCounter) // extra check
//					idCounter = maxIdNum;
//
//			} catch (RuntimeException e) {
//				throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
//						new Object[] { maxId });
//			}
//		}
//	}
//}

package services.account.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

//import bank.it2.model.transaction.Transaction;
import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Format;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.MetaConstants;
import domainapp.basics.model.meta.Select;
import domainapp.basics.util.Tuple;
import exceptions.DExCode;
import services.person.model.customer.Customer;
import utils.DToolkit;

/**
 * Represents a account. The account ID is auto-incremented from the current
 * year.
 * 
 * @author dmle
 * @version 2.0
 */
@DClass(schema = "banksystem")
public abstract class Account {
//	public static final String A_username = "username";
//	public static final String A_balance = "balance";
	public static final String A_id = "id";
	public static final String A_creationDate = "creationDate";
	public static final String A_customer = "customer";


	// attributes of accounts
	@DAttr(name = A_id, id = true, type = Type.String, auto = true, length = 9, mutable = false, optional = false)
	private String id;
	// static variable to keep track of account id
	protected static int idCounter = 0;

//	@DAttr(name = A_username, type = Type.String, length = 14, optional = false, cid = true)
//	private String username;
//
//	@DAttr(name = A_balance, type = Type.Long, length = 20, optional = false, min = 100000)
//	private Long balance;
	
	@DAttr(name = A_customer, type = Type.Domain, optional = false)
	@DAssoc(ascName = "customer-has-account", role = "account", ascType = AssocType.One2Many, endType = AssocEndType.Many
	, associate = @Associate(type = Customer.class, cardMin = 1, cardMax = 1))
	private Customer customer;

	@DAttr(name = A_creationDate, type = Type.Date, length = 15, optional = false, format = Format.Date)
	private Date creationDate;


//	@DAttr(name = "transactions", type = Type.Collection, optional = false, serialisable = false, filter = @Select(clazz = Transaction.class))
//	@DAssoc(ascName = "account-has-transactions", role = "account", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = Transaction.class, cardMin = 0, cardMax = MetaConstants.CARD_MORE))
//	private Collection<Transaction> transactions;
//
//	// derived
//	private int transactionsCount;

	// constructor methods
	// for creating in the application
	// without SClass
	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public Account(@AttrRef("customer") Customer customer, @AttrRef("creationDate") Date creationDate) {
		this(null, customer, creationDate);
	}
	
	
	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public Account(@AttrRef("id") String id, @AttrRef("customer") Customer customer, @AttrRef("creationDate") Date creationDate) throws ConstraintViolationException{
		this.id = nextID(id);
		this.customer = customer;
		if (creationDate.before(DToolkit.MIN_TIME)) {
			throw new ConstraintViolationException(DExCode.INVALID_CREATION_DATE, creationDate);
		}
		this.creationDate = creationDate;
	}


	// setter methods

//	public void setCreationDate(Date creationDate) throws ConstraintViolationException {
//		// additional validation on creationDate
//		if (creationDate.before(DToolkit.MIN_DOB)) {
//			throw new ConstraintViolationException(DExCode.INVALID_DOB, creationDate);
//		}
//
//		this.creationDate = creationDate;
//	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	// v2.7.3
	public void setNewCustomer(Customer customer) {
		// change this invocation if need to perform other tasks (e.g. updating value of
		// a derived attribtes)
		setCustomer(customer);
	}

	// getter methods
	public String getId() {
		return id;
	}

	public Date getCreationDate() {
//		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
//	    //cal.set(y, m, d);
//		Date d = cal.getTime();
	    return creationDate;
	}
	
	public void setCreationDate(Date creationDate) throws ConstraintViolationException {
		if (creationDate.before(DToolkit.MIN_TIME)) {
			throw new ConstraintViolationException(DExCode.INVALID_CREATION_DATE, creationDate);
		}
		this.creationDate = creationDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	// override toString
	/**
	 * @effects returns <code>this.id</code>
	 */
	@Override
	public String toString() {
		return toString(true);
	}

	/**
	 * @effects returns <code>Account(id,name,creationDate,customer,email)</code>.
	 */
	public String toString(boolean full) {
		if (full)
			return "Account("+ creationDate + "," + customer + ")";
		else
			return "Account()";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	// automatically generate the next account id
	private String nextID(String id) throws ConstraintViolationException {
		if (id == null) { // generate a new id
//			if (idCounter == 0) {
//				idCounter = Calendar.getInstance().get(Calendar.YEAR);
//			} else {
//				idCounter++;
//			}
			idCounter++;
			String stringIdCounter = String.format("%06d", idCounter);
			return "ACC" + stringIdCounter;
		} else {
			// update id
			int num;
			try {
				num = Integer.parseInt(id.substring(3));
			} catch (RuntimeException e) {
				throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
						new Object[] { id });
			}

			if (num > idCounter) {
				idCounter = num;
			}

			return id;
		}
	}

	/**
	 * @requires minVal != null /\ maxVal != null
	 * @effects update the auto-generated value of attribute <tt>attrib</tt>,
	 *          specified for <tt>derivingValue</tt>, using <tt>minVal, maxVal</tt>
	 */
	@DOpt(type = DOpt.Type.AutoAttributeValueSynchroniser)
	public static void updateAutoGeneratedValue(DAttr attrib, Tuple derivingValue, Object minVal, Object maxVal)
			throws ConstraintViolationException {

		if (minVal != null && maxVal != null) {
			// TODO: update this for the correct attribute if there are more than one auto
			// attributes of this class

			if (attrib.name().equals("id")) {
				String maxId = (String) maxVal;

				try {
					int maxIdNum = Integer.parseInt(maxId.substring(3));

					if (maxIdNum > idCounter) // extra check
						idCounter = maxIdNum;

				} catch (RuntimeException e) {
					throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
							new Object[] { maxId });
				}

			}
		}
	}
}

