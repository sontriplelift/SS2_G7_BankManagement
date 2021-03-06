//package services.transactions;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.TimeZone;
// 
//import services.branch.ATM;
//import services.person.customer.Account;
//import services.person.employee.Teller;
//import domainapp.basics.exceptions.ConstraintViolationException;
//import domainapp.basics.model.meta.AttrRef;
//import domainapp.basics.model.meta.DAssoc;
//import domainapp.basics.model.meta.DAttr;
//import domainapp.basics.model.meta.DClass;
//import domainapp.basics.model.meta.DOpt;
//import domainapp.basics.model.meta.DAssoc.AssocEndType;
//import domainapp.basics.model.meta.DAssoc.AssocType;
//import domainapp.basics.model.meta.DAssoc.Associate;
//import domainapp.basics.model.meta.DAttr.Type;
//import domainapp.basics.util.Tuple;
//import exceptions.DExCode;
//import utils.DToolkit;
//
//
//@DClass(schema = "banksystem")
//public abstract class Transactions {
//
//	public static final String A_id = "id";
//	public static final String A_amount = "amount";
//	public static final String A_time = "time";
//	public static final String A_teller = "teller";
//	public static final String A_atm = "atm";
//
//	@DAttr(name = A_id, id = true, auto = true, length = 10, mutable = false, type = Type.Integer)
//	private int id;
//	private static int idCounter;
//
//	@DAttr(name = A_time, type = Type.Date, length = 15, optional = false)
//	private Date time;
//
////	@DAttr(name = A_amount, type = Type.Long, length = 20, optional = false, min = 10000)
////	private Long amount;
//
//	@DAttr(name = A_teller, type = Type.Domain, length = 20, optional = false)
//	@DAssoc(ascName = "teller-has-transactions", role = "transactions", ascType = AssocType.One2Many, endType = AssocEndType.Many
//	, associate = @Associate(type = Teller.class, cardMin = 1, cardMax = 1))
//	private Teller teller;
//
////	@DAttr(name = A_atm, type = Type.Domain, length = 20, optional = true)
////	@DAssoc(ascName = "atm-has-transactions", role = "transactions", ascType = AssocType.One2Many, endType = AssocEndType.Many, associate = @Associate(type = ATM.class, cardMin = 1, cardMax = 1))
////	private ATM atm;
//
//	@DOpt(type = DOpt.Type.ObjectFormConstructor)
//	@DOpt(type = DOpt.Type.RequiredConstructor)
//	public Transactions(@AttrRef("time") Date time,
//			@AttrRef("teller") Teller teller) {
//		this(null, time, teller);
//	}
//
//	@DOpt(type = DOpt.Type.DataSourceConstructor)
//	public Transactions(@AttrRef("id") Integer id, 
//			@AttrRef("time") Date time,
//			@AttrRef("teller") Teller teller)
//			throws ConstraintViolationException {
//		// generate an id
//		this.id = nextID(id);
//		this.time = time;
//		this.teller = teller;
////		this.atm = atm;
//	}
//
////	public Account getAccount() {
////		return account;
////	}
////
////	public void setAccount(Account account) {
////		this.account = account;
////	}
//
////	public Long getAmount() {
////		return amount;
////	}
////
////	public void setAmount(Long amount) {
////		this.amount = amount;
////	}
//
//	public Teller getTeller() {
//		return teller;
//	}
//
//	public void setTeller(Teller teller) {
//		this.teller = teller;
//	}
//	
//	public void setTime(Date time) throws ConstraintViolationException {
//		// additional validation on creationDate
//		if (time.before(DToolkit.MIN_DOB)) {
//			throw new ConstraintViolationException(DExCode.INVALID_DOB, time);
//		}
//		this.time = time;
//	}
//	
//	public Date getTime() {
//		return time;
//	}
////
////	public ATM getAtm() {
////		return atm;
////	}
////
////	public void setAtm(ATM atm) {
////		this.atm = atm;
////	}
//
//	public int getId() {
//		return id;
//	}
//
////	public Date getTime() {
////		return time;
////	}
//
//	@Override
//	public String toString() {
//		return "Transactions(" + getId() + ")";
//	}
//
//	private static int nextID(Integer currID) {
//		if (currID == null) {
//			idCounter++;
//			return idCounter;
//		} else {
//			int num = currID.intValue();
//			if (num > idCounter)
//				idCounter = num;
//
//			return currID;
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
//			if (attrib.name().equals("id")) {
//				int maxIdVal = (Integer) maxVal;
//				if (maxIdVal > idCounter)
//					idCounter = maxIdVal;
//			}
//		}
//	}
//
//	private static Date getCurrentTime() {
//		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
//		// getTime() returns the current date in default time zone
//		Date date = calendar.getTime();
//		int day = calendar.get(Calendar.DATE);
//		// Note: +1 the month for current month
//		int month = calendar.get(Calendar.MONTH) + 1;
//		int year = calendar.get(Calendar.YEAR);
//		Date time = DToolkit.getTime(day, month, year);
//		return time;
//	}
//
//	public static boolean updateBalance() {
//		return true;
//	}
//
//}
//
//
//

package services.transactions.model;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import services.Address;
import services.account.model.Account;
import services.person.model.Gender;
import services.person.model.employee.Teller;
import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr.Format;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.util.Tuple;
import exceptions.DExCode;
import utils.DToolkit;

@DClass(schema = "banksystem")
public abstract class Transactions {

	public static final String A_id = "id";
	public static final String A_time = "time";
	public static final String A_teller = "teller";

	@DAttr(name = A_id, id = true, type = Type.Integer, auto = true, length = 10, mutable = false, optional = false)
	private int id;
	// static variable to keep track of Employee id
	private static int idCounter = 0;

	@DAttr(name = A_time, type = Type.Date, length = 15, optional = false, format = Format.Date)
	private Date time;

	@DAttr(name = A_teller, type = Type.Domain, length = 20, optional = false)
	@DAssoc(ascName = "teller-has-transactions", role = "transactions", ascType = AssocType.One2Many, endType = AssocEndType.Many, associate = @Associate(type = Teller.class, cardMin = 1, cardMax = 1))
	private Teller teller;

	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public Transactions(@AttrRef("time") Date time, @AttrRef("teller") Teller teller)
			throws ConstraintViolationException {
		// generate an id
		this(null, time, teller);
	}

	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public Transactions(@AttrRef("id") Integer id,@AttrRef("time") Date time, @AttrRef("teller") Teller teller)
			throws ConstraintViolationException {
		// generate an id
		this.id = nextID(id);
		if (time.before(DToolkit.MIN_TIME)) {
			throw new ConstraintViolationException(DExCode.INVALID_TIME, time);
		}
		this.time = time;
		this.teller = teller;
	}

	public Date getTime() {
//		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
//	    //cal.set(y, m, d);
//		Date d = cal.getTime();
	    return time;
	}
	
	public void setTime(Date time) throws ConstraintViolationException {
		if (time.before(DToolkit.MIN_TIME)) {
			throw new ConstraintViolationException(DExCode.INVALID_TIME, time);
		}
		this.time = time;
	}

//	public void setTime(Date time) throws ConstraintViolationException {
//		// additional validation on dob
//		if (time.before(DToolkit.MIN_DOB)) {
//			throw new ConstraintViolationException(DExCode.INVALID_DOB, time);
//		}
//
//		this.time = time;
//	}

	public Teller getTeller() {
		return teller;
	}

	public void setTeller(Teller teller) {
		this.teller = teller;
	}

	public int getId() {
		return id;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public void setNewTeller(Teller teller) {
		this.teller = teller;
		// do other updates here (if needed)
		// update!!
	}

	@Override
	public String toString() {
		return "Transactions";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Transactions other = (Transactions) obj;
		if (id != other.id)
			return false;
		return true;
	}

	private static int nextID(Integer currID) {
		if (currID == null) {
			idCounter++;
			return idCounter;
		} else {
			int num = currID.intValue();
			if (num > idCounter)
				idCounter = num;

			return currID;
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
				int maxIdVal = (Integer) maxVal;
				if (maxIdVal > idCounter)
					idCounter = maxIdVal;
			}
		}
	}

}
