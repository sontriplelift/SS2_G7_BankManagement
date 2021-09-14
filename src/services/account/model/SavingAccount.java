package services.account.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.MetaConstants;
import domainapp.basics.model.meta.Select;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.util.Tuple;
import exceptions.DExCode;
import services.account.report.SavingAccountsByCustomerNameReport;
import services.person.model.Gender;
import services.person.model.customer.Customer;
import services.transactions.model.savingTransactions.Finalization;
import services.transactions.model.spendingTransactions.Deposit;
import services.transactions.model.spendingTransactions.Transfer;
import services.transactions.model.spendingTransactions.Withdraw;
import domainapp.basics.model.meta.DAttr.Type;

@DClass(schema = "banksystem")
public class SavingAccount extends Account {

	public static final String A_code = "code";
	public static final String A_savingAmount = "savingAmount";
	public static final String A_termOfSaving = "termOfSaving";
	public static final String A_dueDate = "dueDate";
	public static final String A_interestRate = "interestRate";
	public static final String A_rptSavingAccountsByCustomerDob = "rptSavingAccountsByCustomerDob";
	

	@DAttr(name = A_code, cid=true, type = Type.String, auto = true, length = 9, mutable = false, optional = false)
	private String code;
	// static variable to keep track of account code
	private static int codeCounter = 0;

	@DAttr(name = "savingAmount", type = Type.Long, length = 20, optional = false, min = 1000000L)
	private Long savingAmount;
	
	@DAttr(name = "termOfSaving", type = Type.Domain, length = 20, optional = false)
	private TermOfSaving termOfSaving;
	
	@DAttr(name = "dueDate", type = Type.Date, length = 15, optional = false, auto = true, mutable = false)
	private Date dueDate;
	
	@DAttr(name = "interestRate", type = Type.Double, optional = false, auto = true, mutable = false)
	private Double interestRate;
	
	@DAttr(name = "finalization", type = Type.Domain, optional = true)
	@DAssoc(ascName = "savingAccount-has-finalization", role = "savingAccount", ascType = AssocType.One2One, endType = AssocEndType.One
	, associate = @Associate(type = Finalization.class, cardMin = 1, cardMax = 1))
	private Finalization finalization;
	
	@DAttr(name = A_rptSavingAccountsByCustomerDob, type = Type.Domain, serialisable = false,
			// IMPORTANT: set virtual=true to exclude this attribute from the object state
			// (avoiding the view having to load this attribute's value from data source)
			virtual = true)
	private SavingAccountsByCustomerNameReport rptSavingAccountsByCustomerDob;


	// constructor
	
	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public SavingAccount(@AttrRef("customer") Customer customer,
			@AttrRef("creationDate") Date creationDate,
			@AttrRef("savingAmount") Long savingAmount,
			@AttrRef("termOfSaving") TermOfSaving termOfSaving,
			@AttrRef("finalization") Finalization finalization) throws ConstraintViolationException {
		this(null, customer,creationDate, null, savingAmount, termOfSaving, null, null, finalization);
	}

	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public SavingAccount(@AttrRef("id") String id, @AttrRef("customer") Customer customer,
			@AttrRef("creationDate") Date creationDate,
			@AttrRef("code") String code, 
			@AttrRef("savingAmount") Long savingAmount,
			@AttrRef("termOfSaving") TermOfSaving termOfSaving,
			@AttrRef("dueDate") Date dueDate,
			@AttrRef("interestRate") Double interestRate,
			@AttrRef("finalization") Finalization finalization)
			throws ConstraintViolationException {

		super(id, customer, creationDate);
		this.code = nextCode(code);
		this.savingAmount = savingAmount;
		this.termOfSaving = termOfSaving;
		this.dueDate = getDueDate();
		this.interestRate = getInterestRate();
		this.finalization = finalization;
	}

	public Long getSavingAmount() {
		return savingAmount;
	}

	public void setSavingAmount(Long savingAmount) {
		this.savingAmount = savingAmount;
	}

	public String getCode() {
		return code;
	}

	public TermOfSaving getTermOfSaving() {
		return termOfSaving;
	}

	public void setTermOfSaving(TermOfSaving termOfSaving) {
		this.termOfSaving = termOfSaving;
	}

	public Finalization getFinalization() {
		return finalization;
	}

	public void setFinalization(Finalization finalization) {
		this.finalization = finalization;
	}

	public Date getDueDate() {
		
//		Calendar cal1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.getCreationDate());
		if (termOfSaving == TermOfSaving.Indefinite) {
			dueDate = null; 
		}
		if (termOfSaving == TermOfSaving.OneMonth) {
			cal.add(Calendar.MONTH, 1);
			dueDate = cal.getTime();
		}
		if (termOfSaving == TermOfSaving.TwoMonth) {
			cal.add(Calendar.MONTH, 2);
			dueDate = cal.getTime();
		}
		if (termOfSaving == TermOfSaving.ThreeMonth) {
			cal.add(Calendar.MONTH, 3);
			dueDate = cal.getTime();
		}
		if (termOfSaving == TermOfSaving.FourMonth) {
			cal.add(Calendar.MONTH, 4);
			dueDate = cal.getTime();
		}
		if (termOfSaving == TermOfSaving.FiveMonth) {
			cal.add(Calendar.MONTH, 5);
			dueDate = cal.getTime();
		}
		if (termOfSaving == TermOfSaving.SixMonth) {
			cal.add(Calendar.MONTH, 6);
			dueDate = cal.getTime();
		}
		if (termOfSaving == TermOfSaving.NineMonth) {
			cal.add(Calendar.MONTH, 9);
			dueDate = cal.getTime();
		}
		if (termOfSaving == TermOfSaving.TwelveMonth) {
			cal.add(Calendar.MONTH, 12);
			dueDate = cal.getTime();
		}
		if (termOfSaving == TermOfSaving.FifteenMonth) {
			cal.add(Calendar.MONTH, 15);
			dueDate = cal.getTime();
		}
		if (termOfSaving == TermOfSaving.EighteenMonth) {
			cal.add(Calendar.MONTH, 18);
			dueDate = cal.getTime();
		}
		if (termOfSaving == TermOfSaving.TwentyFourMonth) {
			cal.add(Calendar.MONTH, 24);
			dueDate = cal.getTime();
		}
		if (termOfSaving == TermOfSaving.ThirtySixMonth) {
			cal.add(Calendar.MONTH, 36);
			dueDate = cal.getTime();
		}
		return dueDate;
	}

	public Double getInterestRate() {
		if (termOfSaving == TermOfSaving.Indefinite) {
			interestRate = 0.001; 
		}
		else if (termOfSaving == TermOfSaving.OneMonth || termOfSaving == TermOfSaving.TwoMonth) {
			interestRate = 0.031;
		}
		else if (termOfSaving == TermOfSaving.ThreeMonth || termOfSaving == TermOfSaving.FourMonth || termOfSaving == TermOfSaving.FiveMonth) {
			interestRate = 0.034;
		}
		else if (termOfSaving == TermOfSaving.SixMonth || termOfSaving == TermOfSaving.NineMonth) {
			interestRate = 0.04;
		}
		else {
			interestRate = 0.056;
		}
//		if (termOfSaving == TermOfSaving.TwelveMonth) {
//			cal.add(Calendar.MONTH, 12);
//			interestRate = cal.getTime();
//		}
//		if (termOfSaving == TermOfSaving.FifteenMonth) {
//			cal.add(Calendar.MONTH, 15);
//			interestRate = cal.getTime();
//		}
//		if (termOfSaving == TermOfSaving.EighteenMonth) {
//			cal.add(Calendar.MONTH, 18);
//			interestRate = cal.getTime();
//		}
//		if (termOfSaving == TermOfSaving.TwentyFourMonth) {
//			cal.add(Calendar.MONTH, 24);
//			interestRate = cal.getTime();
//		}
//		if (termOfSaving == TermOfSaving.ThirtySixMonth) {
//			cal.add(Calendar.MONTH, 36);
//			interestRate = cal.getTime();
//		}
		return interestRate;
	}

	public SavingAccountsByCustomerNameReport getRptSavingAccountsByCustomerDob() {
		return rptSavingAccountsByCustomerDob;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		SavingAccount other = (SavingAccount) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	// automatically generate the next account code
	private String nextCode(String code) throws ConstraintViolationException {
		if (code == null) { // generate a new code
//			if (codeCounter == 0) {
//				codeCounter = Calendar.getInstance().get(Calendar.YEAR);
//			} else {
//				codeCounter++;
//			}
			codeCounter++;
			String stringIdCounter = String.format("%06d", codeCounter);
			return "SAV" + stringIdCounter;
		} else {
			// update code
			int num;
			try {
				num = Integer.parseInt(code.substring(3));
			} catch (RuntimeException e) {
				throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
						new Object[] { code });
			}

			if (num > codeCounter) {
				codeCounter = num;
			}

			return code;
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
			if (attrib.name().equals("code")) {
				String maxCode = (String) maxVal;
	
				try {
					int maxCodeNum = Integer.parseInt(maxCode.substring(3));
	
					if (maxCodeNum > codeCounter) // extra check
						codeCounter = maxCodeNum;
	
				} catch (RuntimeException e) {
					throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
							new Object[] { maxCode });
				}
			}
		}
	}

}
