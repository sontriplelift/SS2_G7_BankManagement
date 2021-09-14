package services.account.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import services.Address;
import services.person.model.customer.Customer;
import services.transactions.model.creditTransactions.DeptRepayment;
import services.transactions.model.creditTransactions.Spending;
import domainapp.basics.model.meta.DAttr.Type;

@DClass(schema = "banksystem")
public class CreditAccount extends Account {

	public static final String A_code = "code";
	public static final String A_balance = "balance";

	@DAttr(name = A_code, cid=true, type = Type.String, auto = true, length = 9, mutable = false, optional = false)
	private String code;
	// static variable to keep track of account code
	private static int codeCounter = 0;
	
	@DAttr(name = "income", type = Type.Long, length = 20, optional = false, min = 10000000L)
	private Long income;

	@DAttr(name = "limit",auto=true, type = Type.Long, length = 20, optional = false, mutable = false)
	private Long limit;
	
	@DAttr(name = "creditBalance", type = Type.Long, length = 20, optional = true)
	private Long creditBalance;
	
//	@DAttr(name = "status", type = Type.String, length = 20, optional = false, mutable = false, auto = true)
//	private String status;
	
	@DAttr(name = "spending", type = Type.Collection, serialisable = false, optional = true, filter = @Select(clazz = Spending.class))
	@DAssoc(ascName = "creditAccount-has-spending", role = "creditAccount", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = Spending.class, cardMin = 1, cardMax = MetaConstants.CARD_MORE))
	private Collection<Spending> spending;

	private int spendingCount;
	
//	@DAttr(name = "creditStatement", type = Type.Collection, serialisable = false, optional = true, filter = @Select(clazz = CreditStatement.class))
//	@DAssoc(ascName = "creditAccount-has-creditStatement", role = "creditAccount", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = CreditStatement.class, cardMin = 1, cardMax = MetaConstants.CARD_MORE))
//	private Collection<CreditStatement> creditStatement;
//
//	private int creditStatementCount;

	@DAttr(name = "deptRepayment", type = Type.Collection, serialisable = false, optional = true, filter = @Select(clazz = DeptRepayment.class))
	@DAssoc(ascName = "creditAccount-has-deptRepayment", role = "creditAccount", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = DeptRepayment.class, cardMin = 1, cardMax = MetaConstants.CARD_MORE))
	private Collection<DeptRepayment> deptRepayment;

	private int deptRepaymentCount;
	

	// constructor

	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public CreditAccount(@AttrRef("customer") Customer customer, 
			@AttrRef("creationDate") Date creationDate,
			@AttrRef("income") Long income
			) throws ConstraintViolationException {
		this(null, customer,creationDate,  null, income, null, 0L);
	}
	
	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public CreditAccount(@AttrRef("customer") Customer customer, 
			@AttrRef("creationDate") Date creationDate,
			@AttrRef("income") Long income,
			@AttrRef("creditBalance") Long creditBalance
			) throws ConstraintViolationException {
		this(null, customer,creationDate,  null, income, null, creditBalance);
	}

	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public CreditAccount(@AttrRef("id") String id, 
			@AttrRef("customer") Customer customer,
			@AttrRef("creationDate") Date creationDate,
			@AttrRef("code") String code, 
			@AttrRef("income") Long income,
			@AttrRef("limit") Long limit,
			@AttrRef("creditBalance") Long creditBalance
//			@AttrRef("status") String status
			)
			throws ConstraintViolationException {
		// generate an code
		super(id, customer, creationDate);
		this.code = nextCode(code);
		this.income = income;
		this.limit = getLimit();
		if(creditBalance == null) {
			this.creditBalance = 0L;
		} else {
			this.creditBalance = creditBalance;
		}
		
//		this.status = getStatus();
		spending = new ArrayList<>();
		spendingCount = 0;
//		creditStatement = new ArrayList<>();
//		creditStatementCount = 0;
		deptRepayment = new ArrayList<>();
		deptRepaymentCount = 0;
		
	}

	public Long getCreditBalance() {
		return creditBalance;
	}

	public void setCreditBalance(Long creditBalance) {
		this.creditBalance = creditBalance;
	}

	public String getCode() {
		return code;
	}
	 
	public Long getIncome() {
		return income;
	}

	public void setIncome(Long income) {
		this.income = income;
	}

//	public String getStatus() {
//		return "normal";
//	}
//
//	public void setStatus(Long status) {
//		this.status = status;
//	}

	public Long getLimit() {
		return income*3 ;
	}
	
	

	
	//-----------------------------------------CREDIT-STATEMENT----------------------------------------------------------

//	@DOpt(type = DOpt.Type.LinkAdder)
//	// only need to do this for reflexive association: @MemberRef(name="creditStatement")
//	public boolean addCreditStatement(CreditStatement s) {
//		if (!this.creditStatement.contains(s)) {
//			creditStatement.add(s);
//		}
//
//		// no other attributes changed
//		return false;
//	}
//
//	@DOpt(type = DOpt.Type.LinkAdderNew)
//	public boolean addNewCreditStatement(CreditStatement s) {
//		creditStatement.add(s);
//		creditStatementCount++;
//
//		// no other attributes changed
//		return false;
//	}
//
//	@DOpt(type = DOpt.Type.LinkAdder)
//	public boolean addCreditStatement(Collection<CreditStatement> creditStatement) {
//		for (CreditStatement s : creditStatement) {
//			if (!this.creditStatement.contains(s)) {
//				this.creditStatement.add(s);
//			}
//		}
//
//		// no other attributes changed
//		return false;
//	}
//
//	@DOpt(type = DOpt.Type.LinkAdderNew)
//	public boolean addNewCreditStatement(Collection<CreditStatement> creditStatement) {
//		this.creditStatement.addAll(creditStatement);
//		creditStatementCount += creditStatement.size();
//
//		// no other attributes changed
//		return false;
//	}
//
//	@DOpt(type = DOpt.Type.LinkRemover)
//	// only need to do this for reflexive association: @MemberRef(name="creditStatement")
//	public boolean removeCreditStatement(CreditStatement s) {
//		boolean removed = creditStatement.remove(s);
//
//		if (removed) {
//			creditStatementCount--;
//		}
//
//		// no other attributes changed
//		return false;
//	}
//
//	@DOpt(type = DOpt.Type.Setter)
//	public void setCreditStatement(Collection<CreditStatement> creditStatement) {
//		this.creditStatement = creditStatement;
//
//		creditStatementCount = creditStatement.size();
//	}
//
//	/**
//	 * @effects return <tt>creditStatementCount</tt>
//	 */
//	@DOpt(type = DOpt.Type.LinkCountGetter)
//	public Integer getCreditStatementCount() {
//		return creditStatementCount;
//	}
//
//	@DOpt(type = DOpt.Type.LinkCountSetter)
//	public void setCreditStatementCount(int count) {
//		creditStatementCount = count;
//	}
//
//	@DOpt(type = DOpt.Type.Getter)
//	public Collection<CreditStatement> getCreditStatement() {
//		return creditStatement;
//	}
	
	
	
	
	//-----------------------------------------DEPT-REPAYMENT----------------------------------------------------------

	@DOpt(type = DOpt.Type.LinkAdder)
	// only need to do this for reflexive association:
	// @MemberRef(name="deptRepayment")
	public boolean addDeptRepayment(DeptRepayment s) {
		if (!this.deptRepayment.contains(s)) {
			deptRepayment.add(s);
			//balance += s.getDeptRepaymentAmount();
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewDeptRepayment(DeptRepayment s) {
		deptRepayment.add(s);
		deptRepaymentCount++;
		creditBalance -= s.getRepaymentAmount();
		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdder)
	public boolean addDeptRepayment(Collection<DeptRepayment> deptRepayment) {
		for (DeptRepayment s : deptRepayment) {
			if (!this.deptRepayment.contains(s)) {
				this.deptRepayment.add(s);
				//balance += s.getDeptRepaymentAmount();
			}
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewDeptRepayment(Collection<DeptRepayment> deptRepayment) {
		this.deptRepayment.addAll(deptRepayment);
		deptRepaymentCount += deptRepayment.size();
		for(DeptRepayment s: deptRepayment) {
			creditBalance -= s.getRepaymentAmount();
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkRemover)
	// only need to do this for reflexive association:
	// @MemberRef(name="deptRepayment")
	public boolean removeDeptRepayment(DeptRepayment s) {
		boolean removed = deptRepayment.remove(s);

		if (removed) {
			deptRepaymentCount--;
			creditBalance += s.getRepaymentAmount();
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.Setter)
	public void setDeptRepayment(Collection<DeptRepayment> deptRepayment) {
		this.deptRepayment = deptRepayment;

		deptRepaymentCount = deptRepayment.size();
		for(DeptRepayment s: deptRepayment) {
			creditBalance -= s.getRepaymentAmount();
		}
	}

	/**
	 * @effects return <tt>deptRepaymentCount</tt>
	 */
	@DOpt(type = DOpt.Type.LinkCountGetter)
	public Integer getDeptRepaymentCount() {
		return deptRepaymentCount;
	}

	@DOpt(type = DOpt.Type.LinkCountSetter)
	public void setDeptRepaymentCount(int count) {
		deptRepaymentCount = count;
	}

	@DOpt(type = DOpt.Type.Getter)
	public Collection<DeptRepayment> getDeptRepayment() {
		return deptRepayment;
	}
	
	
	
	
	//------------------------------------------SPENDING----------------------------------------------------
	
	@DOpt(type = DOpt.Type.LinkAdder)
	// only need to do this for reflexive association:
	// @MemberRef(name="payment")
	public boolean addSpending(Spending s) {
		if (!this.spending.contains(s)) {
			spending.add(s);
			
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewSpending(Spending s) {
		spending.add(s);
		spendingCount++;
		creditBalance += s.getPrice();
		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdder)
	public boolean addSpending(Collection<Spending> spending) {
		for (Spending s : spending) {
			if (!this.spending.contains(s)) {
				this.spending.add(s);
				
			}
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewSpending(Collection<Spending> spending) {
		this.spending.addAll(spending);
		spendingCount += spending.size();
		for(Spending s: spending) {
			creditBalance += s.getPrice();
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkRemover)
	// only need to do this for reflexive association:
	// @MemberRef(name="payment")
	public boolean removeSpending(Spending s) {
		boolean removed = spending.remove(s);

		if (removed) {
			spendingCount--;
			creditBalance -= s.getPrice();
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.Setter)
	public void setSpending(Collection<Spending> spending) {
		this.spending = spending;

		spendingCount = spending.size();
		for(Spending s: spending) {
			creditBalance += s.getPrice();
		}
	}

	/**
	 * @effects return <tt>paymentCount</tt>
	 */
	@DOpt(type = DOpt.Type.LinkCountGetter)
	public Integer getSpendingCount() {
		return spendingCount;
	}

	@DOpt(type = DOpt.Type.LinkCountSetter)
	public void setSpendingCount(int count) {
		spendingCount = count;
	}

	@DOpt(type = DOpt.Type.Getter)
	public Collection<Spending> getSpending() {
		return spending;
	}
	
	
	
	
	//---------------------------------------------------------------------------------------------------------
	

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
		CreditAccount other = (CreditAccount) obj;
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
			return "CRE" + stringIdCounter;
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
