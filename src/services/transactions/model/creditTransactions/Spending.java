package services.transactions.model.creditTransactions;

import domainapp.basics.model.meta.DClass;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr.Format;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.util.Tuple;
import exceptions.DExCode;
import services.account.model.CreditAccount;
import services.person.model.Gender;
import services.person.model.employee.Teller;
import services.transactions.model.Service;
import services.transactions.model.Transactions;
import utils.DToolkit;

@DClass(schema = "banksystem")
public class Spending{
	
	public static final String A_id = "id";
	public static final String A_time = "time";
	
	@DAttr(name = A_id, id = true, type = Type.Integer, auto = true, length = 10, mutable = false, optional = false)
	private int id;
	// static variable to keep track of Employee id
	private static int idCounter = 0;

	@DAttr(name = A_time, type = Type.Date, length = 15, optional = false, format = Format.Date)
	private Date time;
	
	@DAttr(name = "creditAccount", type = Type.Domain, optional = false)
	@DAssoc(ascName = "creditAccount-has-spending", role = "spending", ascType = AssocType.One2Many, endType = AssocEndType.Many
	, associate = @Associate(type = CreditAccount.class, cardMin = 1, cardMax = 1))
	private CreditAccount creditAccount;
	
	@DAttr(name = "service", type = Type.Domain, length = 10, optional = false)
	private Service service;
	
	@DAttr(name = "price", type = Type.Long, length = 20, optional = false)
	private Long price;

	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public Spending(
			@AttrRef("time") Date time,
			@AttrRef("creditAccount") CreditAccount creditAccount,
			@AttrRef("service") Service service,
			@AttrRef("price") Long price)
			throws ConstraintViolationException {
		// generate an id
		this(null, time, creditAccount, service, price);
	}

	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public Spending(@AttrRef("id") Integer id, @AttrRef("time") Date time, 
			@AttrRef("creditAccount") CreditAccount creditAccount,
			@AttrRef("service") Service service,
			@AttrRef("price") Long price)
			throws ConstraintViolationException {
		// generate an id
		this.id = nextID(id);
		if (time.before(DToolkit.MIN_TIME)) {
			throw new ConstraintViolationException(DExCode.INVALID_TIME, time);
		}
		this.time = time;
		this.creditAccount = creditAccount;
		this.service = service;
		if ((price + creditAccount.getCreditBalance()) > creditAccount.getLimit()) {
			throw new ConstraintViolationException(DExCode.INVALID_SPENDING, creditAccount.getLimit());
		}
		this.price = price;
	}

	public Date getTime() {
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


	public int getId() {
		return id;
	}
	
	public CreditAccount getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(CreditAccount creditAccount) {
		this.creditAccount = creditAccount;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) throws ConstraintViolationException {
		if ((price + creditAccount.getCreditBalance()) > creditAccount.getLimit()) {
			throw new ConstraintViolationException(DExCode.INVALID_SPENDING, creditAccount.getLimit());
		}
		this.price = price;
	}

	@Override
	public String toString() {
		return "Payment";
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
		Spending other = (Spending) obj;
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
