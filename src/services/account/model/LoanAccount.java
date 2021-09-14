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
import services.person.model.customer.Customer;
import services.transactions.model.loanTransactions.PeriodicRepayment;

import domainapp.basics.model.meta.DAttr.Type;

@DClass(schema = "banksystem")
public class LoanAccount extends Account {

	public static final String A_code = "code";

	@DAttr(name = A_code, cid=true, type = Type.String, auto = true, length = 9, mutable = false, optional = false)
	private String code;
	// static variable to keep track of account code
	private static int codeCounter = 0;
	
	@DAttr(name = "income", type = Type.Long, length = 20, optional = false, min = 10000000L)
	private Long income;
	
	@DAttr(name = "collateral", type = Type.Domain, length = 20, optional = false)
	private Collateral collateral;
	
	@DAttr(name = "collateralValue", type = Type.Long, optional = true)
	private Long collateralValue;
	
	@DAttr(name = "purpose", type = Type.String, optional = false)
	private String purpose;
	
	@DAttr(name = "limit",auto=true, type = Type.Long, length = 20, optional = false, mutable = false)
	private Long limit;
	
	@DAttr(name = "loanAmount", type = Type.Long, length = 20, optional = false)
	private Long loanAmount;

	@DAttr(name = "durationByMonth", type = Type.Integer, optional = false, mutable = false)
	private Integer durationByMonth;
	
	@DAttr(name = "repaymentPeriod", type = Type.Integer, optional = false, mutable = false)
	private Integer repaymentPeriod;

	@DAttr(name = "interestRate", type = Type.Double, optional = false, auto = true, mutable = false)
	private Double interestRate;
	
	@DAttr(name = "totalRepayment",auto=true, type = Type.Long, length = 20, optional = false, mutable = false)
	private Long totalRepayment;
	
	@DAttr(name = "repaidAmount",auto=true, type = Type.Long, length = 20, optional = false, mutable = false)
	private Long repaidAmount;
	
	@DAttr(name = "totalInstallments",auto=true, type = Type.Integer, optional = false, mutable = false)
	private Integer totalInstallments;
	
	@DAttr(name = "numOfPaidInstallments",auto=true, type = Type.Integer, optional = false, mutable = false)
	private Integer numOfPaidInstallments;
	
	@DAttr(name = "periodicRepayment", type = Type.Collection, serialisable = false, optional = true, filter = @Select(clazz = PeriodicRepayment.class))
	@DAssoc(ascName = "loanAccount-has-periodicRepayment", role = "loanAccount", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = PeriodicRepayment.class, cardMin = 1, cardMax = MetaConstants.CARD_MORE))
	private Collection<PeriodicRepayment> periodicRepayment;

	private int periodicRepaymentCount;
	

	// constructor
	
	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public LoanAccount(@AttrRef("customer") Customer customer,
			@AttrRef("creationDate") Date creationDate,
			@AttrRef("income") Long income,
			@AttrRef("collateral") Collateral collateral,
			@AttrRef("collateralValue") Long collateralValue,
			@AttrRef("purpose") String purpose,
			@AttrRef("loanAmount") Long loanAmount,
			@AttrRef("durationByMonth") Integer durationByMonth,
			@AttrRef("repaymentPeriod") Integer repaymentPeriod
			) throws ConstraintViolationException {
		this(null, customer,creationDate, null, income, collateral, collateralValue, purpose, null, loanAmount, durationByMonth, repaymentPeriod, null, null, null, null, null);
	}
	
	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	public LoanAccount(@AttrRef("customer") Customer customer,
			@AttrRef("creationDate") Date creationDate,
			@AttrRef("income") Long income,
			@AttrRef("collateral") Collateral collateral,
			@AttrRef("purpose") String purpose,
			@AttrRef("loanAmount") Long loanAmount,
			@AttrRef("durationByMonth") Integer durationByMonth,
			@AttrRef("repaymentPeriod") Integer repaymentPeriod
			) throws ConstraintViolationException {
		this(null, customer,creationDate, null, income, collateral, null, purpose, null, loanAmount, durationByMonth, repaymentPeriod, null, null, null, null, null);
	}

	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public LoanAccount(@AttrRef("id") String id, 
			@AttrRef("customer") Customer customer,
			@AttrRef("creationDate") Date creationDate,
			@AttrRef("code") String code, 
			@AttrRef("income") Long income,
			@AttrRef("collateral") Collateral collateral,
			@AttrRef("collateralValue") Long collateralValue,
			@AttrRef("purpose") String purpose,
			@AttrRef("limit") Long limit,
			@AttrRef("loanAmount") Long loanAmount,
			@AttrRef("durationByMonth") Integer durationByMonth,
			@AttrRef("repaymentPeriod") Integer repaymentPeriod,
			@AttrRef("interestRate") Double interestRate,
			@AttrRef("totalRepayment") Long totalRepayment,
			@AttrRef("repaidAmount") Long repaidAmount,
			@AttrRef("totalInstallments") Integer totalInstallments,
			@AttrRef("numOfPaidInstallments") Integer numOfPaidInstallments)
			throws ConstraintViolationException {

		super(id, customer, creationDate);
		this.code = nextCode(code);
		this.income = income;
		this.collateral = collateral;
		if (collateral == Collateral.None) {
			collateralValue = 0L;
		} else {
			this.collateralValue = collateralValue;
		}
//		this.collateralValue = collateralValue;
		this.purpose = purpose;
		this.limit = getLimit();
		if (loanAmount > getLimit()) {
			throw new ConstraintViolationException(DExCode.INVALID_LOAN_AMOUNT, loanAmount, getLimit());
		}
		this.loanAmount = loanAmount;
		this.durationByMonth = durationByMonth;
		this.repaymentPeriod = repaymentPeriod;
		this.interestRate = getInterestRate();
		this.totalRepayment = getTotalRepayment();
		if (repaidAmount == null)
			this.repaidAmount = 0L;
		else 
			this.repaidAmount = repaidAmount;
		this.totalInstallments = getTotalInstallments();
		if (numOfPaidInstallments == null)
			this.numOfPaidInstallments = 0;
		else
			this.numOfPaidInstallments = numOfPaidInstallments;
		periodicRepayment = new ArrayList<>();
		periodicRepaymentCount = 0;
	}

	
	


	public Long getIncome() {
		return income;
	}

	public void setIncome(Long income) {
		this.income = income;
	}

	public Collateral getCollateral() {
		return collateral;
	}

	public void setCollateral(Collateral collateral) {
		this.collateral = collateral;
	}

	public Long getCollateralValue() {
		return collateralValue;
	}

	public void setCollateralValue(Long collateralValue) {
		if (collateral == Collateral.None) {
			collateralValue = 0L;
		}
		this.collateralValue = collateralValue;
	}

	public Long getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Long loanAmount) throws ConstraintViolationException {
		Long l;
		if (collateral == Collateral.None) {
			l = income * 8;
		} else {
			l = (long) (income * 5 + collateralValue * 0.8);
		}
		if (loanAmount > l) {
			throw new ConstraintViolationException(DExCode.INVALID_LOAN_AMOUNT, loanAmount, l);
		}
		this.loanAmount = loanAmount;
	}

	public Integer getDurationByMonth() {
		return durationByMonth;
	}

	public void setDurationByMonth(Integer durationByMonth) {
		this.durationByMonth = durationByMonth;
	}

	public Integer getRepaymentPeriod() {
		return repaymentPeriod;
	}

	public void setRepaymentPeriod(Integer repaymentPeriod) {
		this.repaymentPeriod = repaymentPeriod;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getCode() {
		return code;
	}

	public Long getLimit() {
		if (collateral == Collateral.None) {
			limit = income * 8;
		} else {
			limit = (long) (income * 5 + collateralValue * 0.8);
		}
		return limit;
	}

	public Double getInterestRate() {
		if (collateral == Collateral.None) {
			interestRate = 0.15;
		} else {
			interestRate = 0.07;
		}
		return interestRate;
	}
	
	public Long getTotalRepayment() {
		totalRepayment = (long) (loanAmount + (loanAmount * interestRate * durationByMonth / 12));
		return totalRepayment;
	}

	public Long getRepaidAmount() {
		return repaidAmount;
	}

	public Integer getTotalInstallments() {
		totalInstallments = durationByMonth / repaymentPeriod;
		return totalInstallments;
	}

	public Integer getNumOfPaidInstallments() {
//		numOfPaidInstallments = periodicRepaymentCount;
		return numOfPaidInstallments;
	}
	
	
	@DOpt(type = DOpt.Type.LinkAdder)
	// only need to do this for reflexive association:
	// @MemberRef(name="periodicRepayment")
	public boolean addPeriodicRepayment(PeriodicRepayment s) {
		if (!this.periodicRepayment.contains(s)) {
			periodicRepayment.add(s);
			//repaidAmount += s.getRepaymentAmount();
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewPeriodicRepayment(PeriodicRepayment s) {
		periodicRepayment.add(s);
		numOfPaidInstallments++;
		repaidAmount += s.getRepaymentAmount();
		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdder)
	public boolean addPeriodicRepayment(Collection<PeriodicRepayment> periodicRepayment) {
		for (PeriodicRepayment s : periodicRepayment) {
			if (!this.periodicRepayment.contains(s)) {
				this.periodicRepayment.add(s);
				//repaidAmount += s.getRepaymentAmount();
			}
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewPeriodicRepayment(Collection<PeriodicRepayment> periodicRepayment) {
		this.periodicRepayment.addAll(periodicRepayment);
		numOfPaidInstallments += periodicRepayment.size();
		for(PeriodicRepayment s: periodicRepayment) {
			repaidAmount += s.getRepaymentAmount();
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkRemover)
	// only need to do this for reflexive association:
	// @MemberRef(name="periodicRepayment")
	public boolean removePeriodicRepayment(PeriodicRepayment s) {
		boolean removed = periodicRepayment.remove(s);

		if (removed) {
			
			repaidAmount -= s.getRepaymentAmount();
//			repaidAmount = (long) (repaidAmount - s.getRepaymentAmount() + (this.getTotalRepayment()/this.getTotalInstallments())*0.1/12);
			numOfPaidInstallments--;
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.Setter)
	public void setPeriodicRepayment(Collection<PeriodicRepayment> periodicRepayment) {
		this.periodicRepayment = periodicRepayment;

		numOfPaidInstallments = periodicRepayment.size();
		for(PeriodicRepayment s: periodicRepayment) {
			repaidAmount += s.getRepaymentAmount();
		}
	}

	/**
	 * @effects return <tt>periodicRepaymentCount</tt>
	 */
	@DOpt(type = DOpt.Type.LinkCountGetter)
	public Integer getPeriodicRepaymentCount() {
		return periodicRepaymentCount;
	}

	@DOpt(type = DOpt.Type.LinkCountSetter)
	public void setPeriodicRepaymentCount(int count) {
		periodicRepaymentCount = count;
	}

	@DOpt(type = DOpt.Type.Getter)
	public Collection<PeriodicRepayment> getPeriodicRepayment() {
		return periodicRepayment;
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
		LoanAccount other = (LoanAccount) obj;
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
			return "LOA" + stringIdCounter;
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
